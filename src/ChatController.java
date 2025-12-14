public interface ChatController {

    void onConnect(String host, int port);

    void onSendText(String message);

    void onStartRecording();

    void onStopRecording();

    void playAudio(byte[] audioData);

    // NEW: called by Client when a message arrives
    void onMessageReceived(String sender, String message);

    // NEW: called by Client when audio arrives
    void onAudioReceived(String sender, byte[] audioData);
}
