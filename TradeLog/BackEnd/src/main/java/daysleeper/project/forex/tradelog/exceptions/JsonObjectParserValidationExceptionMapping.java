package daysleeper.project.forex.tradelog.exceptions;

import daysleeper.project.forex.tradelog.utilies.Notification;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonObjectParserValidationExceptionMapping extends ExceptionNotification implements ExceptionMapper<JsonObjectParserValidationException>{

    @Override
    public Response toResponse(JsonObjectParserValidationException exception) {
        Notification notification = super.createNotificationValidation(exception.getMessages());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(notification.toJson())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
    
}
