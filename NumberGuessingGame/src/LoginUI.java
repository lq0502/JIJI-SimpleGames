import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI() {
        setTitle("登录");
        setSize(300, 180);
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

        usernameField = new JTextField("user");
        usernameField.setBounds(100, 20, 160, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(30, 60, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField("pass");
        passwordField.setBounds(100, 60, 160, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(100, 100, 80, 25);
        panel.add(loginButton);

        // 登录按钮事件处理
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取输入的用户名和密码
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // 简单验证（默认用户名：user，密码：pass）
                if ("user".equals(username) && "pass".equals(password)) {
                    // 进入游戏界面
                    new GameUI(username);
                    dispose( ); // 关闭登录界面
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

