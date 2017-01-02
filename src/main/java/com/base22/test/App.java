package com.base22.test;

// import org.omg.SendingContext.RunTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
	public static void main( String[] args ) {
		MyKafkaProducer producer = new MyKafkaProducer();

// Trying multiple threaded consumer tutorial.
		final int consumerNumber = 2; // 2 Consumers in one single consumer group.
		final String groupId = "consumer-tutorial-group";
		List<String> topics = Arrays.asList("javatest"); //Same topic as the producer's.
		final ExecutorService executorService = Executors.newFixedThreadPool( consumerNumber ); // Service to manage multiple threads.
		final List<MyKafkaConsumer> consumers = new ArrayList<MyKafkaConsumer>(); // Keeps track of the consumers on this group.

		// Add each consumer to the executor.
		for ( int i = 0; i < consumerNumber; i++ ) {
			MyKafkaConsumer consumer = new MyKafkaConsumer( i, groupId, topics );
			consumers.add( consumer );
			executorService.submit( consumer );
		}
		producer.run();

		// Shutdown all the threads.
		Runtime.getRuntime().addShutdownHook( new Thread(){
			@Override
			public void run(){
				System.out.println( "Shuting system down." );
				try {
					for ( MyKafkaConsumer consumer: consumers ){
						consumer.shutdown();
					}
					executorService.shutdown();
					executorService.awaitTermination(5000, TimeUnit.MILLISECONDS); // Waits 5 seconds before definitely closing all tasks.
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

}

