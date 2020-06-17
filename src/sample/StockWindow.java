package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StockWindow {

    public TableView stockList;
    public TableColumn<StockModel,Integer> stockId;
    public TableColumn<StockModel,Integer> stockBalance;
    public TableColumn<StockModel,String> stockDate;
    public TableColumn<StockModel,Integer> stockDebit;
    public TableColumn<StockModel,Integer> stockCredit;
    public StockAccount stockAccount = StockAccount.getInstance();

    public TextField amountText;
    static int id;
    static Integer balance;
    static int counter=0;
    ObservableList<StockModel> data = FXCollections.observableArrayList();

    public void creditButton(ActionEvent event){
        int amount = 0;
        try {
            amount = Integer.parseInt(amountText.getText());
        }catch (Exception e ){
            amount=0;
        }
        String date =stockAccount.getDateMonthYear().toString();
        id += 1;


        if ( amount == 0  ) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning");
            a.setContentText("All data must be filled");
            a.show();
        }

        else{
            balance -= amount;
            data.add(new StockModel(id,balance,date,0,amount));
            stockAccount.insertDatabase(amount,'c');}
    }



    public void printStockDatabase(){


        try{
            stockList.getItems().clear();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/saving","root","admin");
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery("select * from "+"saving.stock");

            while(rs.next()){
                data.add(new StockModel(rs.getInt("idstock"),
                        rs.getInt("balance"),
                        rs.getDate("date").toString(),
                        rs.getInt("debit"),
                        rs.getInt("credit")));
                id = rs.getInt("idstock");
                balance = rs.getInt("balance");
            }

        }catch (SQLException err){
            System.out.println(err.getMessage());
        }

        this.stockId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.stockBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        this.stockDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.stockDebit.setCellValueFactory(new PropertyValueFactory<>("debit"));
        this.stockCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));

        try {
            this.stockList.setItems(data);
        }catch (Exception e){
            System.out.println("data can't be setted");
        }
    }

}
