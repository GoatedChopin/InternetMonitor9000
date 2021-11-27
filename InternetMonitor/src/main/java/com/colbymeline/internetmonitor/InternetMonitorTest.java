package com.colbymeline.internetmonitor;

public class InternetMonitorTest {
    public static void main(String[] args) {
        InternetMonitor internetMonitor = new InternetMonitor();
        internetMonitor.connectWiFi();
        internetMonitor.setCurrentWiFi("Voltron");
        internetMonitor.resetWiFi(3000);
    }
}
