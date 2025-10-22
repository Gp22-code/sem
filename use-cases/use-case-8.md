# USE CASE: 8 Search Employee by Name

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR officer* I want *to search for an employee by name (first or last)* so that *I can quickly access their personnel details.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Employee names are stored in the database.

### Success End Condition
The correct employee record(s) are displayed.

### Failed End Condition
No matching employee found.

### Primary Actor
HR Officer.

### Trigger
HR officer initiates a name search.

## MAIN SUCCESS SCENARIO
1. HR officer inputs employee name or partial name.
2. System searches employee records using the input.
3. System displays matching employees with ID, name, and department.
4. HR officer selects a record to view details.

## EXTENSIONS
1. **No match found:**
    - System displays “No employee found.”

## SUB-VARIATIONS
2. **Partial name search:** system returns multiple results.

## SCHEDULE
**DUE DATE**: Release 1.0