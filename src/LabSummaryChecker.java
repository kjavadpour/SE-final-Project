import java.sql.*;
import java.util.*;

public class LabSummaryChecker {

    public static void showLabMonitoringSummary(Connection conn, String patientId) {
        String query = "SELECT ResultName_English FROM LabResultsEN WHERE PatientID = ?";
        Set<String> monitoredTests = new HashSet<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                monitoredTests.add(rs.getString("ResultName_English").toLowerCase());
            }
        } catch (SQLException e) {
            System.out.println("❌ Error checking lab monitoring.");
            e.printStackTrace();
            return;
        }

        // ⚠️ Only high-priority lab tests related to JDM monitoring are included below.
        // This list is not exhaustive — it focuses on clinically significant markers.
        List<String> importantTests = Arrays.asList(
                "c-reactive protein", "creatine kinase", "esr", "ana", "alt", "ast",
                "ldh", "aldolase", "hemoglobin", "wbc", "platelet count",
                "albumin", "ferritin", "igg", "igm", "iga"
        );

        System.out.println("\n🧪 Lab Monitoring Checklist for Patient ID: " + patientId);
        boolean anyMissing = false;

        for (String test : importantTests) {
            boolean monitored = monitoredTests.contains(test);
            System.out.printf("- %-20s : %s\n", capitalize(test), monitored ? "✅ Monitored" : "❌ Not Monitored");
            if (!monitored) {
                anyMissing = true;
                if (test.equals("c-reactive protein") || test.equals("creatine kinase") || test.equals("esr")) {
                    System.out.println("⚠️ Important Notice: " + capitalize(test) + " is a critical test and has NOT been monitored.");
                }
            }
        }

        if (!anyMissing) {
            System.out.println("✅ All critical tests have been monitored.");
        }
    }

    private static String capitalize(String input) {
        String[] parts = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty())
                sb.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
