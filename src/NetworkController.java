import java.io.IOException;

public class NetworkController implements ChatController {
    private String username;
    private YapChatGUI gui;
    private Client client;
    private final Audio audioModule = new Audio();

    // Constructor receives username
    public NetworkController(String username) {
        this.username = username;
    }

    public void setGUI(YapChatGUI gui) {
        this.gui = gui;
    }

    // ===== CONNECTION =====
    @Override
    public void onConnect(String host, int port) {
        try {
            this.client = new Client(host, port, this); // pass controller to client
            gui.enableChat();
        } catch (IOException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
        }
    }

    // ===== SENDING TEXT =====
    @Override
    public void onSendText(String message) {
        if (client != null) {
            client.sendMessage(username, message); // send username + text
        }
        gui.appendMessage("YOU", message); // self-display
    }

    // ===== RECORDING AUDIO =====
    @Override
    public void onStartRecording() {
        audioModule.startRecording();
    }

    @Override
    public void onStopRecording() {
        byte[] audioData = audioModule.stopRecording();
        if (client != null && audioData != null) {
            client.sendAudio(username, audioData); // send username + audio
            gui.appendVoiceMessage("YOU", audioData); // self-display
        }
    }

    // ===== PLAY AUDIO LOCALLY =====
    @Override
    public void playAudio(byte[] audioData) {
        audioModule.playAudio(audioData);
    }

    // ===== RECEIVING TEXT FROM OTHER USERS =====
    @Override
    public void onMessageReceived(String sender, String msg) {
        if (gui != null) gui.appendMessage(sender, msg);
    }

    // ===== RECEIVING AUDIO FROM OTHER USERS =====
    @Override
    public void onAudioReceived(String sender, byte[] audioData) {
        if (gui != null) gui.appendVoiceMessage(sender, audioData);
    }
}
