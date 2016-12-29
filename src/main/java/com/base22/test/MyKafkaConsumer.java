package com.base22.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by margaritahernandez on 12/28/16.
 */
public class MyKafkaConsumer {
	private final static String TOPIC = "javatest";
	private Properties properties = new Properties();

	public void runConsumer() {
		org.apache.log4j.BasicConfigurator.configure();

		// As found in the consumer.properties file from the kafka console consumer.
		properties.put( "bootstrap.servers", "localhost:9092" );
		properties.put( "group.id", "test-consumer-group" );
//		properties.put( "zookeeper.connect", "127.0.0.1:2181" );
//		properties.put( "zookeeper.connection.timeout.ms", "6000" );
		properties.put( "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );
		properties.put( "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>( properties );
		consumer.subscribe( Arrays.asList( TOPIC ) );
		while ( true ) {
			ConsumerRecords<String, String> records = consumer.poll( 100 );
			for ( ConsumerRecord<String, String> record : records )
				System.out.printf( "offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value() );
		}

	}
}
