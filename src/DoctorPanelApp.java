import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DoctorPanelApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("👨‍⚕️ Welcome to JDM Doctor Panel");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (!username.equals("doctor") || !password.equals("1234")) {
            System.out.println("❌ Invalid login.");
            return;
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            while (true) {
                System.out.println("\n🔧 Main Menu:");
                System.out.println("1. View Main Patient CMAS Analysis");
                System.out.println("2. Search Lab Results by Patient ID");
                System.out.println("3. Analyze Critical Lab Monitoring by Patient ID ✅");
                System.out.println("4. View Measurements by LabResult ID");
                System.out.println("5. About / Participants");
                System.out.println("0. Exit");
                System.out.print("Your choice: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> CMASAnalyzer.analyzeMainPatient(conn);
                    case "2" -> {
                        System.out.print("Enter Patient ID: ");
                        String id = scanner.nextLine();
                        PatientSearcher.searchLabResultsByPatientID(conn, id);
                    }
                    case "3" -> {
                        System.out.print("Enter Patient ID: ");
                        String id = scanner.nextLine();
                        LabSummaryChecker.showLabMonitoringSummary(conn, id);
                    }
                    case "4" -> {
                        System.out.print("Enter LabResult ID: ");
                        String id = scanner.nextLine();
                        MeasurementViewer.showMeasurementsByLabResultID(conn, id);
                    }
                    case "5" -> AboutSection.showInfo();
                    case "0" -> {
                        System.out.println("✅ Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Database connection error.");
            e.printStackTrace();
        }
    }
}
