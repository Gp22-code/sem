# USE CASE: 6 Produce Report of Employees Hired Within a Date Range

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR officer* I want *to produce a report of employees hired within a specified date range* so that *I can track recruitment trends and onboarding progress.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Hiring dates exist in employee records.

### Success End Condition
A report listing employees hired within the date range is generated.

### Failed End Condition
No employees fall within the range or report fails to generate.

### Primary Actor
HR Officer.

### Trigger
Management requests recent hiring data.

## MAIN SUCCESS SCENARIO
1. HR officer inputs a start and end date.
2. System retrieves employees whose hire_date falls within the range.
3. System displays names, positions, and hire dates.
4. HR officer reviews and exports report.

## EXTENSIONS
1. **No employees found:**
    - System notifies HR officer that no records exist in that period.

## SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0