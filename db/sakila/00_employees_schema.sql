-- Employees sample DB - schema only
DROP DATABASE IF EXISTS employees;
CREATE DATABASE IF NOT EXISTS employees;
USE employees;

SELECT 'CREATING DATABASE STRUCTURE' AS INFO;

/*!50503 SET default_storage_engine = InnoDB */;
DROP TABLE IF EXISTS dept_emp, dept_manager, titles, salaries, employees, departments;

CREATE TABLE employees (
                           emp_no      INT         NOT NULL,
                           birth_date  DATE        NOT NULL,
                           first_name  VARCHAR(14) NOT NULL,
                           last_name   VARCHAR(16) NOT NULL,
                           gender      ENUM('M','F') NOT NULL,
                           hire_date   DATE        NOT NULL,
                           PRIMARY KEY (emp_no)
);

CREATE TABLE departments (
                             dept_no   CHAR(4)     NOT NULL,
                             dept_name VARCHAR(40) NOT NULL,
                             PRIMARY KEY (dept_no),
                             UNIQUE KEY (dept_name)
);

CREATE TABLE dept_manager (
                              emp_no    INT     NOT NULL,
                              dept_no   CHAR(4) NOT NULL,
                              from_date DATE    NOT NULL,
                              to_date   DATE    NOT NULL,
                              PRIMARY KEY (emp_no, dept_no),
                              FOREIGN KEY (emp_no)  REFERENCES employees (emp_no)  ON DELETE CASCADE,
                              FOREIGN KEY (dept_no) REFERENCES departments (dept_no) ON DELETE CASCADE
);

CREATE TABLE dept_emp (
                          emp_no    INT     NOT NULL,
                          dept_no   CHAR(4) NOT NULL,
                          from_date DATE    NOT NULL,
                          to_date   DATE    NOT NULL,
                          PRIMARY KEY (emp_no, dept_no),
                          FOREIGN KEY (emp_no)  REFERENCES employees (emp_no)  ON DELETE CASCADE,
                          FOREIGN KEY (dept_no) REFERENCES departments (dept_no) ON DELETE CASCADE
);

CREATE TABLE titles (
                        emp_no    INT          NOT NULL,
                        title     VARCHAR(50)  NOT NULL,
                        from_date DATE         NOT NULL,
                        to_date   DATE,
                        PRIMARY KEY (emp_no, title, from_date),
                        FOREIGN KEY (emp_no) REFERENCES employees (emp_no) ON DELETE CASCADE
);

CREATE TABLE salaries (
                          emp_no    INT  NOT NULL,
                          salary    INT  NOT NULL,
                          from_date DATE NOT NULL,
                          to_date   DATE NOT NULL,
                          PRIMARY KEY (emp_no, from_date),
                          FOREIGN KEY (emp_no) REFERENCES employees (emp_no) ON DELETE CASCADE
);

CREATE OR REPLACE VIEW dept_emp_latest_date AS
SELECT emp_no, MAX(from_date) AS from_date, MAX(to_date) AS to_date
FROM dept_emp
GROUP BY emp_no;

CREATE OR REPLACE VIEW current_dept_emp AS
SELECT l.emp_no, d.dept_no, l.from_date, l.to_date
FROM dept_emp d
         JOIN dept_emp_latest_date l
              ON d.emp_no = l.emp_no
                  AND d.from_date = l.from_date
                  AND d.to_date = l.to_date;

SELECT 'SCHEMA CREATED' AS INFO;
