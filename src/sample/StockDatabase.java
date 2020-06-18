package sample;



import java.sql.*;

public class StockDatabase {
//    making it a singleton because only one
    private static StockDatabase instance = null;
//    host attributes to set the host for mysql
    private String host = "jdbc:mysql://localhost:3306/saving";
//    username attributes for the username of mysql
    private String uName = "root";
//    password attributes for the password of mysql
    private String uPass = "admin";
//    tablename for the tablename in mysql
    private String tableName = "saving.stock";
//    mysql syntax for getting the content of the table
    private String sql = "select * from ";
//    mysql syntax to insert to mysql table
    private String query = "insert into saving.stock (date, balance ,debit, credit)"
            + " values (?, ?, ?, ?)";
//    mysql syntax to delete the content of the table
    private String delete = "delete from ";


//making it a singleton class
    public static StockDatabase getInstance(){
        if(instance==null){
            instance = new StockDatabase();
        }
        return instance;
    }


//method to print all the content of the databse to the console , mainly use for debugging
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
//    method to get the current balance of the stock Account

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

//    method to insert to the stock database
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

//    method to delete all the content of the databse
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

