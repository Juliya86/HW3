package lesson2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;

    public static void main(String[] args) {
        try {
            connect();
            readFile();
            createTableEx();
            clearTableEx();

//        запускаем все 3 варианта, заполнения, поочереди
          preparedStmtEx();
//          batchEx();
//          transactionEx();

           selectEx();

            updateEx();

            selectExRange();


            rollbackEx();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private static void rollbackEx() throws SQLException {
        stmt.executeUpdate("INSERT INTO goodsTable (prodid, title, cost) VALUES ('123', 'book', 20000);");
        Savepoint sp1 = connection.setSavepoint();
        stmt.executeUpdate("INSERT INTO goodsTable (prodid, title, cost) VALUES ('456', 'book', 20000);");
        connection.rollback(sp1);
        stmt.executeUpdate("INSERT INTO goodsTable (prodid, title, cost) VALUES ('789', 'book', 20000);");
        connection.setAutoCommit(true);
    }

    private static void preparedStmtEx() throws SQLException {
        long t = System.currentTimeMillis();
        connection.setAutoCommit(false);
        psInsert = connection.prepareStatement("INSERT INTO goodsTable (prodid, title, cost) VALUES (?, ?, ?);");
        for (int i = 0; i < 20000; i++) {
            psInsert.setString(1, (i + 1) + "1");
            psInsert.setString(2, "book");
            psInsert.setInt(3, 20 + (i * 10) % 90);
            psInsert.addBatch();
        }
        psInsert.executeBatch();
        connection.setAutoCommit(true);
        System.out.println(System.currentTimeMillis() - t);
    }


    private static void createTableEx() throws SQLException {

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS goodsTable (\n" +
                "    id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    prodid  TEXT UNIQUE,\n" +
                "    title TEXT,\n" +
                "    cost INTEGER\n" +
                ");");
    }


    private static void dropTableEx() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS goodsTable;");
    }


    // чистим таблицу
    private static void clearTableEx() throws SQLException {
        stmt.executeUpdate("DELETE FROM goodsTable;");
    }

    private static void deleteOneEntryEx() throws SQLException {
        stmt.executeUpdate("DELETE FROM goodsTable WHERE id = 5;");
    }

    // изменяем
    private static void updateEx() throws SQLException {
        System.out.println("изменяем");
        Scanner scanner = new Scanner(System.in);
        String cost = scanner.nextLine();
        String id = scanner.nextLine();
        String sql = String.format("UPDATE goodsTable SET cost = '%s' WHERE id = '%s';", cost, id);
        stmt.executeUpdate(sql);
    }

    private static void insertEx() throws SQLException {
        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob4', 100);");
    }

    // узнаем цену товара
    private static void selectEx() throws SQLException {
        System.out.println("ищем");
        Scanner scanner = new Scanner(System.in);
        String res = scanner.nextLine();
        String sql = String.format("SELECT cost FROM goodsTable where prodid = '%s';", res);
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("cost"));
        }
    }

    // ищем в диапазоне
    private static void selectExRange() throws SQLException {
        System.out.println("ищем в диапазоне");
        Scanner scanner = new Scanner(System.in);
        String low = scanner.nextLine();
        String hight = scanner.nextLine();
        String sql = String.format("SELECT prodid FROM goodsTable where cost BETWEEN '%s' AND '%s' ORDER BY  prodid;", low, hight);
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("prodid"));
        }
    }

    public static void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void readFile() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("update.txt");
        Scanner scanner = new Scanner(fileInputStream);

        while (scanner.hasNext()) {
            String[] mass = scanner.nextLine().split(" ");
            try {
                updateDB(mass[0], mass[1]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateDB(String id, String newValue) throws SQLException {
        String sql = String.format("UPDATE students SET score = %s where id = %s", newValue, id);
        stmt.executeUpdate(sql);
    }
}
