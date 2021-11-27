package com.colbymeline.internetmonitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InternetMonitor extends CMDRunner {
    private int ping;
    private String currentWiFi = "Voltron";
    public boolean checkingPeriodically = false;

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public String getCurrentWiFi() {
        return currentWiFi;
    }

    public void setCurrentWiFi(String currentWiFi) {
        this.currentWiFi = currentWiFi;
    }

    public boolean ping() {
        return runCMD("ping 8.8.8.8");
    }

    public void resetWiFi(int milliSeconds) {
        if (currentWiFi != null) {
            disconnectWiFi();
            Runnable reconnect = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(milliSeconds);
                        connectWiFi();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(reconnect).start();

            connectWiFi(currentWiFi);
        }
    }

    public void resetWiFi(int milliSeconds, String networkName) {
        this.currentWiFi = networkName;
        disconnectWiFi();
        Runnable reconnect = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(milliSeconds);
                    connectWiFi(networkName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(reconnect).start();
    }

    private void disconnectWiFi() {
        runCMD("netsh wlan disconnect");
    }

    public void connectWiFi(String networkName) {
        this.currentWiFi = networkName;
        runCMD("netsh wlan connect name=\"" + networkName + "\"");
    }

    public void connectWiFi() {
        if (this.currentWiFi != null) runCMD("netsh wlan connect name=\"" + this.currentWiFi + "\"");
        else System.out.println("Please set Current WiFi before using connectWiFi()");
    }

    public void checkWiFiPeriodically(int period) {
        this.checkingPeriodically = true;
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!ping()) {
                    resetWiFi(3000);
                }
            }
        }, 0, period, TimeUnit.MINUTES);
    }
}
