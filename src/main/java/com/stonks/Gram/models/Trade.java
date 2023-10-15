package com.stonks.Gram.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {
    
    private long tradeId;
    private Date entryDate;
    private Date exitDate;
    private double entryPrice;
    private double exitPrice;
    private int entrySize;
    private String comments;

    @Override
    public String toString() {
        return "Trade [entryDate=" + entryDate + ", exitDate=" + exitDate + ", entryPrice=" + entryPrice
                + ", exitPrice=" + exitPrice + ", entrySize=" + entrySize + ", comments=" + comments + "]";
    }
   
    
}
