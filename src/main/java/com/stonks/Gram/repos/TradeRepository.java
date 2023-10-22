package com.stonks.Gram.repos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.stonks.Gram.repos.Queries.*;

import java.util.List;

import com.stonks.Gram.entities.Trade;
import com.stonks.Gram.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class TradeRepository {

    /* 
	tradeId varchar(8) not null, 
    username varchar(64) not null,
	entryDate date not null, 
	exitDate date, 
	entryPrice float not null, 
	exitPrice float, 
    tradeSize int not null,
    tradeType varchar(64),
	comments text, 
    tradePicsList varchar(256) not null,
    pnL float,
    */


    // Using JPA here 
    @Autowired
    private EntityManager em;


    public List<Trade> loadTradesJpa(User user){
        TypedQuery<Trade> loadTradesQuery = em.createNamedQuery("Load Trades", Trade.class);
        loadTradesQuery.setParameter("username", user.getUserId());
        return loadTradesQuery.getResultList();
    }


    public com.stonks.Gram.entities.Trade findTradeByIdJpa(String tradeId){
        return em.find(com.stonks.Gram.entities.Trade.class, tradeId);
        
    }


    @Transactional
    public void saveTradeJpa(Trade trade){
        if(trade.getExitDate() != null)
        {   trade.setPnL(
                ( trade.getExitPrice() - trade.getEntryPrice() ) * trade.getTradeSize()
            );
        }
        trade.setUsername("lulu");  // take session username
        em.persist(trade);
    }


    @Transactional
    public void updateTradeJpa(Trade trade){
        if(trade.getExitDate() != null)
        {   trade.setPnL(
                ( trade.getExitPrice() - trade.getEntryPrice() ) * trade.getTradeSize()
            );
        }
        trade.setUsername("lulu");  // take session username
        em.merge(trade);
    }


    @Transactional
    public void deleteTradeJpa(String tradeId){

        com.stonks.Gram.entities.Trade trade = findTradeByIdJpa(tradeId);
        if(trade!=null){
            em.remove(trade);    
            System.out.println("Removed " + trade.getTradeId());
        }
        else{ System.out.println("Nothing removed");  }
    }



    /* unused functions
     * Using JdbcTemplate directly Before JPA setup
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void saveTrade(Trade trade){
    // set PnL for closed trades
    if(trade.getExitDate() != null)
    {   trade.setPnL(
            ( trade.getExitPrice() - trade.getEntryPrice() ) * trade.getTradeSize()
        );
    }

    jdbcTemplate.update(SQL_ADD_TRADE, 
                        trade.getTradeId(),
                        "lulu",    
                        trade.getEntryDate(),
                        trade.getExitDate(),
                        trade.getEntryPrice(),
                        trade.getExitPrice(),
                        trade.getTradeSize(),
                        trade.getTradeType(),
                        trade.getComments(),
                        trade.getTradePicsList(),
                        trade.getPnL());
    }

    public void deleteTrade(Trade trade){
        if(jdbcTemplate.update(SQL_DELETE_TRADE, trade.getTradeId()) == 1)
        {   System.out.println("Deleted " + trade.getTradeId());};  // log for trade deleted
    }

    public boolean findTradeById(Trade trade){
        return jdbcTemplate.queryForRowSet(SQL_FIND_TRADE_BY_ID, trade.getTradeId()).next();
    }

    public List<Trade> loadTrades(User user){
        return jdbcTemplate.query(SQL_LOAD_TRADES, 
                                new BeanPropertyRowMapper(Trade.class), 
                                user.getUserId());
    }

    public void updateTrade(Trade trade){
        // set PnL for closed trades
        if(trade.getExitDate() != null)
        {   trade.setPnL(
                ( trade.getExitPrice() - trade.getEntryPrice() ) * trade.getTradeSize()
            );
        }

        jdbcTemplate.update(SQL_UPDATE_TRADE, 
                    "lulu",    
                            trade.getEntryDate(),
                            trade.getExitDate(),
                            trade.getEntryPrice(),
                            trade.getExitPrice(),
                            trade.getTradeSize(),
                            trade.getTradeType(),
                            trade.getComments(),
                            trade.getTradePicsList(),
                            trade.getPnL(),
                            trade.getTradeId());
    }
}
