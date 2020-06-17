package sample;

import javafx.scene.control.Label;

public class MonthReport {
    public Label monthlySavedText;
    public Label monthlySpendText;

    public void setText(int saved, int spend){
        monthlySavedText.setText("Saved : "+saved);
        monthlySpendText.setText("Spend : "+spend);
    }
}
