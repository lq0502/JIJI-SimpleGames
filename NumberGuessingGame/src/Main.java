import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 设置界面风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 显示登录界面
        SwingUtilities.invokeLater(() -> new LoginUI());
    }
}
