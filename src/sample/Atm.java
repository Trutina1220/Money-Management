package sample;

import java.sql.Date;

public interface Atm {
    void sendMoney(Account a ,int amount);
    int getReport(Date previousCheck);
    int getSpending(Date previousCheck);
    int sendStockMoney( StockAccount s );

}
