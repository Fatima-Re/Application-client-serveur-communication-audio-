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

    // Send text
    public void sendMessage(String username, String message) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeUTF("[TEXT]");
            dos.writeUTF(username);
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send audio
    public void sendAudio(String username, byte[] audioData) {
        if (audioData == null || audioData.length == 0) return;

        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeUTF("[AUDIO]");
            dos.writeUTF(username);
            dos.writeInt(audioData.length);
            dos.write(audioData);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReceiverThread() {
        new Thread(() -> {
            try {
                DataInputStream dis = new DataInputStream(in);
                while (true) {
                    String type = dis.readUTF();

                    if (type.equals("[TEXT]")) {
                        String sender = dis.readUTF();
                        String msg = dis.readUTF();
                        if (controller != null) controller.onMessageReceived(sender, msg);

                    } else if (type.equals("[AUDIO]")) {
                        String sender = dis.readUTF();
                        int len = dis.readInt();
                        byte[] audioData = new byte[len];
                        dis.readFully(audioData);
                        if (controller != null) controller.onAudioReceived(sender, audioData);
                    }
                }
            } catch (IOException e) {
                System.out.println("Connexion au serveur perdue");
            }
        }).start();
    }


    // DISCONNECT
    public void disconnect() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) { }
    }
}
