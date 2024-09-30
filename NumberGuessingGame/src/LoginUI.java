import javax.swing.*;

public class LoginUI extends JFrame {

    public LoginUI() {
        setTitle("ログイン");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("ユーザー名:");
        userLabel.setBounds(30, 20, 80, 25);
        panel.add(userLabel);

        JTextField usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 160, 25);
        panel.add(usernameField);

        JButton loginButton = new JButton("ログイン");
        loginButton.setBounds(100, 60, 85, 25);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            new GameUI(username);
            dispose();
        });
    }
}