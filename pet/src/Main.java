public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            Pet pet = PetDatabase.loadPetState();
            new PetUI(pet).createAndShowGUI();
        });
    }
}
