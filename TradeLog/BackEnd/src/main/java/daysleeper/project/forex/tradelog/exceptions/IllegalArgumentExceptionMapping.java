package daysleeper.project.forex.tradelog.exceptions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapping implements ExceptionMapper<IllegalArgumentException>{

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(toJson(new IllegalArgumentException("No enum value support")))
                .build();
    }
    
    private JsonObject toJson(IllegalArgumentException exception) {
         return Json.createObjectBuilder()
                .add("type", exception.getClass().getSimpleName())
                .add("message", exception.getLocalizedMessage())
                .build();
    }
}
