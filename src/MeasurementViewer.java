import java.sql.*;
import java.util.*;

public class MeasurementViewer {

    public static void showMeasurementsByLabResultID(Connection conn, String labResultId) {
        String query = """
            SELECT MeasurementID, LabResultID, Value, DateTime
            FROM Measurement
            WHERE LabResultID = ?
        """;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, labResultId);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            System.out.println("\n📏 Measurement Results for LabResultID: " + labResultId);
            while (rs.next()) {
                found = true;
                String measurementId = rs.getString("MeasurementID");
                double value = rs.getDouble("Value");
                String dateTime = rs.getString("DateTime");

                System.out.printf("- MeasurementID: %s | Value: %.2f | DateTime: %s\n",
                        measurementId, value, dateTime);
            }

            if (!found) {
                System.out.println("No measurement data found for this LabResultID.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading measurement data.");
            e.printStackTrace();
        }
    }
}
