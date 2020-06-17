package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    ObservableList<StockModel> data = FXCollections.observableArrayList();






    public void printStockDatabase(){
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/saving","root","admin");
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery("select * from "+"saving.stock");

            while(rs.next()){
                data.add(new StockModel(rs.getInt("idstock"),
                        rs.getInt("balance"),
                        rs.getDate("date").toString(),
                        rs.getInt("debit"),
                        rs.getInt("credit")));


            }

        }catch (SQLException err){
            System.out.println(err.getMessage());
        }

        stockId.setCellValueFactory(new PropertyValueFactory<>("id"));
        stockId.setCellValueFactory(new PropertyValueFactory<>("balance"));
        stockId.setCellValueFactory(new PropertyValueFactory<>("date"));
        stockId.setCellValueFactory(new PropertyValueFactory<>("debit"));
        stockId.setCellValueFactory(new PropertyValueFactory<>("credit"));

        stockList.setItems(data);
    }

}
