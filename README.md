# kafka-test

Simple code to run a Kafka producer and a couple of Kafka consumers in parallel.

#Development setup

1. Download Kafka 0.10.1.0 and un-tar it.
2. Start a Zookeeper server.
3. Start a Kafka server.
4. Create a topic in your Kafka broker with 2 partitions called "javatest".
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic javatest


When running the program you will see in the console whenever 1000 messages have been both produced and consumed.
