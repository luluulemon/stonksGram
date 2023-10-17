package com.stonks.Gram.repos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.stonks.Gram.repos.Queries.*;

import java.util.LinkedList;
import java.util.List;

import com.stonks.Gram.models.Trade;
import com.stonks.Gram.models.User;

@Repository
public class TradeRepository {
    
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
                            "lulu",     // TODO: add session storage for User
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

    public List<Trade> loadTrades(User user){
        return jdbcTemplate.query(SQL_LOAD_TRADES, 
                                new BeanPropertyRowMapper(Trade.class), 
                                user.getUserId());

    }


    public void deleteTrade(Trade trade){
        if(jdbcTemplate.update(SQL_DELETE_TRADE, trade.getTradeId()) == 1)
        {   System.out.println("Deleted " + trade.getTradeId());};  // log for trade deleted
    }


    public boolean findTradeById(Trade trade){
        return jdbcTemplate.queryForRowSet(SQL_FIND_TRADE_BY_ID, trade.getTradeId()).next();
    }


    public void updateTrade(Trade trade){
        // set PnL for closed trades
        if(trade.getExitDate() != null)
        {   trade.setPnL(
                ( trade.getExitPrice() - trade.getEntryPrice() ) * trade.getTradeSize()
            );
        }

        jdbcTemplate.update(SQL_UPDATE_TRADE, 
                    "lulu",     // TODO: add session storage for User
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
