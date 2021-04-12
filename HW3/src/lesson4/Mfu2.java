package lesson4;

public class Mfu2 {
    private static StringBuilder stringBuilder = new StringBuilder("MFU");
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (stringBuilder) {
                    System.out.println("МФУ сканирует");
                    stringBuilder.append("a");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (stringBuilder) {
                    System.out.println("МФУ печатает");
                    stringBuilder.append("b");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (stringBuilder) {
                    System.out.println("МФУ ксерокопирует");
                    stringBuilder.append("c");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }
}

