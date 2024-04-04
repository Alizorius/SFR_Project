from confluent_kafka.schema_registry import SchemaRegistryClient, Schema

# Schema Registry URL
schema_registry_url = 'http://localhost:8081'

# Create a SchemaRegistryClient instance
schema_registry_client = SchemaRegistryClient({"url": schema_registry_url})

# Load Avro schema from file
with open("StreamingApplication/WeatherForecast.avsc", "r") as schema_file:
    avro_schema_string = schema_file.read()

with open("StreamingApplication/WeatherDataAggregate.avsc", "r") as schema_file:
    avro_aggregated_schema_string = schema_file.read()

# Get Avro Schema from string
avro_schema = Schema(avro_schema_string, "AVRO")
avro_aggregated_schema = Schema(avro_aggregated_schema_string, "AVRO")

# Register the Avro schema under the subject name
schema_id = schema_registry_client.register_schema("weather-forecast-schema", avro_schema)
print(schema_id)
aggregated_schema_id = schema_registry_client.register_schema("weather-aggregated-schema", avro_aggregated_schema)
print(aggregated_schema_id)