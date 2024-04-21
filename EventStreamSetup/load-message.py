from kafka import KafkaProducer
import time
import random
from avro import schema, io
import avro.schema
import avro.io
import json
from io import BytesIO

# Kafka broker address
bootstrap_servers = 'localhost:9092'

# Kafka topic to produce messages to
topic = 'weather_forecast'

# Avro schema for weather forecast data
avro_schema = {
    "type": "record",
    "name": "WeatherForecast",
    "fields": [
        {"name": "location", "type": "string"},
        {"name": "condition", "type": "string"},
        {"name": "temperature", "type": "int"}
    ]
}

# Sample weather data -> get from api (soon)
weather_conditions = ['sunny', 'cloudy', 'rainy', 'windy', 'stormy']
locations = ['New York', 'Los Angeles', 'Chicago', 'Houston', 'Miami']
temperatures = {'sunny': (70, 90), 'cloudy': (60, 75), 'rainy': (50, 70), 'windy': (65, 80), 'stormy': (55, 75)}

# Create Avro schema object
avro_parsed_schema = avro.schema.parse(json.dumps(avro_schema))

# Create KafkaProducer instance
producer = KafkaProducer(bootstrap_servers=bootstrap_servers)

try:
    # Produce messages to Kafka topic
    for i in range(10):
        location = random.choice(locations)
        condition = random.choice(weather_conditions)
        min_temp, max_temp = temperatures[condition]
        temperature = random.randint(min_temp, max_temp)

        # Create Avro datum writer
        writer = avro.io.DatumWriter(avro_parsed_schema)
        bytes_writer = BytesIO()
        encoder = avro.io.BinaryEncoder(bytes_writer)
        
        # Write data to bytes writer
        writer.write({"location": location, "condition": condition, "temperature": temperature}, encoder)
        raw_bytes = bytes_writer.getvalue()
        
        # Send message to Kafka topic
        producer.send(topic, raw_bytes)
        print(f"Produced: Location: {location}, Condition: {condition}, Temperature: {temperature}°F")
        time.sleep(1)  # Add some delay between messages
    print("All messages produced successfully!")
except Exception as ex:
    print(f"Error producing messages: {ex}")
finally:
    # Close KafkaProducer
    producer.close()
