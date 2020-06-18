package sample;

import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class Database {
//    declaring the attributes
//    make it singleton , because only one instance is needed
    private static Database instance = null;
//    the host string for my sql database
    private String host = "jdbc:mysql://localhost:3306/saving";
//    the username of my sql database
    private String uName = "root";
//    the password of my sql database
    private String uPass = "admin";
//    the table name of my sql databse
    private String tableName = "saving.report";
//     mysql syntax to access my sql databse
    private String sql = "select * from ";
//    mysql syntax to insert to mysql databse
    private String query = "insert into saving.report (date, debit, credit, information)"
            + " values (?, ?, ?, ?)";
//    mysql syntax to delete content of mysql databse
    private String delete = "delete from ";

    static int weekCounter = 0 ;
    static int monthCounter = 0;

    public void increaseWeekCounter(){
        weekCounter++;
    }

    public void increaseMonthCounter(){
        monthCounter++;
    }



//function to make it a singleton class
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }



// a function to printall the databse to the console , to make it easy for debugging
    public void printAll() {
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery(sql + this.tableName);

            while (rs.next()) {
                int id = rs.getInt("id");
                Date date = rs.getDate("date");
                int debet = rs.getInt("debit");
                int credit = rs.getInt("credit");
                String desc = rs.getString("information");
                String t = id + " " + date + " " + debet + "  " + credit + "  " + desc;
                System.out.println(t);

            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }


//    a function to insert to the databse , with the data that is provided
    public void insert(Date date, int amount, String string, char type) {
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDate(1, date);
            if (type == 'd') {
                preparedStatement.setInt(2, amount);
                preparedStatement.setInt(3, 0);
            } else {
                preparedStatement.setInt(3, amount);
                preparedStatement.setInt(2, 0);
            }
            preparedStatement.setString(4, string);
            preparedStatement.execute();
            con.close();


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

//    a function to delete all the content from the databse
    public void deleteData() {
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            PreparedStatement preparedStatement = con.prepareStatement(delete + this.tableName);
            preparedStatement.execute();
            con.close();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    // a function to get how much you saved on that period of time
    public int getSaved(Date startPeriod, Date endPeriod) {
        int saved = 0;
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql + this.tableName);


            while (rs.next()) {
                Date tableDate = rs.getDate("date");
                int debit = rs.getInt("debit");
                int credit = rs.getInt("credit");
                if (tableDate.before(endPeriod) && tableDate.after(startPeriod)) {
                    saved += debit;
                    saved -= credit;
                }

            }
            return saved;


        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return saved;
        }

    }

//     a function on how much you spend on that period of time
    public int getSpending(Date startPeriod, Date endPeriod) {
        int spending = 0;
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql + this.tableName);


            while (rs.next()) {
                Date tableDate = rs.getDate("date");
                int debit = rs.getInt("debit");
                int credit = rs.getInt("credit");
                if (tableDate.before(endPeriod) && tableDate.after(startPeriod)) {
                    spending += credit;
                }

            }
            return spending;


        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return spending;
        }

    }

//    a function to get the first date that the daa is inputted
    public LocalDate getStartingDate() {
        LocalDate tableDate = null ;

        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql + this.tableName);


            while (rs.next()) {
                tableDate = rs.getDate("date").toLocalDate();
            }


        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return tableDate;
        }
        return tableDate;
    }
    public LocalDate get1MonthAfterDate(){
        return getStartingDate().plusMonths(monthCounter);
    }

//    getting the a week after the date
    public LocalDate get1WeekAfterDate(){
        return getStartingDate().plusWeeks(weekCounter);
    }

//    get a month after the current date



}





