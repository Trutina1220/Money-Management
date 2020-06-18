package sample;

import java.sql.Date;


public class StockAccount extends Account {
// taking the stockdatabse class to be able to use the stockdatabse
    private StockDatabase sDB = StockDatabase.getInstance();

//    making it a singleton class , because only one instance is needed
    private static StockAccount instance = null;
    public static StockAccount getInstance(){
        if(instance==null){
            instance = new StockAccount();
        }
        return instance;
    }




//    a function to delete the content of the databse , mainly use for debugging
    @Override
    public void deleteDatabase() {
        sDB.deleteData();
    }
// a function to print the databse content mainly use for testing
    @Override
    public void printDatabase() {
        sDB.printAll();
    }

//   a function to insert to the stock databse
    public void insertDatabase(int amount , char type){
        Date sqlDate = Date.valueOf(getDateMonthYear());
        sDB.insert(sqlDate,amount,type);
    }
}


