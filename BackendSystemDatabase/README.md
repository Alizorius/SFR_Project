SFR SS24 | Kate Ongcoy, Alice Mihalca
ASSIGNMENT: SCALABLE BACKEND & DATABASE SYSTEMS 
QUESTIONS ON YOUR TECHNOLOGY CHOICE


QUESTIONS ABOUT THE MICROSERVICE:
Q1
What happens if the microservice goes down and cannot process the messages from Kafka anymore?
If the microservice goes offline and can't process Kafka messages, we rely on Kafka's built-in message retention feature. Kafka stores messages in topics until they are consumed. We can use consumer group configurations to ensure that messages are not lost when one microservice instance fails, allowing other instances to continue processing.

Q2
What happens if the microservice consumes the messages and goes down while processing the message?
If this occurs, we need mechanisms to handle incomplete operations. By implementing idempotency, the microservice can safely reprocess messages without causing duplicates or inconsistencies. Kafka's offset tracking helps us determine where the failure occurred, allowing for recovery and continued processing.

Q3
What happens when the microservice consumes the message but cannot write the event into the database as it is unavailable?
In this scenario, we design the microservice with a retry mechanism to ensure no messages are lost. This system queues the messages and retries writing to the database when it's back online. This approach ensures data persistence even during temporary outages.

Q4
Can you span a transaction across reading from Kafka and writing to the database?
Typically, spanning transactions between Kafka and a database is complex. Instead, we use Kafka for reliable message handling and PostgreSQL for durable storage, emphasizing an event-driven approach with eventual consistency. Kafka ensures message persistence, while PostgreSQL provides strong ACID properties for data storage and retrieval.


QUESTIONS ABOUT THE DATABASE
Q1
Why did you decide on the given database model (SQL, NoSQL like document store, column store, or graph database)?
PostgreSQL, as an SQL database, provides ACID transactions, supporting data integrity and complex queries. This choice fits well with the structured nature of weather forecast data. SQL databases offer robust transaction support and query capabilities, which are essential for operations involving multiple relationships and complex data processing.

Q2
What guarantees does the database give you if you want to join different entities?
PostgreSQL supports SQL joins, allowing complex queries across different entities. This capability is vital for a microservice that might need to retrieve data from related tables or create aggregate views.

Q3
Describe how the database scales (leader/follower, sharding/partitioning, etc.) horizontally to multiple instances?
PostgreSQL can scale horizontally through replication, partitioning, or sharding. This scalability is essential for high-volume data systems like those involving Kafka. The database's ability to scale and maintain consistency makes it a reliable choice for production-ready microservices.