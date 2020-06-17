package sample;

import javafx.scene.control.Label;

public class WeeklyReport {
    public Label savedText;
    public Label spendText;



    public void transferMessage(int saved, int spent ){
        savedText.setText("Saved : "+saved);
        spendText.setText("Spent : "+spent);
    }


}
