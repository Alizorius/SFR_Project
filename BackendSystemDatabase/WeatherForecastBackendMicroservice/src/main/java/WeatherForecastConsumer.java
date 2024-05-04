import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class WeatherForecastConsumer {
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "weather-forecast-consumer-group");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        config.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        config.put("schema.registry.url", "http://localhost:8081");

        final String topic = "weather_forecast";
        final String topic_aggregate = "average_location_temperature";

        // Open a db connection
        try (Connection connection =  DB.connect()){
            System.out.println("Connected to the PostgreSQL database.");

            // Create Insert for topic
            String insertSql = "INSERT INTO weather_forecast (Location, Condition, Temperature) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSql);

            // Create Kafka consumer instance and subscribe to the topic
            try (final Consumer<String, WeatherForecast> consumer = new KafkaConsumer<String, WeatherForecast>(config)) {
                consumer.subscribe(Arrays.asList(topic));
                while (true) {
                    ConsumerRecords<String, WeatherForecast> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, WeatherForecast> record : records) {
                        String key = record.key();
                        WeatherForecast value = record.value();
                        System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
                        statement.setString(1, value.getLocation().toString());
                        statement.setString(2, value.getCondition().toString());
                        statement.setDouble(3, value.getTemperature());

                        // execute the INSERT statement
                        statement.executeUpdate();
                    }
                    connection.commit();
                }
            }

            // Create Insert for topic_aggregate
            /*String insertSql = "INSERT INTO average_location_temperature (Location, AverageTemp) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSql);

            // Create Kafka consumer instance and subscribe to the topic_aggregate
            try (final Consumer<String, WeatherDataAggregate> consumer = new KafkaConsumer<String, WeatherDataAggregate>(config)) {
                consumer.subscribe(Arrays.asList(topic_aggregate));
                while (true) {
                    ConsumerRecords<String, WeatherDataAggregate> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, WeatherDataAggregate> record : records) {
                        String key = record.key();
                        WeatherDataAggregate value = record.value();
                        System.out.println(String.format("Consumed event from topic %s: key = %-10s value = %s", topic_aggregate, key, value));
                        statement.setString(1, value.getLocation().toString());
                        statement.setDouble(2, value.getAverageTemperature());

                        // execute the INSERT statement
                        statement.executeUpdate();
                    }
                    connection.commit();
                }
            }*/
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
