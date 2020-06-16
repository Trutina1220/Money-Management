package sample;

import javafx.collections.ObservableList;

import java.sql.*;

public class Database {
    private static Database instance = null;
    private String host = "jdbc:mysql://localhost:3306/saving";
    private String uName = "root";
    private String uPass = "admin";
    private String tableName = "saving.report";
    private String sql = "select * from ";
    private String query = "insert into saving.report (date, debit, credit, information)"
            + " values (?, ?, ?, ?)";
    private String delete = "delete from ";


    public Database() {

    }

    public int getBalance() {
        int balance = 0;
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql + this.tableName);


            while (rs.next()) {

                int debit = rs.getInt("debit");
                balance += debit;
                int credit = rs.getInt("credit");
                balance -= credit;


            }
            return balance;

        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return balance;
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    public ObservableList<ModelTable> getInsertedObsList(ObservableList<ModelTable> obsList) {
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();

            ResultSet rs = stat.executeQuery(sql + this.tableName);

            while (rs.next()) {
                obsList.add(new ModelTable(rs.getInt("id"),
                        rs.getDate("date").toString(), rs.getInt("debit"), rs.getInt("credit"),
                        rs.getString("information")));


            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return obsList;


    }


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

    public int getSaved(Date currentDate, Date previousCheck) {
        int saved = 0;
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql + this.tableName);


            while (rs.next()) {
                Date tableDate = rs.getDate("date");
                int debit = rs.getInt("debit");
                int credit = rs.getInt("credit");
                if (tableDate.before(currentDate) && tableDate.after(previousCheck)) {
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

    public int getSpending(Date currentDate, Date previousCheck) {
        int spending = 0;
        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql + this.tableName);


            while (rs.next()) {
                Date tableDate = rs.getDate("date");
                int debit = rs.getInt("debit");
                int credit = rs.getInt("credit");
                if (tableDate.before(currentDate) && tableDate.after(previousCheck)) {
                    spending += credit;
                }

            }
            return spending;


        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return spending;
        }

    }

    public Date getStartingDate() {
        Date tableDate = Date.valueOf("2020-06-15");

        try {
            Connection con = DriverManager.getConnection(this.host, this.uName, this.uPass);
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(sql + this.tableName);


            while (rs.next()) {
                tableDate = rs.getDate("date");
                return tableDate;
            }


        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return tableDate;
        }
        return tableDate;
    }


}





