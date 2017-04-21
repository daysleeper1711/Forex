package daysleeper.project.forex.tradelog.utilies;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class PaginationLinks {
    private String firstPage;
    private String nextPage;
    private String previousPage;
    private String lastPage;
    private String onePage;

    public PaginationLinks() {
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getOnePage() {
        return onePage;
    }

    public void setOnePage(String onePage) {
        this.onePage = onePage;
    }
    
    public JsonObject toJson() {
        JsonObjectBuilder links = Json.createObjectBuilder();
        if (this.firstPage != null) {
            links.add("fist", this.firstPage);
        }
        if (this.previousPage != null) {
            links.add("previous", this.previousPage);
        }
        if (this.nextPage != null) {
            links.add("next", this.nextPage);
        }
        if (this.lastPage != null) {
            links.add("last", this.lastPage);
        }
        if (this.onePage != null) {
            links.add("1 page", this.onePage);
        }
        return links.build();
    }
}
