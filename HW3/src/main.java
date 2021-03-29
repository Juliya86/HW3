import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class main
{
    public static void main(String[] args) {
        try {
            task1();
            task2();
            task3();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void task1() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("123/hw.txt"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int x;
        while ((x = in.read()) != -1) {
            out.write(x);
        }

        byte[] b = out.toByteArray();
        System.out.println(Arrays.toString(b));
        in.close();
        out.close();
    }


    public static void task2() throws IOException {
        long a = System.currentTimeMillis();

        ArrayList<InputStream> al = new ArrayList<>();
        al.add(new FileInputStream("1234/1.txt"));
        al.add(new FileInputStream("1234/2.txt"));
        al.add(new FileInputStream("1234/3.txt"));
        al.add(new FileInputStream("1234/4.txt"));
        al.add(new FileInputStream("1234/5.txt"));

        BufferedInputStream in = new BufferedInputStream(new SequenceInputStream(Collections.enumeration(al)));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("out.txt"));
        int x;
        while ((x = in.read()) != -1) {
            out.write(x);
            System.out.println((char) x);
        }
        in.close();
        out.close();

        System.out.println(System.currentTimeMillis() - a);
    }


    public static void task3() throws IOException {
        final int PAGE_SIZE = 1800;
        RandomAccessFile raf = new RandomAccessFile("123/1.txt", "r");
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите страницу: ");
        int p = sc.nextInt() - 1;
        raf.seek(p * PAGE_SIZE);
        byte[] barr = new byte[1800];
        raf.read(barr);
        System.out.println(new String(barr));
        raf.close();
    }
}
