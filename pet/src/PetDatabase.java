import java.sql.*;

public class PetDatabase {

    private static final String URL = "jdbc:sqlite:pet.db";

    public static Pet loadPetState() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS pet (hunger INTEGER, happiness INTEGER, fatigue INTEGER)";
            stmt.execute(sql);

            ResultSet rs = stmt.executeQuery("SELECT * FROM pet LIMIT 1");

            if (rs.next()) {
                return new Pet(rs.getInt("hunger"), rs.getInt("happiness"), rs.getInt("fatigue"));
            } else {
                Pet pet = new Pet(50, 50, 50);
                savePetState(pet); 
                return pet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Pet(50, 50, 50);
        }
    }

    public static void savePetState(Pet pet) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "DELETE FROM pet";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            sql = "INSERT INTO pet (hunger, happiness, fatigue) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, pet.getHunger()); 
                pstmt.setInt(2, pet.getHappiness()); 
                pstmt.setInt(3, pet.getFatigue()); 
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
