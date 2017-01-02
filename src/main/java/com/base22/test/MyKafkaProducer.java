package com.base22.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
// import org.apache.kafka.connect.connector.Connector;
// import org.apache.kafka.connect.sink.SinkConnector;

// import java.util.Collection;
import java.util.Properties;

/**
 * Created by margaritahernandez on 12/28/16.
 */
public class MyKafkaProducer {

	private final static String TOPIC = "javatest";
	private Properties properties = new Properties();
	private Producer<String, String> producer;

	public MyKafkaProducer() {
		org.apache.log4j.BasicConfigurator.configure();
		// As found in the producer.properties file from the kafka console producer.
		properties.put( "bootstrap.servers", "localhost:9092" );
		properties.put( "compression.type", "none" );
		properties.put( "key.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
		properties.put( "value.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
		
		producer = new KafkaProducer<String, String>( properties );
	}

	public void run(){
		for ( int i = 0; i < 2000000; i++ ) {
			if( i%1000 == 0 ){
				producer.send( new ProducerRecord<String, String>( TOPIC, Integer.toString( i ), "Multiple of 1000: "+i ) );				
			}
		}
		producer.close();
	}

}
