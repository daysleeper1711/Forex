package daysleeper.project.forex.tradelog.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonObjectValidationExceptionMapping implements ExceptionMapper<JsonObjectValidationException>{

    @Override
    public Response toResponse(JsonObjectValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.toJson())
                .build();
    }
    
}
