package sample;

import com.mysql.cj.xdevapi.Table;
import com.sun.webkit.Timer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Array;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;




public class Controller implements Initializable {

    public TableView transactionList;
    public TableColumn <ModelTable,Integer>idColumn;
    public TableColumn <ModelTable,String>dateColumn;
    public TableColumn <ModelTable,Integer>debitColumn;
    public TableColumn <ModelTable,Integer>creditColumn;
    public TableColumn <ModelTable,String>infoColumn;
    public TextField amountText,infoText;

    Account mainAccount = Account.getInstance();

    ObservableList<ModelTable> data = FXCollections.observableArrayList(

    );
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





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printDatabase();


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
