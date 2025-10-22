# USE CASE: 5 Produce Department Manager Report

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR manager* I want *to produce a report showing the current manager of each department* so that *I can verify leadership structure and accountability.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Department and manager data exist in the database.

### Success End Condition
Report displays all departments with their respective managers.

### Failed End Condition
No report is produced or data missing.

### Primary Actor
HR Manager.

### Trigger
Periodic review of department management assignments.

## MAIN SUCCESS SCENARIO
1. HR manager requests department-manager list.
2. System retrieves department and current manager data.
3. System displays results including department name, manager name, and start date.
4. HR manager reviews and exports report.

## EXTENSIONS
2. **Department without assigned manager**:
    - System marks the department as “Vacant.”

## SUB-VARIATIONS
None.

## SCHEDULE
**DUE DATE**: Release 1.0