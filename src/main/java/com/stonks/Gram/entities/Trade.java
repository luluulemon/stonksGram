package com.stonks.Gram.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQuery(name="Load Trades", query="SELECT u FROM Trade u WHERE u.username = :username")
@Getter
@Setter
@Table(name="trades")
public class Trade {
    
    @Id
    @Column(name="TRADEId")
    private String tradeId;
    private String username;

    @Column(name="entrydate")
    private Date entryDate;

    @Column(name="exitdate")
    private Date exitDate;

    @Column(name="entryprice")
    private double entryPrice;

    @Column(name="exitprice")
    private double exitPrice;

    @Column(name="tradesize")
    private int tradeSize;

    @Column(name="tradetype")
    private String tradeType;
    private String comments;

    @Column(name="tradepicslist")
    private String tradePicsList;
    private double pnL;

    // required default zero arg constructor
    public Trade() {}

    
}
