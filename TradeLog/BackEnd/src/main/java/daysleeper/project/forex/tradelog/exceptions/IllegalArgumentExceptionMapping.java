package daysleeper.project.forex.tradelog.exceptions;

import daysleeper.project.forex.tradelog.utilies.Notification;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapping extends ExceptionNotification implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        Notification notification = super.createNotification(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(notification.toJson())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
