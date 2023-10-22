package com.stonks.Gram.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.stonks.Gram.GramApplication;
import com.stonks.Gram.entities.Trade;
import com.stonks.Gram.repos.TradeRepository;

@SpringBootTest(classes=GramApplication.class)
public class TradeRepositoryTest {
    
    @Autowired
    private TradeRepository tradeRepo;


    @Test
	void contextLoads() {
        Trade trade = tradeRepo.findTradeByIdJpa("5f619852");
        assertEquals(24.33, trade.getExitPrice());
	}
}
