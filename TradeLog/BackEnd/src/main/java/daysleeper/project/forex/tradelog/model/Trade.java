package daysleeper.project.forex.tradelog.model;

import daysleeper.project.forex.tradelog.resources.TradeResource;
import daysleeper.project.forex.tradelog.service.LinkService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.UriInfo;

@Entity
@Table(name = "Trade")
public class Trade implements Serializable {

    //-------------------------------------------------
    // Constant value
    //-------------------------------------------------
    private static final long serialVersionUID = 1L;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat timestampFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.sss");

    //-------------------------------------------------
    // Fields
    //-------------------------------------------------
    private String id;
    private Symbol symbol;
    private Position position;
    private BigDecimal tradeSize;
    private BigDecimal enter;
    private BigDecimal takeProfit;
    private BigDecimal stoploss;
    private BigDecimal stop;
    private Date dateEnter;
    private Date timeEnter;
    private Date dateStop;
    private Date timeStop;
    private Date created;
    private Date lastUpdated;
    private String description;

    //-------------------------------------------------
    // Get & set method
    //-------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tradeId", updatable = false, length = 50)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tradeSymbol")
    @NotNull(message = "symbol is null")
    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tradePosition")
    @NotNull(message = "position is null")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Column(name = "tradeSize", precision = 3, scale = 2)
    @NotNull(message = "size is null")
    public BigDecimal getTradeSize() {
        return tradeSize;
    }

    public void setTradeSize(BigDecimal tradeSize) {
        this.tradeSize = tradeSize;
    }

    @Column(name = "tradeEnter", precision = 6, scale = 5)
    @NotNull(message = "price enter is null")
    public BigDecimal getEnter() {
        return enter;
    }

    public void setEnter(BigDecimal enter) {
        this.enter = enter;
    }

    @Column(name = "tp", precision = 6, scale = 5)
    public BigDecimal getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(BigDecimal takeProfit) {
        this.takeProfit = takeProfit;
    }

    @Column(name = "sl", precision = 6, scale = 5)
    public BigDecimal getStoploss() {
        return stoploss;
    }

    public void setStoploss(BigDecimal stoploss) {
        this.stoploss = stoploss;
    }

    @Column(name = "tradeStop", precision = 6, scale = 5)
    public BigDecimal getStop() {
        return stop;
    }

    public void setStop(BigDecimal stop) {
        this.stop = stop;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dateEnter")
    @NotNull(message = "date enter is null")
    public Date getDateEnter() {
        return dateEnter;
    }

    public void setDateEnter(Date dateEnter) {
        this.dateEnter = dateEnter;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "timeEnter")
    @NotNull(message = "time enter is null")
    public Date getTimeEnter() {
        return timeEnter;
    }

    public void setTimeEnter(Date timeEnter) {
        this.timeEnter = timeEnter;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dateStop")
    public Date getDateStop() {
        return dateStop;
    }

    public void setDateStop(Date dateStop) {
        this.dateStop = dateStop;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "timeStop")
    public Date getTimeStop() {
        return timeStop;
    }

    public void setTimeStop(Date timeStop) {
        this.timeStop = timeStop;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", updatable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastUpdated")
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Column(name = "description", length = 1500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //-------------------------------------------------
    // Constructor
    //-------------------------------------------------

    public Trade() {
    }

    //------------------------------------------------
    // Call back method
    //------------------------------------------------
    @PrePersist
    @PreUpdate
    private void timeRecord() {
        if (this.created == null) {
            setCreated(new Date());
        } else {
            setLastUpdated(new Date());
        }
    }

    //-------------------------------------------------
    // hashcode() & equals() & toString()
    //-------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trade)) {
            return false;
        }
        Trade other = (Trade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Trade id " + this.id);
    }

    //-------------------------------------------------
    // toJson()
    //-------------------------------------------------
    public JsonObject toJson(UriInfo uriInfo, boolean expand){
        JsonObjectBuilder b = Json.createObjectBuilder();
        b.add("id", this.id);
        b.add("symbol", this.symbol.toString());
        b.add("position", this.position.toString());
        if (!expand) {
            b.add("self", new LinkService().self(uriInfo, TradeResource.class, id));
        } else {
            //--- round the value
            b.add("size", this.tradeSize);
            b.add("enter", this.enter);
            if (this.takeProfit != null) {
                b.add("take profit", this.takeProfit);
            }
            if (this.stoploss != null) {
                b.add("stop loss", this.stoploss);
            }
            if (this.stop != null) {
                b.add("stop", this.stop);
            }
            b.add("date enter", dateFormat.format(this.dateEnter));
            b.add("time enter", timeFormat.format(this.timeEnter));
            if (this.dateStop != null) {
                b.add("date stop", dateFormat.format(this.dateStop));
            }
            if (this.timeStop != null) {
                b.add("time stop", timeFormat.format(this.timeStop));
            }
            b.add("created", timestampFormat.format(this.created));
            if (this.lastUpdated != null) {
                b.add("last updated", timestampFormat.format(this.lastUpdated));
            }
            if (this.description != null) {
                b.add("description", this.description);
            }
        }
        return b.build();
    }
}
