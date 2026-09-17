# Problem Statement

Educational institutions often manage student and course records manually or through fragmented spreadsheets, leading to duplicate enrollments, incorrect credit tracking, and inconsistent record-keeping. **Campus Course & Records Manager (CCRM)** addresses this by providing a single, console-based Java application to manage the full lifecycle of student and course records — from enrollment to transcript generation.

## Scope

CCRM covers:
- Student and instructor record management
- Course creation and semester assignment
- Enrollment processing with validation (duplicate enrollment, max credit limit)
- Grade recording and transcript generation
- CSV-based data import, export, and backup

Out of scope: web/GUI interface, multi-user authentication, and database persistence (current version uses CSV file storage).

## Target Users

- College/university administrative staff managing student records
- Academic coordinators tracking enrollments and grades
- Instructors reviewing course rosters

## High-Level Features

1. **Student & Course Management** — create, update, and view student and course records
2. **Enrollment Management** — enroll students in courses with business-rule validation
3. **Grading & Transcripts** — record grades and generate student transcripts
4. **Data Import/Export & Backup** — persist and back up records via CSV files
