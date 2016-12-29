package com.base22.test;

/**
 * Hello world!
 */
public class App {
	public static void main( String[] args ) {
		MyKafkaProducer producer = new MyKafkaProducer();
		MyKafkaConsumer consumer = new MyKafkaConsumer();
		consumer.runConsumer();
//		producer.runProducer();

	}

}
