import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


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
        config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        // Configure Avro Serdes
        SpecificAvroSerde<WeatherForecast> weatherSerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<AggregatedWeatherData> aggregatedSerde = new SpecificAvroSerde<>();

        // Set up serde properties
        Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        weatherSerde.configure(serdeConfig, false);
        aggregatedSerde.configure(serdeConfig, false);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, WeatherForecast> weatherStream = builder.stream("weather_forecast", Consumed.with(Serdes.String(), weatherSerde));

        // Parse weather data and extract location and temperature
        KTable<String, AggregatedWeatherData> aggregatedWeatherData = weatherStream
                .mapValues(value -> {
                    String[] parts = value.split(",");
                    return Double.parseDouble(parts[2].split(":")[1].replace("°F", "").trim());
                })
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Double()))
                .aggregate(
                        AggregatedWeatherData::new,
                        (key, newValue, aggregate) -> {
                            // Aggregator: Update the aggregate object with the new temperature data
                            aggregate.setTotalTemperature(aggregate.getTotalTemperature() + value.getTemperature());
                            aggregate.setCount(aggregate.getCount() + 1);
                            return aggregate;
                        },
                        Materialized.with(Serdes.String(), aggregatedSerde)
                )
                .mapValues((key, value) -> {
                    // Map the aggregated data to AggregatedWeatherData objects with average temperature
                    double averageTemperature = value.getTotalTemperature() / value.getCount();
                    return new AggregatedWeatherData(key, averageTemperature);
                });

        // New Stream from aggregated data
        aggregatedWeatherData.toStream("average_location_temperature", Produced.with(Serdes.String(), aggregatedSerde)).foreach((key, value) -> System.out.println("Location: " + key + ", Average Temperature: " + value + "°F"));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
    }
}
