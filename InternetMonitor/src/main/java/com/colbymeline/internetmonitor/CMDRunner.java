package com.colbymeline.internetmonitor;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class CMDRunner {

    public boolean runCMD(String cmd) {
        try {
            log(cmd);
            Process process = Runtime.getRuntime().exec(cmd);
            boolean failed = logOutput(process.getInputStream(),"").intValue() > 2;
            logOutput(process.getErrorStream(), "Error: ");
            process.waitFor();
            return failed;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private AtomicInteger logOutput(InputStream inputStream, String prefix) {
        AtomicInteger failures = new AtomicInteger();
        new Thread(() -> {
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                synchronized (this) {
                    String text = scanner.nextLine();
                    log(prefix + text);
                    if (text.contains("General Failure")) failures.getAndIncrement();
                }
            }
            scanner.close();
        }).start();
        return failures;
    }

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSS");

    private synchronized void log(String message) {
        System.out.println(format.format(
                new Date()) + ": " + message);
    }
}
