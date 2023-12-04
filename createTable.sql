create table trades (
    tradeid varchar(255) not null,
    username varchar(255),
    entryDate datetime(6),
    exitDate datetime(6),
    entryPrice float,
    exitPrice float,
    tradeSize integer,
    tradeType varchar(64),
    comments text,
    tradePicsList varchar(255),
    pnl float not null,
    last_update_date datetime(6),
    is_deleted boolean default false,
    primary key (tradeid)
)


create table logincredentials(
    username char(64) not null,
    password char(64) not null,
    primary key (username)
)