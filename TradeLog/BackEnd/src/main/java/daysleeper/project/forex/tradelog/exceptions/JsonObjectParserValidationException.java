/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daysleeper.project.forex.tradelog.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DaySLeePer
 */
public class JsonObjectParserValidationException extends RuntimeException {

    private List<String> messages = new ArrayList<>();

    public JsonObjectParserValidationException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
    

}
