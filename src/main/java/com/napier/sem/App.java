package com.napier.sem;

import java.sql.*;

public class App {

    private Connection con = null;

    // env -> JDBC
    private final String host = System.getenv().getOrDefault("DB_HOST", "127.0.0.1");
    private final String port = System.getenv().getOrDefault("DB_PORT", "3306");
    private final String db   = System.getenv().getOrDefault("DB_NAME", "employees");
    private final String user = System.getenv().getOrDefault("DB_USER", "root");
    private final String pass = System.getenv().getOrDefault("DB_PASS", "example");

    private String jdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + db
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    // ---- connect / disconnect ------------------------------------------------

    public void connect() {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { System.out.println("Could not load JDBC driver"); System.exit(1); }

        int retries = 30;
        for (int i = 1; i <= retries; i++) {
            System.out.println("Connecting to database... attempt " + i + "/" + retries);
            try {
                System.out.println("JDBC URL = " + jdbcUrl());
                con = DriverManager.getConnection(jdbcUrl(), user, pass);
                System.out.println("Successfully connected");
                return;
            } catch (SQLException ex) {
                System.out.println("Failed to connect: " + ex.getMessage());
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            }
        }
        System.out.println("Giving up after " + retries + " attempts");
        System.exit(2);
    }

    public void disconnect() {
        if (con != null) {
            try { con.close(); } catch (Exception ignored) {}
            con = null;
        }
    }

    // ---- Feature 1: get a single employee (current title/salary/department/manager)

    public Employee getEmployee(int empNo) {
        final String sql =
                "SELECT e.emp_no, e.first_name, e.last_name, " +
                        "       t.title, s.salary, d.dept_name, " +
                        "       CONCAT(m.first_name, ' ', m.last_name) AS manager " +
                        "FROM employees e " +
                        "LEFT JOIN titles       t  ON t.emp_no = e.emp_no AND t.to_date  = '9999-01-01' " +
                        "LEFT JOIN salaries     s  ON s.emp_no = e.emp_no AND s.to_date  = '9999-01-01' " +
                        "LEFT JOIN dept_emp     de ON de.emp_no = e.emp_no AND de.to_date = '9999-01-01' " +
                        "LEFT JOIN departments  d  ON d.dept_no = de.dept_no " +
                        "LEFT JOIN dept_manager dm ON dm.dept_no = de.dept_no AND dm.to_date = '9999-01-01' " +
                        "LEFT JOIN employees    m  ON m.emp_no  = dm.emp_no " +
                        "WHERE e.emp_no = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, empNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.emp_no     = rs.getInt("emp_no");
                    emp.first_name = rs.getString("first_name");
                    emp.last_name  = rs.getString("last_name");
                    emp.title      = rs.getString("title");
                    emp.salary     = rs.getInt("salary");
                    emp.dept_name  = rs.getString("dept_name");
                    emp.manager    = rs.getString("manager");
                    return emp;
                }
            }
        } catch (SQLException ex) {
            System.out.println("getEmployee failed: " + ex.getMessage());
        }
        return null;
    }

    // ---- Feature 2: HR story — salary report for a given role (title)

    /** Prints headcount and salary stats for the given role/title. */
    public void getSalaryReportByRole(String role) {
        final String sql =
                "SELECT t.title, " +
                        "       COUNT(*)                AS headcount, " +
                        "       AVG(s.salary)           AS avg_salary, " +
                        "       MIN(s.salary)           AS min_salary, " +
                        "       MAX(s.salary)           AS max_salary " +
                        "FROM titles t " +
                        "JOIN salaries s ON s.emp_no = t.emp_no AND s.to_date = '9999-01-01' " +
                        "WHERE t.to_date = '9999-01-01' AND t.title = ? " +
                        "GROUP BY t.title";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nSalary report for role: " + rs.getString("title"));
                    System.out.println("Headcount : " + rs.getLong("headcount"));
                    System.out.println("Avg salary: " + rs.getLong("avg_salary"));
                    System.out.println("Min salary: " + rs.getLong("min_salary"));
                    System.out.println("Max salary: " + rs.getLong("max_salary"));
                } else {
                    System.out.println("\nNo employees found for role: " + role);
                }
            }
        } catch (SQLException ex) {
            System.out.println("getSalaryReportByRole failed: " + ex.getMessage());
        }
    }

    // ---- Utilities -----------------------------------------------------------

    public void displayEmployee(Employee emp) {
        if (emp == null) {
            System.out.println("No employee found");
            return;
        }
        System.out.println(
                emp.emp_no + " " + emp.first_name + " " + emp.last_name + "\n" +
                        (emp.title != null ? emp.title : "null") + "\n" +
                        "Salary: " + emp.salary + "\n" +
                        (emp.dept_name != null ? emp.dept_name : "null") + "\n" +
                        "Manager: " + (emp.manager != null ? emp.manager : "null")
        );
    }

    public static void main(String[] args) {
        App app = new App();
        try {
            app.connect();

            // Demo 1: single employee
            Employee e = app.getEmployee(10001);
            app.displayEmployee(e);

            // Demo 2: HR report
            String role = "Engineer";                     // e.g. "Senior Engineer", "Staff", etc.
            app.getSalaryReportByRole(role);

        } finally {
            app.disconnect();
        }
    }
}
