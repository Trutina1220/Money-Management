package sample;

import javafx.scene.control.Label;

public class WeeklyReport {
    public Label savedText;
    public Label spendText;
    public Label stockText;



    public void transferMessage(int saved, int spent, int sendStockMoney ){
        savedText.setText("Saved : "+saved);
        spendText.setText("Spent : "+spent);
        stockText.setText("Stocks : "+sendStockMoney);

    }


}
