package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        String[] process = {"-", "\\", "|", "/"};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[index++]);
            if (index == process.length) {
                index = 0;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
