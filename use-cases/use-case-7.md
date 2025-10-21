# USE CASE: 7 Identify Employees Due for Salary Review

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR analyst* I want *to identify employees whose salaries have not been updated for over 12 months* so that *I can recommend salary reviews to management.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Employee salary history is recorded with effective dates.

### Success End Condition
A list of employees due for salary review is displayed.

### Failed End Condition
No employees are identified.

### Primary Actor
HR Analyst.

### Trigger
Quarterly or annual salary review process.

## MAIN SUCCESS SCENARIO
1. HR analyst initiates salary review report.
2. System checks last salary update date for each employee.
3. System lists those with more than 12 months since the last change.
4. HR analyst reviews and forwards list to HR manager.

## EXTENSIONS
2. **Incomplete salary history data:**
    - System flags employees with missing data for manual check.

## SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0