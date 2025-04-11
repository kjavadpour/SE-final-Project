import java.sql.*;

public class PatientSearcher {

    public static void searchLabResultsByPatientID(Connection conn, String patientId) {
        System.out.println("\n🧪 Lab Results (Filtered by Patient ID):");
        String query = """
            SELECT LabResultID, LabResultGroupID, ResultName_English, Unit 
            FROM LabResultsEN 
            WHERE PatientID = ?
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, patientId);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                String resultName = rs.getString("ResultName_English");
                String unit = rs.getString("Unit");
                String labResultID = rs.getString("LabResultID");
                String groupID = rs.getString("LabResultGroupID");

                System.out.printf("- ID: %s | Group: %s | Name: %s | Unit: %s\n",
                        labResultID, groupID, resultName, unit);
            }

            if (!found) {
                System.out.println("No lab results found for this patient.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error retrieving lab results.");
            e.printStackTrace();
        }
    }
}
