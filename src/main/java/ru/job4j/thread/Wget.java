package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;

    private final int speed;

    private final String folder;

    public Wget(String url, int speed) {
        this(url, speed, "");
    }

    public Wget(String url, int speed, String folder) {
        this.url = url;
        this.speed = speed;
        this.folder = folder;
    }

    @Override
    public void run() {
        String fileName = new File(url).getName();
        String outName = !folder.isEmpty()
                ? folder + File.separator + fileName
                : "tmp_" + fileName;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outName)) {
            byte[] dataBuffer = new byte[1024];
            int byteRead;
            long time = System.currentTimeMillis();
            while ((byteRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, byteRead);
                long difference = System.currentTimeMillis() - time;
                if (difference < speed) {
                    try {
                        Thread.sleep(speed - difference);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String folder = args[2];
        Thread wget = new Thread(new Wget(url, speed, folder));
        wget.start();
        wget.join();
    }
}