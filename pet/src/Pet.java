public class Pet {
    private int hunger; // 0-100，100表示非常饥饿
    private int happiness; // 0-100，100表示非常开心
    private int fatigue; // 0-100，100表示非常疲惫

    public Pet(int hunger, int happiness, int fatigue) {
        this.hunger = hunger;
        this.happiness = happiness;
        this.fatigue = fatigue;
    }

    // Getter 和 Setter 方法
    public int getHunger() {
        return hunger;
    }

    public void feed() {
        hunger = Math.max(hunger - 10, 0); // 喂食减少饥饿值
    }

    public int getHappiness() {
        return happiness;
    }

    public void play() {
        happiness = Math.min(happiness + 10, 100); // 娱乐增加快乐值
    }

    public int getFatigue() {
        return fatigue;
    }

    public void rest() {
        fatigue = Math.max(fatigue - 10, 0); // 休息减少疲劳值
    }
}
