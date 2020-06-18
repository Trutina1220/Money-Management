package sample;

import javafx.scene.control.Label;

//this is the controller for the monthly report window

public class MonthReport {
    //    this is the instance to get all the label text creating on the MonthlyReport.fxml or the scenebuilder

    public Label monthlySavedText;
    public Label monthlySpendText;
//this function to set the message with the correct amount from databse

    public void setText(int saved, int spend){
        monthlySavedText.setText("Saved : "+saved);
        monthlySpendText.setText("Spend : "+spend);
    }
}
