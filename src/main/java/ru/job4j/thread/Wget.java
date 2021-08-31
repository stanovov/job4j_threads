package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class Wget implements Runnable {

    private static final String PROMPT = "Use correct arguments: \n"
                                        + "-url -speed -folder_path";

    private final String url;

    private final int speed;

    private final String folder;

    public Wget(String url, int speed) {
        this(url, speed, "");
    }

    public Wget(String url, int speed, String folder) {
        validate(url, speed, folder);
        this.url = url;
        this.speed = speed;
        this.folder = folder;
    }

    private void validate(String url, int speed, String folder) {
        if (!isValidURL(url)) {
            throw new IllegalArgumentException("Invalid URL.\n" + PROMPT);
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Invalid speed, should be positive.\n" + PROMPT);
        }
        if (!isValidPath(folder)) {
            throw new IllegalArgumentException("Invalid folder path.\n" + PROMPT);
        }
    }

    private boolean isValidPath(String folder) {
        Path p;
        try {
            p = Path.of(folder);
        } catch (InvalidPathException e) {
            return false;
        }
        return Files.isDirectory(p);
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url).openStream().close();
        } catch (IOException e) {
            return false;
        }
        return true;
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
        if (args.length != 3) {
            throw new IllegalArgumentException("Incorrect number of parameters.\n" + PROMPT);
        }
        String url = args[0];
        int speed;
        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Second argument must be a integer.\n" + PROMPT);
        }
        String folder = args[2];
        Thread wget = new Thread(new Wget(url, speed, folder));
        wget.start();
        wget.join();
    }
}