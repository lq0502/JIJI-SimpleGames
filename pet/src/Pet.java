public class Pet {
    private int hunger; 
    private int happiness; 
    private int fatigue;

    public Pet(int hunger, int happiness, int fatigue) {
        this.hunger = hunger;
        this.happiness = happiness;
        this.fatigue = fatigue;
    }

    public int getHunger() {
        return hunger;
    }

    public void feed() {
        hunger = Math.max(hunger - 10, 0);
    }

    public int getHappiness() {
        return happiness;
    }

    public void play() {
        happiness = Math.min(happiness + 10, 100); 
    }

    public int getFatigue() {
        return fatigue;
    }

    public void rest() {
        fatigue = Math.max(fatigue - 10, 0);
    }
}
