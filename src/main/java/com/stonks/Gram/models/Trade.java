package com.stonks.Gram.models;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {
    
    private String tradeId;
    private Date entryDate;
    private Date exitDate;
    private double entryPrice;
    private double exitPrice;
    private int tradeSize;
    private String tradeType;
    private String comments;
    private String tradePicsList;
    private double pnL;
    
    

    public Trade() {}


    @Override
    public String toString() {
        return "Trade [tradeId=" + tradeId + ", entryDate=" + entryDate + ", exitDate=" + exitDate + ", entryPrice="
                + entryPrice + ", exitPrice=" + exitPrice + ", tradeSize=" + tradeSize + ", tradeType=" + tradeType
                + ", comments=" + comments + ", tradePicsList=" + tradePicsList + ", pnL=" + pnL + "]";
    }
   
    
}
