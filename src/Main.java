import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {

        // Ask user for their username
        String username = JOptionPane.showInputDialog(null,
                "Enter your username:",
                "Login",
                JOptionPane.PLAIN_MESSAGE);

        if (username == null || username.trim().isEmpty()) {
            username = "Unknown"; // default if user cancels
        }

        // Create controller with username
        NetworkController controller = new NetworkController(username);
        YapChatGUI gui = new YapChatGUI(controller);

        // Link GUI back to controller
        controller.setGUI(gui);

        // Show the GUI
        gui.setVisible(true);
    }
}
