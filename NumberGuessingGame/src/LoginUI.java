import javax.swing.*;
import java.awt.event.*;

public class LoginUI extends JFrame {
    private JTextField usernameField;

    public LoginUI() {
        setTitle("登录");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(30, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 160, 25);
        panel.add(usernameField);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(100, 60, 80, 25);
        panel.add(loginButton);

        // 简单登录事件
        loginButton.addActionListener(e -> {
            new GameUI();
            dispose(); // 关闭登录界面
        });
    }
}
