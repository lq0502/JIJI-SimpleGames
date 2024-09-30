import java.sql.*;

public class PetDatabase {

    private static final String URL = "jdbc:sqlite:pet.db";

    // 加载宠物的状态
    public static Pet loadPetState() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            // 创建表（如果表不存在）
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS pet (hunger INTEGER, happiness INTEGER, fatigue INTEGER)";
            stmt.execute(sql);

            // 查询宠物状态
            ResultSet rs = stmt.executeQuery("SELECT * FROM pet LIMIT 1");

            if (rs.next()) {
                // 如果数据库有记录，返回宠物状态
                return new Pet(rs.getInt("hunger"), rs.getInt("happiness"), rs.getInt("fatigue"));
            } else {
                // 如果数据库没有记录，创建一个默认宠物
                Pet pet = new Pet(50, 50, 50);
                savePetState(pet); // 保存默认宠物状态
                return pet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new Pet(50, 50, 50); // 如果发生错误，返回默认宠物状态
        }
    }

    // 保存宠物的状态
    public static void savePetState(Pet pet) {
        try (Connection conn = DriverManager.getConnection(URL)) {
            // 清空旧数据
            String sql = "DELETE FROM pet";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            // 插入新数据
            sql = "INSERT INTO pet (hunger, happiness, fatigue) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, pet.getHunger()); // 设置第一个占位符
                pstmt.setInt(2, pet.getHappiness()); // 设置第二个占位符
                pstmt.setInt(3, pet.getFatigue()); // 设置第三个占位符
                pstmt.executeUpdate(); // 执行更新
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
