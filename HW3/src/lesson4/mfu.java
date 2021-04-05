package lesson4;

public class mfu {
    int m;

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public synchronized void scan() {
        System.out.println("MFU " + "scan");
    }

    public synchronized void print() {
        System.out.println("MFU " + "print");
    }

    public synchronized void copy() {
        System.out.println("MFU " + "copy");
    }

}

class mfuMain {
        public static void main(String[] args) {
            final mfu m = new mfu();

            Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.scan();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.print();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.copy();
            }
        });

        t1.start();
        t2.start();
        t3.start();

//        while (true) {
//            if (!t1.isAlive()) {
//                break;
//            }
//        }
//        while (true) {
//            if (!t2.isAlive()) {
//                break;
//            }
//        }
//            while (true) {
//                if (!t3.isAlive()) {
//                    break;
//                }
//            }


        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
