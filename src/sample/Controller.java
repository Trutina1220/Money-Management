package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

//this is the main controller for the main window ,or for the sample.fxml

public class Controller implements Initializable {

// taking all the table , and the column from the sample.fxml or scene builder to manipulate it
    public TableView transactionList;
    public TableColumn <ModelTable,Integer>idColumn;
    public TableColumn <ModelTable,String>dateColumn;
    public TableColumn <ModelTable,Integer>debitColumn;
    public TableColumn <ModelTable,Integer>creditColumn;
    public TableColumn <ModelTable,String>infoColumn;
    public TextField amountText,infoText;



//get main account instance to use the function
    Account mainAccount = Account.getInstance();
//    get stock account instance to use the function
    StockAccount stockAccount = StockAccount.getInstance();
// create the observable list so that it can keep track to the inserted transaction
    ObservableList<ModelTable> data = FXCollections.observableArrayList();
//   making a static id so , that you don't need to input the id
    static int id ;

//    a function that happens when you click the debit button
//    basiclly get the current date from Account class function

    public void debitButton(ActionEvent event){
        int amount = 0;
        try {
//            getting the amount from the amount text field
            amount = Integer.parseInt(amountText.getText());
        }catch (Exception e ){
            amount=0;
        }
        //    basiclly get the current date from Account class function
        String date =mainAccount.getDateMonthYear().toString();
//        increse the id by one to keep track
        id += 1;
//        get the information from the information text field
        String info = infoText.getText();
//        throw an alert if all data is not inputted
        if (info.isEmpty() || amount<=0 ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning");
            a.setContentText("All data must be filled");
            a.show();
        }

        else{
//            adding data to the observable list
        data.add(new ModelTable(id,date,amount,0,info));
//        inserting the data to mysql database
        mainAccount.insert(Date.valueOf(mainAccount.getDateMonthYear()),amount,info,'d');}
    }

    public void creditButton(ActionEvent event){
        int amount = 0;
        try {
            //            getting the amount from the amount text field
            amount = Integer.parseInt(amountText.getText());
        }catch (Exception e ){
            amount=0;
        }
        //    basiclly get the current date from Account class function
        String date =mainAccount.getDateMonthYear().toString();
        id += 1;
        //        get the information from the information text field
        String info = infoText.getText();

        //        throw an alert if all data is not inputted
        if (info.isEmpty() || amount <= 0  ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning");
            a.setContentText("All data must be filled");
            a.show();
        }

        else{
            //            adding data to the observable list
        data.add(new ModelTable(id,date,0,amount,info));
            //        inserting the data to mysql database
        mainAccount.insert(Date.valueOf(mainAccount.getDateMonthYear()),amount,info,'c');}
    }

// a function to show a stock window for my stock account
//    happens when you click the stock account button
    public void stockButton(ActionEvent event) throws IOException {
        showStockWindow();
    }



//all of this function will be executed on the beginning of the program

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        a function to make to check the week
//        if its fulfilled , it will popup the new window of the weeklyreport
        if (checkWeek()){
           showWeeklyReport();
        }
//        a function to make to check the month
//        if its fulfilled , it will popup the new window of the monthly report
        if(checkMonth()){
            showMonthlyReport();
// inserting to the observable list to display the data from my sql database on the startup of
//            the program
        printDatabase();
        }

    }

//    a function to created new window , and show the weekly report
    public void showWeeklyReport(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WeekReport.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//getting the saved weekly from mainAccount
        int saved = mainAccount.getReport(mainAccount.getPreviousWeek());
//        getting the spent weekly from mainAccount
        int spending = mainAccount.getSpending(mainAccount.getPreviousWeek());
//        getting the stock money to be sent to the stock account from mainAccount
        int stockMoney = mainAccount.sendStockMoney(stockAccount);
//        getting the controller for the weekly report window
        WeeklyReport weekController = fxmlLoader.getController();
//set the text for the spent, spend , and sent stock money from the account
        weekController.transferMessage(saved,spending,stockMoney);


        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
//        make the window always on top
        stage.setAlwaysOnTop(true);

        stage.show();
    }
    //    a function to created new window , and show the monthly report
    public void showMonthlyReport(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MonthReport.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//getting the saved monthly from mainAccount
        int saved = mainAccount.getReport(mainAccount.getPreviousMonth());
//        getting the spent monthly from main Account
        int spending = mainAccount.getSpending(mainAccount.getPreviousMonth());

//        getting the month report window controller
        MonthReport monthController = fxmlLoader.getController();
//        set the text with the correct value of the monthly report
        monthController.setText(saved,spending);

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
//        make the window always on top
        stage.setAlwaysOnTop(true);

        stage.show();
    }

//    a function to show the stock Account winddows
    public void showStockWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StockWindow.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

//getting the controller from the stock account
        StockWindow stockController = fxmlLoader.getController();
//        make the stock account listbox , to display the data on startup
        stockController.printStockDatabase();

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
//        making it always on top
        stage.setAlwaysOnTop(true);

        stage.show();
    }

//    this is the function to check the week if its already one week passed
    public boolean checkWeek(){
        Date previousWeek = mainAccount.getPreviousWeek();
        LocalDate prevWeekLocal = previousWeek.toLocalDate();
        Date nextWeekCheck = Date.valueOf(prevWeekLocal.plusWeeks(1));
        String prevWeekString = previousWeek.toString();
        String nextWeekString = nextWeekCheck.toString();
        if(nextWeekString.equals("2020-06-09")){
            return true;
        }
        else{
            return false;
        }

    }

//    this is the function to check if already one month passed
    public boolean checkMonth(){
        Date previousMonth = mainAccount.getPreviousMonth();
        LocalDate prevMonthLocal = previousMonth.toLocalDate();
        Date nextMonthCheck = Date.valueOf(prevMonthLocal.plusMonths(1));
        String prevMonthString = previousMonth.toString();
        String nextMonthString = nextMonthCheck.toString();
        if(nextMonthString.equals("2020-06-09")){
            return true;
        }
        else{
            return false;
        }
    }

//    this function is for inserting the table on the windows to display the data
//    take the data from the databse
//    and insert the data to the apropriate column

    public void printDatabase(){

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/saving","root", "admin");
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery("select * from " + "saving.report");

            while (rs.next()) {
                data.add(new ModelTable(rs.getInt("id"),rs.getDate("date").toString()
                        ,rs.getInt("debit"),rs.getInt("credit"),rs.getString("information")));

                id = rs.getInt("id");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        debitColumn.setCellValueFactory(new PropertyValueFactory<>("debit"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        infoColumn.setCellValueFactory(new PropertyValueFactory<>("info"));

        transactionList.setItems(data);
    }
}
