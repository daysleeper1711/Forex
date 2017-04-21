package daysleeper.project.forex.tradelog.service;

import daysleeper.project.forex.tradelog.model.Position;
import daysleeper.project.forex.tradelog.model.Symbol;
import daysleeper.project.forex.tradelog.model.Trade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class TradeService extends AbstractService<Trade>{
    
    public TradeService() {
        super(Trade.class);
    }
    
    public String idGenerator() {
        return "T-" + new Date().getTime() + "-" + new Random().nextInt(9999);
    }
    
    @Override
    public Predicate[] getPredicates(Root<Trade> root, Trade example) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        Symbol symbol = example.getSymbol();
        if (symbol != null) {
            predicates.add(cb.equal(root.<Symbol> get("symbol"), symbol));
        }
        Position position = example.getPosition();
        if (position != null) {
            predicates.add(cb.equal(root.<Position> get("position"), position));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    
}
