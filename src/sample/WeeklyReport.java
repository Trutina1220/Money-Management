package sample;

import javafx.scene.control.Label;

//this is the controller for the weekly report window
public class WeeklyReport {
//    this is the instance to get all the label text creating on the WeeklyReport.fxml or the scenebuilder
    public Label savedText;
    public Label spendText;
    public Label stockText;


//this function to set the message with the correct amount from databse
    public void transferMessage(int saved, int spent, int sendStockMoney ){
        savedText.setText("Saved : "+saved);
        spendText.setText("Spent : "+spent);
        stockText.setText("Stocks : "+sendStockMoney);

    }


}
