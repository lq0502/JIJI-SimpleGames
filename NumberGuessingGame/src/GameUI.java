import javax.swing.*;
import java.sql.*;
import java.util.logging.*;

public class GameUI extends JFrame {
    private static final Logger logger = Logger.getLogger(GameUI.class.getName());
    private int targetNumber;
    private int guessCount;
    private final String username;

    private JTextField guessField;
    private JLabel resultLabel;

    public GameUI(String username) {
        this.username = username;
        initGame();

        setTitle("数字当てゲーム");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void initGame() {
        targetNumber = (int) (Math.random() * 100) + 1;
        guessCount = 0;
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel instructionLabel = new JLabel("1から100までの数字を当ててください：");
        instructionLabel.setBounds(50, 20, 300, 25);
        panel.add(instructionLabel);

        guessField = new JTextField(20);
        guessField.setBounds(50, 50, 200, 25);
        panel.add(guessField);

        JButton guessButton = new JButton("推測");
        guessButton.setBounds(260, 50, 80, 25);
        panel.add(guessButton);

        resultLabel = new JLabel("数字を入力して推測をクリックしてください。");
        resultLabel.setBounds(50, 90, 300, 25);
        panel.add(resultLabel);

        JButton historyButton = new JButton("履歴を見る");
        historyButton.setBounds(50, 120, 120, 25);
        panel.add(historyButton);

        guessButton.addActionListener(e -> {
            String guessText = guessField.getText();
            try {
                int guessNumber = Integer.parseInt(guessText);
                guessCount++;
                if (guessNumber > targetNumber) {
                    resultLabel.setText("大きすぎます！");
                } else if (guessNumber < targetNumber) {
                    resultLabel.setText("小さすぎます！");
                } else {
                    resultLabel.setText("おめでとうございます！当たりました！総計 " + guessCount + " 回推測しました。");
                    saveResult(guessCount);
                    int option = JOptionPane.showConfirmDialog(null, "もう一度やりますか？", "ゲーム終了", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        initGame();
                        guessField.setText("");
                        resultLabel.setText("数字を入力して推測をクリックしてください。");
                    } else {
                        dispose();
                    }
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("有効な数字を入力してください！");
            }
        });

        historyButton.addActionListener(e -> viewHistory());
    }

    private void saveResult(int guessCount) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:game.db");
            String sql = "CREATE TABLE IF NOT EXISTS history (username TEXT, attempts INTEGER)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

            sql = "INSERT INTO history (username, attempts) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setInt(2, guessCount);
            pstmt.executeUpdate();

            pstmt.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "数据库操作失败", e);
        }
    }

    private void viewHistory() {
        // 从数据库中检索历史记录
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:game.db");
            String sql = "SELECT * FROM history WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            StringBuilder history = new StringBuilder();
            while (rs.next()) {
                history.append("ユーザー名: ").append(rs.getString("username"))
                        .append(", 推測回数: ").append(rs.getInt("attempts"))
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, history.length() > 0 ? history.toString() : "履歴がありません。", "履歴", JOptionPane.INFORMATION_MESSAGE);

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "数据库查询失败", e);
        }
    }
}
