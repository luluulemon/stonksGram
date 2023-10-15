package com.stonks.Gram.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.stonks.Gram.repos.Queries.*;

import com.stonks.Gram.models.Trade;

@Repository
public class TradeRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveTrade(Trade trade, String tradeId){
        jdbcTemplate.update(SQL_ADD_TRADE, 
                            tradeId,
                            "lulu",     // TO-DO: add session storage for User
                            trade.getEntryDate(),
                            trade.getExitDate(),
                            trade.getEntryPrice(),
                            trade.getExitPrice(),
                            trade.getEntrySize(),
                            trade.getComments());
    }

    /* 
    tradeId varchar(8) not null, 
    username varchar(64) not null,
	entryDate date not null, 
	exitDate date, 
	entryPrice float not null, 
	exitPrice float, 
    entrySize int not null,
	comments text, 
    */
}
