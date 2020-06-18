package sample;

import java.sql.Date;

// when i first make this program , i used this interface to make it minimally have this main function
// becauce one of my main goal , on making this program is
// how i much spent per period of time , and how i much i saved
// and make a stock account so that it will automatically send it to that account
// because in real life scenario , my main account saving , and my stock account saving are two
//different account
public interface Atm {
    int getReport(Date previousCheck);
    int getSpending(Date previousCheck);
    int sendStockMoney( StockAccount s );
}
