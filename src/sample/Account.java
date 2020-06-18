package sample;


import javafx.collections.ObservableList;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Calendar;

public class Account implements Atm {
//    Making this class singleton , because only one instance needed
//    declaring the neccesary attributes that it needs
    private static Account instance = null;
//    database to access the function from the database class
    private Database database = Database.getInstance();
//    StockDatabase to access the function from the stock database class
    private StockDatabase stockDatabase = StockDatabase.getInstance();
//    Local date to get the local date at the current moment to insert to the database
    private LocalDate startingDate = getStartingDate();
//    previous check week, to get the weekly report , this attribute become the starting range
    private Date previousCheckWeek = getPreviousWeek();
//    previous check , to get the monthly report , this attribute become the starting range
    private Date previousCheckMonth = getPreviousMonth();

// a function to make it a singleton
    public static Account getInstance(){
        if(instance==null){
            instance = new Account();
        }
        return instance;
    }


// this function to get the starting date , the starting date is the first date that the database is inputted
    public LocalDate getStartingDate(){
        return database.getStartingDate().toLocalDate();
    }

//    to get the previous week , i just take the starting date and minus 1 week
    public Date getPreviousWeek() {
        Date prevWeek = Date.valueOf(startingDate.minusWeeks(1));
        return prevWeek;
    }
// to get the previous month , i just take the starting date and minus 1 month
    public Date getPreviousMonth(){
        Date prevMonth = Date.valueOf(startingDate.minusMonths(1));
        return prevMonth;
    }

//    this is the function to get the current date
    public LocalDate getDateMonthYear() {
        return LocalDate.now();
    }

//    this the function to reset the database , mainly use for testing the program
    public void deleteDatabase() {
        database.deleteData();
    }

//    this is the function to print the databse into the console , mainly for debugging
    public void printDatabase() {
        database.printAll();
    }




//    this is the function for getting the saving report on how much you saved on that period
//    , it can get weekly or monthly report , just enter the starting range
//    of the date
    @Override
    public int getReport(Date previousCheck) {
        Date sqlDate = Date.valueOf(getDateMonthYear());
        int amount = database.getSaved(sqlDate, previousCheck);
        return amount;
    }
    //    this is the function for getting the spending report on how much you spent on that period
//    , it can get weekly or monthly report , just enter the starting range
//    of the date

    @Override
    public int getSpending(Date previousCheck) {
        Date sqlDate = Date.valueOf(getDateMonthYear());
        int amount = database.getSpending(sqlDate, previousCheck);
        return amount;
    }


//    this function works to get ur weekly saving , and take 20% of it and send it to the stock account
//    and also deduct 20% of your main account
    @Override
    public int sendStockMoney(StockAccount s) {

        int weeklySaving = getReport(previousCheckWeek) * 20 / 100;
        stockDatabase.insert(Date.valueOf(getDateMonthYear()),weeklySaving,'d');
        database.insert(Date.valueOf(getDateMonthYear()),weeklySaving,"Sent to Stock Account",'c');
        return weeklySaving;

    }

//    this is the function to insert transaction that you provided and insert it to mysql database
    public void insert(Date date,int amount , String information, char type){
        database.insert(date,amount,information,type);
    }
}





