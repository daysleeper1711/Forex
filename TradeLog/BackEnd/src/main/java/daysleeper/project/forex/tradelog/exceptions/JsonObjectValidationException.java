/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daysleeper.project.forex.tradelog.exceptions;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author DaySLeePer
 */
public class JsonObjectValidationException extends RuntimeException {
    private final String validationMsg;

    /**
     * Constructs an instance of <code>JsonObjectValidationException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public JsonObjectValidationException(String validationMsg) {
        this.validationMsg = validationMsg;
    }
    
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("type", this.getClass().getSimpleName())
                .add("message", this.validationMsg)
                .build();
    }
}
