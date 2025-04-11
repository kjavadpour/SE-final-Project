import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LabGroupSearcher {

    public static void searchByGroupID(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n🔍 Enter LabResultGroupID: ");
        String groupId = scanner.nextLine();

        String query = "SELECT LabResultGroupID, GroupName FROM LabResultGroup WHERE LabResultGroupID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, groupId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n🧪 Group Info:");
                System.out.println("Group ID: " + rs.getString("LabResultGroupID"));
                System.out.println("Group Name: " + rs.getString("GroupName"));
            } else {
                System.out.println("⚠️ No Lab Result Group found with this ID.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading group info.");
            e.printStackTrace();
        }
    }
}
