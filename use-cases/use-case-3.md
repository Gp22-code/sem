# USE CASE: 3 Produce Average Salary Report by Role

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR analyst* I want *to produce a report showing the average salary for each role* so that *I can compare compensation across job titles.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Roles exist and have assigned employees with valid salary data.

### Success End Condition
Average salary report by role is displayed or exported.

### Failed End Condition
No report generated or incomplete data.

### Primary Actor
HR Analyst.

### Trigger
HR requests comparative salary analysis.

## MAIN SUCCESS SCENARIO
1. HR analyst selects one or multiple roles.
2. System retrieves current salary data for employees in each role.
3. System calculates the average, minimum, and maximum salary for each role.
4. System displays the report.
5. HR analyst exports or shares with management.

## EXTENSIONS
2. **Role has no employees**:
    - System marks the role as "no data available."

## SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0