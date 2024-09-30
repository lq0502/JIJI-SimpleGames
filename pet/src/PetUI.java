import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PetUI {
    private Pet pet;
    private int x, y; // 宠物的位置
    private final int pixelSize = 20; // 像素大小
    private final Random random = new Random();

    // 定义不同的动作帧（普通、眨眼、举手等）
    private final int[][] normalPetImage = {
            {0, 0, 1, 1, 1, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {1, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 1, 1, 1, 0, 0}
    };

    private final int[][] blinkingPetImage = {
            {0, 0, 1, 1, 1, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {1, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 1, 0, 0} // 眨眼时眼睛闭上
    };

    private final int[][] wavingPetImage = {
            {0, 0, 1, 1, 1, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {1, 0, 1, 1, 1, 1, 0, 1}, // 举手
            {1, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 1, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 1, 1, 1, 0, 0}
    };

    private int[][] currentPetImage = normalPetImage; // 当前显示的宠物图像

    public PetUI(Pet pet) {
        this.pet = pet;
        this.x = 50; // 宠物初始位置
        this.y = 50; // 宠物初始位置
    }

    public void createAndShowGUI() {
        // 创建主框架
        JFrame frame = new JFrame("バーチャルペット");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 显示宠物状态的标签 (日文)
        JLabel hungerLabel = new JLabel("お腹の空き: " + pet.getHunger());
        JLabel happinessLabel = new JLabel("幸せ: " + pet.getHappiness());
        JLabel fatigueLabel = new JLabel("疲労: " + pet.getFatigue());

        // 喂食按钮 (日文)
        JButton feedButton = new JButton("餌をあげる");
        feedButton.addActionListener(e -> {
            pet.feed();
            hungerLabel.setText("お腹の空き: " + pet.getHunger());
            frame.repaint();
        });

        // 娱乐按钮 (日文)
        JButton playButton = new JButton("遊ぶ");
        playButton.addActionListener(e -> {
            pet.play();
            happinessLabel.setText("幸せ: " + pet.getHappiness());
            frame.repaint();
        });

        // 休息按钮 (日文)
        JButton restButton = new JButton("休む");
        restButton.addActionListener(e -> {
            pet.rest();
            fatigueLabel.setText("疲労: " + pet.getFatigue());
            frame.repaint();
        });

        // 像素宠物面板
        JPanel petPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPixelPet(g);
            }
        };
        petPanel.setPreferredSize(new Dimension(200, 200));

        // 定时器实现随机移动和随机动作
        Timer timer = new Timer(500, e -> {
            movePetRandomly(); // 每隔500毫秒随机移动
            changePetAction(); // 每次移动时随机改变动作
            petPanel.repaint();
        });
        timer.start();

        // 布局组件
        JPanel panel = new JPanel();
        panel.add(hungerLabel);
        panel.add(happinessLabel);
        panel.add(fatigueLabel);
        panel.add(feedButton);
        panel.add(playButton);
        panel.add(restButton);
        panel.add(petPanel);

        frame.add(panel);
        frame.setVisible(true);
    }

    // 随机移动宠物
    private void movePetRandomly() {
        int moveX = random.nextInt(3) - 1; // -1, 0, 1 随机移动
        int moveY = random.nextInt(3) - 1;

        x += moveX * pixelSize; // 移动x轴
        y += moveY * pixelSize; // 移动y轴

        // 边界检测，防止超出面板
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + currentPetImage[0].length * pixelSize > 200) x = 200 - currentPetImage[0].length * pixelSize;
        if (y + currentPetImage.length * pixelSize > 200) y = 200 - currentPetImage.length * pixelSize;
    }

    // 随机改变宠物的动作（例如眨眼或举手）
    private void changePetAction() {
        int action = random.nextInt(3); // 0 = normal, 1 = blink, 2 = wave
        switch (action) {
            case 0:
                currentPetImage = normalPetImage;
                break;
            case 1:
                currentPetImage = blinkingPetImage;
                break;
            case 2:
                currentPetImage = wavingPetImage;
                break;
        }
    }

    // 通过像素数组绘制宠物图像
    private void drawPixelPet(Graphics g) {
        for (int i = 0; i < currentPetImage.length; i++) {
            for (int j = 0; j < currentPetImage[i].length; j++) {
                if (currentPetImage[i][j] == 1) {
                    g.fillRect(x + j * pixelSize, y + i * pixelSize, pixelSize, pixelSize);
                }
            }
        }
    }
}
