package foo.v5archstudygroup.exercises.backpressure.messages.converter;

import foo.v5archstudygroup.exercises.backpressure.messages.Messages;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Map;

public class ProcessingRequestMessageConverter extends AbstractHttpMessageConverter<Messages.ProcessingRequest> {

    public static final MediaType CONTENT_TYPE = new MediaType("application", "protobuf", Map.of("proto", Messages.ProcessingRequest.getDescriptor().getName()));

    public ProcessingRequestMessageConverter() {
        super(CONTENT_TYPE);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Messages.ProcessingRequest.class.isAssignableFrom(clazz);
    }

    @Override
    protected Messages.ProcessingRequest readInternal(Class<? extends Messages.ProcessingRequest> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return Messages.ProcessingRequest.parseFrom(inputMessage.getBody());
    }

    @Override
    protected void writeInternal(Messages.ProcessingRequest request, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(CONTENT_TYPE);
        outputMessage.getBody().write(request.toByteArray());
    }
}
