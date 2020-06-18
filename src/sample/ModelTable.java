package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

// creating a model table class so that it can be inserted to the observable list for the main account table
// it needs a simpleString format not just string
// also works for the other datatype
// and just generate constructor , seter and getter

public class ModelTable {
    private SimpleIntegerProperty id;
    private SimpleStringProperty date;
    private SimpleIntegerProperty debit;
    private SimpleIntegerProperty credit;
    private SimpleStringProperty info;

    public ModelTable(Integer id, String date, Integer debit, Integer credit, String info) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date) ;
        this.debit = new SimpleIntegerProperty(debit);
        this.credit = new SimpleIntegerProperty(credit);
        this.info = new SimpleStringProperty(info);
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

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
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

    public String getInfo() {
        return info.get();
    }

    public SimpleStringProperty infoProperty() {
        return info;
    }

    public void setInfo(String info) {
        this.info.set(info);
    }
}


