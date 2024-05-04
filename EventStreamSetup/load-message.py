from confluent_kafka import Producer
from confluent_kafka.avro import CachedSchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroSerializer
import time
import random
import json

def delivery_callback(err, msg):
    if err:
        print('ERROR: Message failed delivery: {}'.format(err))
    else:
        print("Produced event to topic {topic}: key = {key:12} value = {value:12}".format(
            topic=msg.topic(), key=msg.key(), value=msg.value()))

# config
bootstrap_servers = 'localhost:9092'
schema_registry_url = 'http://localhost:8081'

# Kafka topic to produce messages to
topic = 'weather_forecast'

# Avro schema ID registered in Schema Registry
schema_id = 1
schema_subject = "weather-forecast-schema"
schema_version = "1"

# Avro schema for weather forecast data
# avro_schema = {
#     "type": "record",
#     "name": "WeatherForecast",
#     "fields": [
#         {"name": "location", "type": "string"},
#         {"name": "condition", "type": "string"},
#         {"name": "temperature", "type": "double"}
#     ]
# }
# Retrieve Avro schema from Schema Registry by schema ID.
schema_registry = CachedSchemaRegistryClient({'url': schema_registry_url})
# Retrieve Avro schema from Schema Registry by subject and version.
schema_id, avro_schema, _ = schema_registry.get_latest_schema(schema_subject)
# Convert Avro schema object to JSON-serializable dictionary
avro_schema_dict = {
    "type": avro_schema.type,
    "name": avro_schema.name,
    "fields": [{"name": field.name, "type": field.type} for field in avro_schema.fields]
}
# Convert each field in the Avro schema
for field in avro_schema.fields:
    if hasattr(field.type, 'type'):  # Check if the field type has a 'type' attribute
        # Complex type, handle accordingly
        field_type = {
            "type": field.type.type,
            "name": field.type.name,
            "fields": [{"name": f.name, "type": f.type.type} for f in field.type.fields]
        }
    else:
        # Primitive type, handle accordingly
        field_type = field.type

    avro_schema_dict["fields"].append({"name": field.name, "type": field_type})

# Create Avro serializer
avro_serializer = AvroSerializer(schema_registry_client=schema_registry)

# Sample weather data
weather_conditions = ['sunny', 'cloudy', 'rainy', 'windy', 'stormy']
locations = ['New York', 'Los Angeles', 'Chicago', 'Houston', 'Miami']
temperatures = {'sunny': (70, 90), 'cloudy': (60, 75), 'rainy': (50, 70), 'windy': (65, 80), 'stormy': (55, 75)}

# Create Producer instance with Schema Registry configuration and Avro serializer
producer = Producer({
    'bootstrap.servers': bootstrap_servers, 
    'schema.registry.url': schema_registry_url,
    'key.serializer': 'confluent_kafka.serializing.StringSerializer', 
    'value.serializer': avro_serializer})

try:
    # Produce messages to Kafka topic
    for i in range(10):
        location = random.choice(locations)
        condition = random.choice(weather_conditions)
        min_temp, max_temp = temperatures[condition]
        temperature = random.uniform(min_temp, max_temp)

        # Create message with Avro schema
        message = {"location": location, "condition": condition, "temperature": temperature}

        # Produce message to Kafka topic using Avro schema
        producer.produce(topic=topic, value=message, key="key", value_schema_id=schema_id, on_delivery=delivery_callback)
        print(f"Produced: Location: {location}, Condition: {condition}, Temperature: {temperature}°F")
        time.sleep(1)  # Add some delay between messages
    print("All messages produced successfully!")
except Exception as ex:
    print(f"Error producing messages: {ex}")
finally:
    # Flush and close Producer
    producer.flush()
    producer.close()
