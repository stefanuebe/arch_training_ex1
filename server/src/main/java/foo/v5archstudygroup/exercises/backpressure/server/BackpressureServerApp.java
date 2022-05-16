package foo.v5archstudygroup.exercises.backpressure.server;

import foo.v5archstudygroup.exercises.backpressure.messages.converter.ProcessingRequestMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main entry point into the server application.
 */
@SpringBootApplication
public class BackpressureServerApp {

    /**
     * We need this to be able to receive {@link foo.v5archstudygroup.exercises.backpressure.messages.Messages.ProcessingRequest} messages.
     */
    @Bean
    public ProcessingRequestMessageConverter processingRequestMessageConverter() {
        return new ProcessingRequestMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(BackpressureServerApp.class, args);
    }
}
