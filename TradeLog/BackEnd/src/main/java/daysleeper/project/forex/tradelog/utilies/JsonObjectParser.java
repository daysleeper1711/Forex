package daysleeper.project.forex.tradelog.utilies;

import daysleeper.project.forex.tradelog.exceptions.JsonObjectParserValidationException;
import daysleeper.project.forex.tradelog.model.Position;
import daysleeper.project.forex.tradelog.model.Symbol;
import daysleeper.project.forex.tradelog.model.Trade;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;

public class JsonObjectParser {

    //-------------------------------------------------
    // Constant value
    //-------------------------------------------------
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    //-------------------------------------------------
    // JsonObject to Trade entity
    //-------------------------------------------------
    public Trade jsonTradeParser(JsonObject jsonInput) {
        List<String> validateMessages = new ArrayList<>();
        Trade trade = new Trade();
        JsonParser parser = Json.createParser(new StringReader(jsonInput.toString()));
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            while (parser.hasNext() && !event.equals(JsonParser.Event.KEY_NAME)) {
                event = parser.next();
            }

            //--- parsing symbol of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("symbol")) {
                event = parser.next();
                //--- validation symbol's field
                switch (event) {
                    case VALUE_STRING:
                        String symbolVal = parser.getString().toUpperCase();
                        Symbol symbol = Symbol.valueOf(symbolVal);
                        trade.setSymbol(symbol);
                        break;
                    case VALUE_NUMBER:
                        validateMessages.add("symbol must be string");
                        break;
                    default:
                        break;
                }
            }//--- end parsing symbol

            //--- parsing position of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("position")) {
                event = parser.next();
                //--- validation position's field
                switch (event) {
                    case VALUE_STRING:
                        String positionVal = parser.getString().toUpperCase();
                        Position position = Position.valueOf(positionVal);
                        trade.setPosition(position);
                        break;
                    case VALUE_NUMBER:
                        validateMessages.add("position must be string");
                        break;
                    default:
                        break;
                }
            }//--- end parsing position

            //--- parsing size of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("size")) {
                event = parser.next();
                //--- validation size's field
                switch (event) {
                    case VALUE_STRING:
                        validateMessages.add("size must be string");
                        break;
                    case VALUE_NUMBER:
                        BigDecimal size = parser.getBigDecimal().round(MathContext.DECIMAL32);
                        trade.setTradeSize(size);
                        break;
                    default:
                        break;
                }
            }//--- end parsing trade's size

            //--- parsing enter of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("enter")) {
                event = parser.next();
                //--- validation enter's field
                switch (event) {
                    case VALUE_STRING:
                        validateMessages.add("enter must be number");
                        break;
                    case VALUE_NUMBER:
                        BigDecimal enter = parser.getBigDecimal().round(MathContext.DECIMAL32);
                        trade.setEnter(enter);
                        break;
                    default:
                        break;
                }
            }//--- end parsing enter

            //--- parsing take profit of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("take profit")) {
                event = parser.next();
                //--- validation takeProfit's field
                switch (event) {
                    case VALUE_STRING:
                        validateMessages.add("take profit must be number");
                        break;
                    case VALUE_NUMBER:
                        BigDecimal tp = parser.getBigDecimal().round(MathContext.DECIMAL32);
                        trade.setTakeProfit(tp);
                        break;
                    default:
                        break;
                }
            }//--- end parsing take profit

            //--- parsing stop loss of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("stop loss")) {
                event = parser.next();
                //--- validation stopLoss's field
                switch (event) {
                    case VALUE_STRING:
                        validateMessages.add("stop loss must be number");
                        break;
                    case VALUE_NUMBER:
                        BigDecimal sl = parser.getBigDecimal().round(MathContext.DECIMAL32);
                        trade.setStoploss(sl);
                        break;
                    default:
                        break;
                }
            }//--- end parsing stop loss

            //--- parsing stop of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("stop")) {
                event = parser.next();
                //--- validation stop's field
                switch (event) {
                    case VALUE_STRING:
                        validateMessages.add("stop must be number");
                        break;
                    case VALUE_NUMBER:
                        BigDecimal stop = parser.getBigDecimal().round(MathContext.DECIMAL32);
                        trade.setStop(stop);
                        break;
                    default:
                        break;
                }
            }//--- end parsing stop

            //--- parsing date enter of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("date enter")) {
                event = parser.next();
                //--- validation date enter's field
                switch (event) {
                    case VALUE_STRING:
                        String dateEnterVal = parser.getString();
                        try {
                            Date dateEnter = dateFormat.parse(dateEnterVal);
                            trade.setDateEnter(dateEnter);
                        } catch (ParseException ex) {
                            validateMessages.add("date enter format is dd-MM-yyyy");
                        }
                        break;
                    case VALUE_NUMBER:
                        validateMessages.add("date enter must be string");
                        break;
                    default:
                        break;
                }
            }//--- end parsing date enter

            //--- parsing time enter of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("time enter")) {
                event = parser.next();
                //--- validation time enter's field
                switch (event) {
                    case VALUE_STRING:
                        String timeEnterVal = parser.getString();
                        try {
                            Date timeEnter = timeFormat.parse(timeEnterVal);
                            trade.setTimeEnter(timeEnter);
                        } catch (ParseException ex) {
                            validateMessages.add("time enter format is HH:mm 24h");
                        }
                        break;
                    case VALUE_NUMBER:
                        validateMessages.add("time enter must be a string");
                    default:
                        break;
                }
            }//--- end parsing time enter

            //--- parsing date stop of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("date stop")) {
                event = parser.next();
                //--- validation date stop's field
                switch (event) {
                    case VALUE_STRING:
                        String dateStopVal = parser.getString();
                        try {
                            Date dateStop = dateFormat.parse(dateStopVal);
                            trade.setDateStop(dateStop);
                        } catch (ParseException ex) {
                            validateMessages.add("date stop format is dd-MM-yyyy");
                        }
                        break;
                    case VALUE_NUMBER:
                        validateMessages.add("date stop must be a string");
                    default:
                        break;
                }
            }//--- end parsing date stop

            //--- parsing time stop of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("time stop")) {
                event = parser.next();
                //--- validation time stop's field
                switch (event) {
                    case VALUE_STRING:
                        String timeStopVal = parser.getString();
                        try {
                            Date timeStop = timeFormat.parse(timeStopVal);
                            trade.setTimeStop(timeStop);
                        } catch (ParseException ex) {
                            validateMessages.add("time stop format is HH:mm 24h");
                        }
                        break;
                    case VALUE_NUMBER:
                        validateMessages.add("time stop must be a string");
                    default:
                        break;
                }
            }//--- end parsing time stop

            //--- parsing description of trade
            if (event.equals(JsonParser.Event.KEY_NAME) && parser.getString().matches("description")) {
                event = parser.next();
                //--- validation description's field
                switch (event) {
                    case VALUE_STRING:
                        String description = parser.getString();
                        trade.setDescription(description);
                        break;
                    case VALUE_NUMBER:
                        validateMessages.add("description must be string");
                    default:
                        break;
                }
            }//--- end parsing description
        }
        if (!validateMessages.isEmpty()) {
            throw new JsonObjectParserValidationException(validateMessages);
        }
        return trade;
    }
}
