package com.colbymeline.internetmonitor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class Controller {
    InternetMonitor internetMonitor = new InternetMonitor();
    private boolean checkingInternetPeriodically = false;
    private int periodMinutes;

    @FXML
    public ComboBox Period;

    @FXML
    public void onResetButtonClick(ActionEvent actionEvent) {
        internetMonitor.resetWiFi(3000);
    }

    @FXML
    public void onPingButtonClick(ActionEvent actionEvent) {
        internetMonitor.ping();
    }

    @FXML
    public void onPeriodDropdownClick(ActionEvent actionEvent) {
        String dropdownValue = Period.getValue().toString();

        try {
            this.periodMinutes = Integer.parseInt(dropdownValue.substring(6,8));
        } catch (Exception e) {
            try {
                if (Integer.parseInt(dropdownValue.substring(6,7)) == 5) this.periodMinutes = 5;
            } catch (Exception f) {
                this.periodMinutes = -1;
            }
        }
        System.out.println(this.periodMinutes);

    }
}