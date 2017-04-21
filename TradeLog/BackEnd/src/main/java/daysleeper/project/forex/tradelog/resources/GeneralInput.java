package daysleeper.project.forex.tradelog.resources;

import javax.ws.rs.QueryParam;

public class GeneralInput {
    @QueryParam("symbol")
    public String symbol;
    @QueryParam("position")
    public String position;
}
