import javax.swing.*;
import java.awt.event.*;

public class GameUI extends JFrame {
    private int targetNumber;
    private int guessCount;

    private JTextField guessField;
    private JLabel resultLabel;

    public GameUI() {
        initGame();

        setTitle("猜数字游戏");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void initGame() {
        targetNumber = (int) (Math.random() * 100) + 1; // 生成1到100的随机数
        guessCount = 0;
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel instructionLabel = new JLabel("猜一个1到100之间的数字：");
        instructionLabel.setBounds(50, 20, 300, 25);
        panel.add(instructionLabel);

        guessField = new JTextField(20);
        guessField.setBounds(50, 50, 200, 25);
        panel.add(guessField);

        JButton guessButton = new JButton("猜测");
        guessButton.setBounds(260, 50, 80, 25);
        panel.add(guessButton);

        resultLabel = new JLabel("输入数字后点击猜测。");
        resultLabel.setBounds(50, 90, 300, 25);
        panel.add(resultLabel);

        // 简化事件处理
        guessButton.addActionListener(e -> {
            String guessText = guessField.getText();
            try {
                int guessNumber = Integer.parseInt(guessText);
                guessCount++;
                if (guessNumber > targetNumber) {
                    resultLabel.setText("太大了！");
                } else if (guessNumber < targetNumber) {
                    resultLabel.setText("太小了！");
                } else {
                    resultLabel.setText("恭喜你，猜对了！你猜了 " + guessCount + " 次。");
                    int option = JOptionPane.showConfirmDialog(null, "再来一局？", "游戏结束", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        initGame();
                        guessField.setText("");
                        resultLabel.setText("输入数字后点击猜测。");
                    } else {
                        dispose();
                    }
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("请输入有效的数字！");
            }
        });
    }
}
