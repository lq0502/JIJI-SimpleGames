public class Main {
    public static void main(String[] args) {
        // 启动UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            Pet pet = PetDatabase.loadPetState();
            new PetUI(pet).createAndShowGUI();
        });
    }
}
