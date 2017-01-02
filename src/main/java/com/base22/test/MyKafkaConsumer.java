package com.base22.test;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

// import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by margaritahernandez on 12/28/16.
 */

public class MyKafkaConsumer implements Runnable {
//	private final static String TOPIC = "javatest";

	// Following the multiple threads consumer tutorial.
	private List<String> topics;
	private int id;
	private KafkaConsumer<String, String> consumer;
	private Properties properties = new Properties();

//	public MyKafkaConsumer() {
//		org.apache.log4j.BasicConfigurator.configure();
//
//		// As found in the consumer.properties file from the kafka console consumer.
//		properties.put( "bootstrap.servers", "localhost:9092" );
//		properties.put( "group.id", "test-consumer-group" );
//		properties.put( "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );
//		properties.put( "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );
//
//		consumer = new KafkaConsumer<String, String>( properties );
//	}

	public MyKafkaConsumer( int id, String groupId, List<String> topics ) {
		this.topics = topics;
		this.id = id;

		org.apache.log4j.BasicConfigurator.configure();
		// As found in the consumer.properties file from the kafka console consumer.
		this.properties.put( "bootstrap.servers", "localhost:9092" );
		this.properties.put( "group.id", groupId );
		this.properties.put( "key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );
		this.properties.put( "value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer" );
		// Additional important configs
		this.properties.put( "session.timeout.ms", "40000" ); // Default 30 seconds.

		this.consumer = new KafkaConsumer<String, String>( this.properties );
	}

	@Override
	public void run() {
		try {
			consumer.subscribe( this.topics );

			while ( true ) {
				ConsumerRecords<String, String> records = consumer.poll( Long.MAX_VALUE );
				for ( ConsumerRecord<String, String> record : records )
					System.out.printf( "%d : offset = %d, key = %s, value = %s%n", id, record.offset(), record.key(), record.value() );
			}
		} catch ( WakeupException e ) {
			// Ignore for shutdown.
		} finally {
			consumer.close();
		}
	}

	public void shutdown() {
		consumer.wakeup();
	}
}
