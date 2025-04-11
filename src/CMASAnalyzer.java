import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CMASAnalyzer {

    public static void analyzeMainPatient(Connection conn) {
        String query = "SELECT Date, Score_GT10 FROM CMAS_Cleaned ORDER BY Date";
        List<CMASRecord> records = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString("Date"));
                double score = rs.getDouble("Score_GT10");
                records.add(new CMASRecord(date, score));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error loading CMAS data.");
            e.printStackTrace();
            return;
        }

        if (records.isEmpty()) {
            System.out.println("ℹ️ No CMAS data available.");
            return;
        }

        // تحلیل آماری
        records.sort(Comparator.comparing(CMASRecord::date));
        double avg = records.stream().mapToDouble(CMASRecord::score).average().orElse(0);
        double max = records.stream().mapToDouble(CMASRecord::score).max().orElse(0);
        double min = records.stream().mapToDouble(CMASRecord::score).min().orElse(0);

        System.out.println("\n📊 CMAS Analysis for Main Patient:");
        for (CMASRecord rec : records) {
            System.out.printf("%s: %.2f\n", rec.date(), rec.score());
        }

        System.out.printf("\n📈 Stats — Avg: %.2f, Max: %.2f, Min: %.2f\n", avg, max, min);

        // ✅ نمایش سطح ریسک:
        String riskLevel = avg < 30 ? "🔴 High" : (avg < 45 ? "🟡 Moderate" : "🟢 Low");
        System.out.println("🧠 Risk Level Based on CMAS Average: " + riskLevel);

        // ⏱️ فواصل جلسات
        System.out.println("\n🕒 Session Intervals (days):");
        for (int i = 1; i < records.size(); i++) {
            long days = ChronoUnit.DAYS.between(records.get(i - 1).date(), records.get(i).date());
            System.out.printf("- From %s to %s: %d days\n",
                    records.get(i - 1).date(), records.get(i).date(), days);
        }

        // ⚠️ بررسی افت یا بهبود شدید
        System.out.println("\n⚠️ Sharp Trend Alerts:");
        for (int i = 1; i < records.size(); i++) {
            double diff = records.get(i).score() - records.get(i - 1).score();
            if (Math.abs(diff) >= 10) {
                String note = diff > 0 ? "Significant Improvement" : "Sharp Decline";
                System.out.printf("%s → %s: %.2f (%s)\n",
                        records.get(i - 1).date(), records.get(i).date(), diff, note);
            }
        }
    }
}

record CMASRecord(LocalDate date, double score) {}
