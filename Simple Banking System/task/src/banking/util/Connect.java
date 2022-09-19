package banking.util;

import banking.entities.Account;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

class Connect {
    private static final String URL = "jdbc:sqlite:C:\\Users\\david\\IdeaProjects\\Simple Banking System\\Simple Banking System\\task\\card.s3db";

    private static Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS card (
                	id INTEGER PRIMARY KEY,
                	number TEXT,
                	pin TEXT,
                 balance INTEGER DEFAULT 0);""";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String number, String pin, int balance) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Map<String, Account> getAllAccFromDB() {
        String sql = "SELECT * FROM card";
        Account account;
        Map<String, Account> accounts = new HashMap<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                account = new Account(rs.getInt("id"), rs.getString("number"),
                        rs.getString("pin"), rs.getInt("balance"));
                accounts.put(rs.getString("number"), account);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public static boolean existsAccount(String number, String pin) {
        String sql = "SELECT * FROM card " +
                "WHERE number = ? AND pin = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void updateBalance(String number, int balance) {
        String sql = "UPDATE card SET balance = ? "
                + "WHERE number = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, balance);
            pstmt.setString(2, number);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void delete(String number) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, number);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void transfer(Account acc, String receiver, int amount) {
        String sql = "UPDATE card SET balance = balance + ? "
                + "WHERE number = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //conn.setAutoCommit(false);
            pstmt.setInt(1, -amount);
            pstmt.setString(2, acc.getCardNumber());
            pstmt.executeUpdate();

            pstmt.setInt(1, amount);
            pstmt.setString(2, receiver);
            pstmt.executeUpdate();
            //conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
