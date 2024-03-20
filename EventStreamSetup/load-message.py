from kafka import KafkaProducer
import time
import random

# Kafka broker address
bootstrap_servers = 'localhost:9092'

# Kafka topic to produce messages to
topic = 'weather_forecast'

# Sample weather data -> get from api (soon)
weather_conditions = ['sunny', 'cloudy', 'rainy', 'windy', 'stormy']
locations = ['New York', 'Los Angeles', 'Chicago', 'Houston', 'Miami']
temperatures = {'sunny': (70, 90), 'cloudy': (60, 75), 'rainy': (50, 70), 'windy': (65, 80), 'stormy': (55, 75)}


# Create KafkaProducer instance
producer = KafkaProducer(bootstrap_servers=bootstrap_servers)

try:
    # Produce messages to Kafka topic
    for i in range(10):
        location = random.choice(locations)
        condition = random.choice(weather_conditions)
        min_temp, max_temp = temperatures[condition]
        temperature = random.randint(min_temp, max_temp)
        message = f"Location: {location}, Condition: {condition}, Temperature: {temperature}°F"
        producer.send(topic, message.encode('utf-8'))
        print(f"Produced: {message}")
        time.sleep(1)  # Add some delay between messages
    print("All messages produced successfully!")
except Exception as ex:
    print(f"Error producing messages: {ex}")
finally:
    # Close KafkaProducer
    producer.close()
