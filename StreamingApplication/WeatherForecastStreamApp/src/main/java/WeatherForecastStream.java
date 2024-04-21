import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;


/*
    Kafka Streams application that consume weather forecast data from the weather_forecast topic,
    aggregates weather forecast data by location and calculates the average temperature,
    then creates another stream with the aggregated data
*/

public class WeatherForecastStream {

    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "weather-forecast-stream");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        String inputTopic = "weather_forecast";
        String outputTopic = "average_location_temperature";

        // Configure Avro Serdes
        SpecificAvroSerde<WeatherForecast> weatherSerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<WeatherDataAggregate> aggregatedSerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<CountAndSum> countSumSerde = new SpecificAvroSerde<>();

        // Set up serde properties
        Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        weatherSerde.configure(serdeConfig, false);
        aggregatedSerde.configure(serdeConfig, false);
        countSumSerde.configure(serdeConfig, false);

        StreamsBuilder builder = new StreamsBuilder();

        // Create a KStream from the "weather_forecast" topic
        KStream<String, WeatherForecast> weatherStream = builder.stream(inputTopic, Consumed.with(Serdes.String(), weatherSerde))
                .peek((key, value) -> System.out.println("Incoming record - key " +key +" value " + value));

        // Grouping temperature by location
        KGroupedStream<String, Double> tempByLocation = weatherStream
                .map((key, value) -> new KeyValue<>(value.getLocation().toString(), value.getTemperature()))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()));


        // Aggregate temperature count and sum for each location
        final KTable<String, CountAndSum> tempCountAndSum =
                tempByLocation.aggregate(
                        () -> new CountAndSum(0, 0.0),
                        (key, value, aggregate) -> {
                            aggregate.setCount(aggregate.getCount() + 1);
                            aggregate.setSum(aggregate.getSum() + value);
                            return aggregate;
                        },
                        Materialized.with(Serdes.String(), countSumSerde));

        // Calculate average temperature for each location
        final KTable<String, WeatherDataAggregate> aggregatedWeatherData =
                tempCountAndSum.mapValues((key, value) -> {
                            double averageTemperature = value.getSum() / value.getCount();
                            WeatherDataAggregate result = new WeatherDataAggregate();
                            result.setLocation(key);
                            result.setAverageTemperature(averageTemperature);
                            return result;
                        },
                        Materialized.with(Serdes.String(), aggregatedSerde));

        // New Stream from aggregated data
        aggregatedWeatherData.toStream()
                .peek((key, value) -> System.out.println("Outgoing record - key " +key +" value " + value))
                .to(outputTopic, Produced.with(Serdes.String(), aggregatedSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
    }
}
