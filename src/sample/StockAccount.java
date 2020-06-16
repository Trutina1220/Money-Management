package sample;

import java.sql.Date;
import java.util.Calendar;

public class StockAccount extends Account {
    private StockDatabase sDB = StockDatabase.getInstance();
    private static StockAccount instance = null;

    public static StockAccount getInstance(){
        if(instance==null){
            instance = new StockAccount();
        }
        return instance;
    }

    public void buyStock(int amount){

        sDB.insert(Date.valueOf(getDateMonthYear()),amount,'c');

    }

    @Override
    public int getBalance() {
        return sDB.getBalance();
    }

    @Override
    public void deleteDatabase() {
        sDB.deleteData();
    }

    @Override
    public void printDatabase() {
        sDB.printAll();
    }

    public void insertDatabase(int amount , char type){
        Date sqlDate = Date.valueOf(getDateMonthYear());
        sDB.insert(sqlDate,amount,type);
    }
}


