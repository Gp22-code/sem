package com.napier.sem;

import java.sql.*;

/**
 * Minimal JDBC example:
 *  - connect() with retry
 *  - getEmployee(empNo) using a PreparedStatement
 *  - disconnect()
 */
public class App {

    private Connection con = null;

    // Read settings (Compose will set DB_HOST=db inside containers)
    private final String host = System.getenv().getOrDefault("DB_HOST", "127.0.0.1");
    private final String port = System.getenv().getOrDefault("DB_PORT", "3306");
    private final String db   = System.getenv().getOrDefault("DB_NAME", "employees");
    private final String user = System.getenv().getOrDefault("DB_USER", "root");
    private final String pass = System.getenv().getOrDefault("DB_PASS", "example");

    private String jdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + db
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    /** Connect to MySQL with a small retry loop. */
    public void connect() {
        // Optional in JDBC 4+, but harmless
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) {
            System.out.println("Could not load JDBC driver"); System.exit(1);
        }

        int retries = 30;
        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to database... attempt " + i + "/" + retries);
            try {
                con = DriverManager.getConnection(jdbcUrl(), user, pass);
                System.out.println("Successfully connected");
                return;
            } catch (SQLException ex) {
                System.out.println("Failed to connect: " + ex.getMessage());
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            }
        }
        System.out.println("Giving up after " + retries + " attempts"); System.exit(2);
    }

    /** Cleanly close the connection. */
    public void disconnect() {
        if (con != null) {
            try { con.close(); } catch (Exception ignored) {}
            con = null;
        }
    }

    /** Query: get a single employee by emp_no. */
    public Employee getEmployee(int empNo) {
        final String sql =
                "SELECT emp_no, first_name, last_name " +
                        "FROM employees " +             // table name in the employees DB
                        "WHERE emp_no = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, empNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee e = new Employee();
                    e.empNo = rs.getInt("emp_no");
                    e.firstName = rs.getString("first_name");
                    e.lastName  = rs.getString("last_name");
                    return e;
                }
            }
        } catch (SQLException ex) {
            System.out.println("getEmployee failed: " + ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        App app = new App();
        try {
            app.connect();

            // Example: fetch employee #10001
            Employee e = app.getEmployee(10001);
            if (e != null) System.out.println("Employee: " + e);
            else System.out.println("No employee found");

        } finally {
            app.disconnect();
        }
    }
}
