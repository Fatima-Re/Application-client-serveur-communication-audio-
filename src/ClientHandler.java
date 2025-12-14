import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    // ✅ SEND RAW BYTES
    public void send(byte[] data) throws Exception {
        out.write(data);
        out.flush();
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[8192];
            int len;

            while ((len = in.read(buffer)) != -1) {
                byte[] data = new byte[len];
                System.arraycopy(buffer, 0, data, 0, len);

                System.out.println("📩 Forwarding message...");
                Serveur.broadcast(data, this);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Client disconnected");
        } finally {
            Serveur.removeClient(this);
            try { socket.close(); } catch (Exception ignored) {}
        }
    }
}
