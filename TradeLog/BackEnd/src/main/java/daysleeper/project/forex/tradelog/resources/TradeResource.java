/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daysleeper.project.forex.tradelog.resources;

import daysleeper.project.forex.tradelog.exceptions.JsonObjectValidationException;
import daysleeper.project.forex.tradelog.model.Position;
import daysleeper.project.forex.tradelog.model.Symbol;
import daysleeper.project.forex.tradelog.model.Trade;
import daysleeper.project.forex.tradelog.service.LinkService;
import daysleeper.project.forex.tradelog.service.TradeService;
import daysleeper.project.forex.tradelog.utilies.JsonObjectParser;
import daysleeper.project.forex.tradelog.utilies.Notification;
import daysleeper.project.forex.tradelog.utilies.NotificationLv;
import daysleeper.project.forex.tradelog.utilies.Pagination;
import daysleeper.project.forex.tradelog.utilies.PaginationLinks;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("trades")
public class TradeResource {

    @Context
    UriInfo info;

    @EJB
    private TradeService ts;

    @GET
    public Response getTrades(@QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @BeanParam GeneralInput example) {
        Trade t = new Trade();
        String symbolVal = example.symbol;
        if (symbolVal != null && !"".equals(symbolVal)) {
            Symbol symbol = Symbol.valueOf(symbolVal.toUpperCase());
            t.setSymbol(symbol);
        }
        String positionVal = example.position;
        if (positionVal != null && !"".equals(positionVal)) {
            Position position = Position.valueOf(positionVal.toUpperCase());
            t.setPosition(position);
        }
        //--- total items
        Long items = ts.count(t);
        //--- pagination
        Pagination pagination = new Pagination(items.intValue(), size);
        Map<Integer, Integer> pageMap = pagination.getPageMap();
        int pages = pagination.getPages();
        Integer from = pageMap.get(page);
        //--- empty list return
        Notification notification = new Notification();
        if (from == null) {
            notification.setSuccess(true);
            notification.setNotificationLv(NotificationLv.INFO);
            notification.setMessage("List is empty");
            return Response.status(Response.Status.OK)
                    .entity(notification.toJson())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        List<Trade> result = ts.findCollection(t, from, size);
        //--- convert result to json
        JsonObjectBuilder result_toJson = Json.createObjectBuilder();
        //--- generate page link
        PaginationLinks links = new LinkService().pagination(info, TradeResource.class, pages, page, size);
        result_toJson.add("pagination links", links.toJson());
        JsonArrayBuilder list_toJson = Json.createArrayBuilder();
        for (Trade trade : result) {
            list_toJson.add(trade.toJson(info, false));
        }
        result_toJson.add("result", list_toJson);
        return Response.ok(result_toJson.build())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }//--- end

    @GET
    @Path("{id}")
    public Response getTrade(@PathParam("id") String id) {
        Trade trade = ts.findById(id);
        Notification notification = new Notification();
        if (trade == null) {
            notification.setSuccess(false);
            notification.setNotificationLv(NotificationLv.WARNING);
            notification.setMessage("Id" + id + "is not correct");
            return Response.ok(notification.toJson())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.ok(trade.toJson(info, true))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }//--- end 

    @POST
    public Response addTrade(JsonObject jsonInput) {
        JsonObjectParser parser = new JsonObjectParser();
        Trade trade = parser.jsonTradeParser(jsonInput);
        trade.setId(ts.idGenerator());
        Notification notification = new Notification();
        try {
            ts.persist(trade);
            notification.setSuccess(true);
            notification.setNotificationLv(NotificationLv.INFO);
            notification.setMessage("Success created");
            JsonObject jsonRes = Json.createObjectBuilder()
                    .add("notification", notification.toJson())
                    .add("trade", trade.toJson(info, false))
                    .build();
            return Response.ok(jsonRes)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            notification.setSuccess(false);
            notification.setNotificationLv(NotificationLv.ERROR);
            notification.setMessage("Some thing error while inserting");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(notification.toJson())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        //--- json return

    }//--- end

    @PUT
    @Path("{id}")
    public Response updateTrade(JsonObject jsonInput, @PathParam("id") String id) {
        Notification notification = new Notification();
        if (id == null) {
            notification.setSuccess(false);
            notification.setNotificationLv(NotificationLv.ERROR);
            notification.setMessage("Id is missing");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(notification.toJson())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        Trade trade = ts.findById(id);
        if (trade == null) {
            throw new JsonObjectValidationException("id" + id + "is not correct");
        }
        //--- object parser
        JsonObjectParser parser = new JsonObjectParser();
        Trade uTrade = parser.jsonTradeParser(jsonInput);
        trade.setSymbol(uTrade.getSymbol());
        trade.setPosition(uTrade.getPosition());
        trade.setTradeSize(uTrade.getTradeSize());
        trade.setEnter(uTrade.getEnter());
        trade.setTakeProfit(uTrade.getTakeProfit());
        trade.setStoploss(uTrade.getStoploss());
        trade.setStop(uTrade.getStop());
        trade.setDateEnter(uTrade.getDateEnter());
        trade.setDateStop(uTrade.getDateStop());
        trade.setTimeEnter(uTrade.getTimeEnter());
        trade.setTimeStop(uTrade.getTimeStop());
        trade.setDescription(uTrade.getDescription());
        //--- json response
        try {
            ts.merge(trade);
            notification.setSuccess(true);
            notification.setNotificationLv(NotificationLv.INFO);
            notification.setMessage("Success updated");
            JsonObject jsonRes = Json.createObjectBuilder()
                    .add("notification", notification.toJson())
                    .add("trade", trade.toJson(info, false))
                    .build();
            return Response.ok(jsonRes)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            notification.setSuccess(false);
            notification.setNotificationLv(NotificationLv.ERROR);
            notification.setMessage("Some thing error while updating");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(notification.toJson())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }//--- end
}
