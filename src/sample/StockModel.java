package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StockModel {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty balance;
    private SimpleStringProperty date;
    private SimpleIntegerProperty debit;
    private SimpleIntegerProperty credit;

    public StockModel(Integer id, Integer balance, String date, Integer debit, Integer credit) {
        this.id = new SimpleIntegerProperty(id);
        this.balance = new SimpleIntegerProperty(balance);
        this.date = new SimpleStringProperty(date);
        this.debit = new SimpleIntegerProperty(debit);
        this.credit = new SimpleIntegerProperty(credit);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getBalance() {
        return balance.get();
    }

    public SimpleIntegerProperty balanceProperty() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance.set(balance);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getDebit() {
        return debit.get();
    }

    public SimpleIntegerProperty debitProperty() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit.set(debit);
    }

    public int getCredit() {
        return credit.get();
    }

    public SimpleIntegerProperty creditProperty() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit.set(credit);
    }
}
