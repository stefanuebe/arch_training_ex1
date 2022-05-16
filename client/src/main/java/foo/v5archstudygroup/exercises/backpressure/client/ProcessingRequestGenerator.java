package foo.v5archstudygroup.exercises.backpressure.client;

import com.google.protobuf.ByteString;
import foo.v5archstudygroup.exercises.backpressure.messages.Messages;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is responsible for generating messages to send to the server. You are not allowed to change this.
 */
public class ProcessingRequestGenerator implements Iterator<Messages.ProcessingRequest> {

    private static final int MIN_PAYLOAD_BYTES = 128;
    private static final int MAX_PAYLOAD_BYTES = 104857600;
    private final int count;
    private final Random rnd = new Random();
    private final AtomicInteger nextIndex = new AtomicInteger(0);

    public ProcessingRequestGenerator(int count) {
        this.count = count;
    }

    @Override
    public boolean hasNext() {
        return nextIndex.get() < count;
    }

    @Override
    public Messages.ProcessingRequest next() {
        if (nextIndex.incrementAndGet() > count) {
            throw new NoSuchElementException("Out of requests to process");
        }
        return Messages.ProcessingRequest.newBuilder()
                .setUuid(UUID.randomUUID().toString())
                .setPayload(ByteString.copyFrom(randomBytes()))
                .build();
    }

    private byte[] randomBytes() {
        var len = MIN_PAYLOAD_BYTES + rnd.nextInt(MAX_PAYLOAD_BYTES - MIN_PAYLOAD_BYTES);
        var bytes = new byte[len];
        rnd.nextBytes(bytes);
        return bytes;
    }
}
