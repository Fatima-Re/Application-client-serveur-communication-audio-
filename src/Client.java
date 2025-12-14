import java.net.*;
import java.io.*;

public class Client {
    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private static Audio audioModule = new Audio();
    private NetworkController controller; // <- added

    // CONSTRUCTOR
    public Client(String host, int port, NetworkController controller) throws IOException {
        this.controller = controller; // save reference
        this.socket = new Socket(host, port);
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
        startReceiverThread();
        System.out.println("Connecté au serveur " + host + ":" + port);
    }

    // SEND TEXT
    public void sendMessage(String message) {
        try {
            out.write((message + "\n").getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // SEND AUDIO
    public void sendAudio(byte[] audioData) {
        if (audioData == null || audioData.length == 0) return;

        try {
            out.write("[AUDIO]".getBytes()); // identifier audio
            out.write(audioData);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // RECEIVE LOOP
    private void startReceiverThread() {
        Thread receiver = new Thread(() -> {
            try {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    String check = new String(buffer, 0, Math.min(7, len));

                    if (check.startsWith("[AUDIO]")) {
                        byte[] audioData = new byte[len - 7];
                        System.arraycopy(buffer, 7, audioData, 0, audioData.length);

                        // 🔹 Update GUI via controller
                        if (controller != null) {
                            controller.onAudioReceived(audioData);
                        } else {
                            audioModule.playAudio(audioData); // fallback
                        }

                    } else {
                        String msg = new String(buffer, 0, len).trim();
                        System.out.println("Serveur : " + msg);

                        // 🔹 Update GUI via controller
                        if (controller != null) {
                            controller.onMessageReceived(msg);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Connexion au serveur perdue");
            }
        });
        receiver.start();
    }

    // DISCONNECT
    public void disconnect() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) { }
    }
}
