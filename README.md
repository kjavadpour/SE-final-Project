# SE-final-Project
Introduction to SE final Project (JDM)
# 🩺 JDM Doctor Panel

A console-based Java application for analyzing and monitoring Juvenile Dermatomyositis (JDM) patients. Designed for academic use, the system simplifies the review of clinical data such as CMAS scores and lab results in a lightweight, user-friendly way—without requiring a graphical interface.

---

## 🚀 Features

- **🔐 Login System:**  
  Secure access with predefined credentials (`doctor` / `1234`).

- **📊 CMAS Score Analysis:**  
  Automatically fetches, sorts, and analyzes CMAS data:
  - Displays each session's score.
  - Calculates average, max, and min scores.
  - Detects session gaps and trend shifts.
  - Labels risk level (High, Moderate, Low) based on average score.

- **🧪 Lab Results Viewer:**  
  Allows users to enter a Patient ID and retrieve all related lab tests.

- **✅ Critical Test Monitoring:**  
  Checks if high-priority tests (like CRP, CK, ESR) are present and flags missing ones.

- **📏 Measurement Lookup:**  
  Retrieves specific measurement values based on LabResult IDs.

- **ℹ️ About Section:**  
  Shows project credits and participant information.

---

## 📂 Project Structure

- `DoctorPanelApp.java`: Main class with menu and login logic.
- `CMASAnalyzer.java`: Handles CMAS data analysis.
- `PatientSearcher.java`: Searches lab results by Patient ID.
- `LabSummaryChecker.java`: Verifies presence of critical tests.
- `MeasurementViewer.java`: Displays measurements for a given LabResult ID.
- `LabGroupSearcher.java`: (Optional) Searches LabResult groups by ID.
- `DatabaseManager.java`: Manages SQLite connection.
- `database.db`: SQLite database file (should be placed under `src/`).

---

## 🗃️ Database Overview

The application uses an SQLite database with the following key data:

- **Patients**: Contains core patient information.
- **CMAS_Cleaned**: Stores CMAS scores linked to patient IDs and session dates.
- **LabResultsEN**: Records all lab results with test names, units, and patient/lab IDs.
- **Measurement**: Holds time-stamped measurement values for each LabResult ID.

---

## 🧪 Sample Test Cases

```plaintext
Input: "1"
Expected Output: List of CMAS scores + average/max/min + risk level + trend alerts

Input: "2" + Patient ID: P001
Expected Output: All lab results linked to P001

Input: "3" + Patient ID: P001
Expected Output: Checklist showing which critical tests have been monitored

Input: "4" + LabResult ID: L001
Expected Output: List of measurements with date and values

Input: "5"
Expected Output: Information about the project and participants
