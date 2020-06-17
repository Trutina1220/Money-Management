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


public class Controller implements Initializable {

    public TableView transactionList;
    public TableColumn <ModelTable,Integer>idColumn;
    public TableColumn <ModelTable,String>dateColumn;
    public TableColumn <ModelTable,Integer>debitColumn;
    public TableColumn <ModelTable,Integer>creditColumn;
    public TableColumn <ModelTable,String>infoColumn;
    public TextField amountText,infoText;
    public Label savedText;
    public Label spendText;



    Account mainAccount = Account.getInstance();
    StockAccount stockAccount = StockAccount.getInstance();

    ObservableList<ModelTable> data = FXCollections.observableArrayList();
    static int id ;

    public void debitButton(ActionEvent event){
        int amount = 0;
        try {
            amount = Integer.parseInt(amountText.getText());
        }catch (Exception e ){
            amount=0;
        }
        String date =mainAccount.getDateMonthYear().toString();
        id += 1;
        String info = infoText.getText();
        if (info.isEmpty() || amount==0 ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning");
            a.setContentText("All data must be filled");
            a.show();
        }

        else{
        data.add(new ModelTable(id,date,amount,0,info));
        mainAccount.insert(Date.valueOf(mainAccount.getDateMonthYear()),amount,info,'d');}
    }

    public void creditButton(ActionEvent event){
        int amount = 0;
        try {
            amount = Integer.parseInt(amountText.getText());
        }catch (Exception e ){
            amount=0;
        }
        String date =mainAccount.getDateMonthYear().toString();
        id += 1;
        String info = infoText.getText();

        if (info.isEmpty() || amount == 0  ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning");
            a.setContentText("All data must be filled");
            a.show();
        }

        else{
        data.add(new ModelTable(id,date,0,amount,info));
        mainAccount.insert(Date.valueOf(mainAccount.getDateMonthYear()),amount,info,'c');}
    }


    public void stockButton(ActionEvent event) throws IOException {
        showStockWindow();
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printDatabase();
        if (checkWeek()){
           showWeeklyReport();
        }

        if(checkMonth()){
            showMonthlyReport();

        }



    }

    public void showWeeklyReport(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WeekReport.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int saved = mainAccount.getReport(mainAccount.getPreviousWeek());
        int spending = mainAccount.getSpending(mainAccount.getPreviousWeek());
        int stockMoney = mainAccount.sendStockMoney(stockAccount);
        WeeklyReport weekController = fxmlLoader.getController();
        weekController.transferMessage(saved,spending,stockMoney);

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setAlwaysOnTop(true);

        stage.show();
    }

    public void showMonthlyReport(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MonthReport.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int saved = mainAccount.getReport(mainAccount.getPreviousMonth());
        int spending = mainAccount.getSpending(mainAccount.getPreviousMonth());

        MonthReport monthController = fxmlLoader.getController();
        monthController.setText(saved,spending);

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setAlwaysOnTop(true);

        stage.show();
    }

    public void showStockWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StockWindow.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        StockWindow stockController = fxmlLoader.getController();
        stockController.printStockDatabase();

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setAlwaysOnTop(true);

        stage.show();
    }

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
