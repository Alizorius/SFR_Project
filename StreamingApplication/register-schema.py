from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.avro import AvroSchema

# Create a SchemaRegistryClient instance
schema_registry_client = SchemaRegistryClient({"url": "http://localhost:8081"})

# Load Avro schema from file
with open("../StreamingApplication/WeatherForecast.avsc", "r") as schema_file:
    avro_schema_str = schema_file.read()

# Create an AvroSchema object
avro_schema = AvroSchema(avro_schema_str)

# Register the Avro schema under the subject name
schema_id = schema_registry_client.register_schema("weather-forecast-value", avro_schema)
