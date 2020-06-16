package sample;

import com.sun.jdi.Value;

import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
public class StockDatabase {
    private static StockDatabase instance = null;
    private String host = "jdbc:mysql://localhost:3306/saving";
    private String uName = "root";
    private String uPass = "admin";
    private String tableName = "saving.stock";
    private String sql = "select * from ";
    private String query = "insert into saving.stock (date, balance ,debit, credit)"
            + " values (?, ?, ?, ?)";
    private String delete = "delete from ";


    public StockDatabase() {

    }

    public static StockDatabase getInstance(){
        if(instance==null){
            instance = new StockDatabase();
        }
        return instance;
    }



    public void printAll(){
        try{
            Connection con = DriverManager.getConnection(this.host,this.uName,this.uPass);
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery(sql+this.tableName);

            while(rs.next()){
                int id = rs.getInt("idstock");
                Date date = rs.getDate("date");
                int balance = rs.getInt("balance");
                int debet = rs.getInt("debit");
                int credit = rs.getInt("credit");

                String t = id+" "+date+" "+balance+ " "+debet+ " "+credit;
                System.out.println(t);

            }

        }catch (SQLException err){
            System.out.println(err.getMessage());
        }

    }

    public int getBalance(){
        try{
            Connection con = DriverManager.getConnection(this.host,this.uName,this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql+this.tableName);
            int balance = 0;

            while(rs.next()){

                int debit = rs.getInt("debit");
                balance += debit;
                int credit = rs.getInt("credit");
                balance -= credit;


            }
            return balance;

        }catch (SQLException err){
            System.out.println(err.getMessage());
            return getBalance();
        }

    }



    public void insert(Date date,  int amount, char type) {
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDate(1,date);
            int activeBalance = getBalance();
            if(type=='d'){
                preparedStatement.setInt(3,amount);
                preparedStatement.setInt(4,0);
                activeBalance += amount;
            }
            else{
                preparedStatement.setInt(4,amount);
                preparedStatement.setInt(3,0);
                activeBalance -= amount;
            }
            preparedStatement.setInt(2,activeBalance);
            preparedStatement.execute();
            con.close();



        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteData(){
        try {
            Connection con = DriverManager.getConnection(this.host,this.uName,this.uPass);
            PreparedStatement preparedStatement =  con.prepareStatement(delete+this.tableName);
            preparedStatement.execute();
            con.close();
        }catch (SQLException err){
            System.out.println(err.getMessage());
        }

    }




}

