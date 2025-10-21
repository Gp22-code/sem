# USE CASE: 1 List Employees by Department

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want *to list all employees within a specific department* so that *I can review departmental staffing and structures.*

### Scope
Company.

### Level
Primary task.

### Preconditions
We know the department name or ID. The database contains current employee data linked to departments.

### Success End Condition
A list of employees belonging to the specified department is displayed.

### Failed End Condition
No employees are listed or department not found.

### Primary Actor
HR Advisor.

### Trigger
A request to view departmental employee information.

## MAIN SUCCESS SCENARIO
1. HR advisor selects or inputs the department name.
2. System retrieves all employees currently assigned to that department.
3. System displays employee ID, full name, title, and hire date.
4. HR advisor reviews the list.

## EXTENSIONS
2. **Department does not exist:**
    1. System notifies HR advisor that no such department exists.

## SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0