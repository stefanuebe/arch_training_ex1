package foo.v5archstudygroup.exercises.backpressure.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for sending the messages to the server. You are allowed to change this class in any
 * way you like as long as all messages are delivered successfully to the server.
 */
public class ProcessingWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessingWorker.class);
    private final ProcessingRequestGenerator requestGenerator;
    private final RestClient client;
    private int sent = 0;
    private int errors = 0;

    public ProcessingWorker(ProcessingRequestGenerator requestGenerator, RestClient client) {
        this.requestGenerator = requestGenerator;
        this.client = client;
    }

    public void run() {
        while (requestGenerator.hasNext()) {
            var message = requestGenerator.next();
            try {
                sent++;

                while (!client.sendToServer(message)) {
                    // when coming into here, the server is overloaded and we have to try later to send something
                    LOGGER.trace("BANDWIDTH_LIMIT_EXCEEDED, sleep and try again soon");
                    Thread.sleep(250);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.error("Error sending request {}: {}", message.getUuid(), ex.getMessage());
                errors++;
            }
        }
        LOGGER.info("Finished sending {} requests with {} errors", sent, errors);
    }
}
