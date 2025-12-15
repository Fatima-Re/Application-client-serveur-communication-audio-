
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class YapChatGUI extends JFrame {
   //make it autoscroll
    private void autoScrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            // This ensures the scroll pane moves to the very bottom
            JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());

            // Force the viewport to update
            scrollPane.getViewport().revalidate();
            scrollPane.getViewport().repaint();
        });
    }
    //all types of  messages handling
    private void appendToChat(JComponent content, boolean isMe) {

        JPanel container = new JPanel(new FlowLayout(
                isMe ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0
        ));
        container.setOpaque(false);

        MessageBubble bubble = new MessageBubble(content, isMe);
        container.add(bubble);
// Fixed vertical gap
        container.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        chatPanel.add(container);
        chatPanel.revalidate();
        chatPanel.repaint();
        // Auto-scroll to bottom
        autoScrollToBottom();
    }


    public void appendMessage(String sender, String message) {
        boolean isMe = sender.equalsIgnoreCase("YOU");

        JLabel textLabel = new JLabel(sender + ": " + message);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        appendToChat(textLabel, isMe);
    }


    //Handling sending audios
    public void appendVoiceMessage(String sender, byte[] audioData) {
        boolean isMe = sender.equalsIgnoreCase("YOU");

        // Include username in the button text
        JButton playButton = new JButton(sender + ": 🎤 Voice Message");
        playButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        playButton.setFocusPainted(false);
        playButton.addActionListener(e -> controller.playAudio(audioData));

        // Set button styling - ALWAYS BLACK TEXT for consistency
        playButton.setForeground(Color.BLACK); // Always black text
        playButton.setBackground(isMe ? new Color(139, 94, 153) : new Color(232, 210, 243));
        playButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        appendToChat(playButton, isMe);
    }

    // ===== UI COMPONENTS =====
    private JPanel chatPanel;
    private JTextField messageField;
    private JButton sendButton;
    private JButton voiceButton;
    private JButton connectButton;
    private JTextField hostField;
    private JTextField portField;
    private JScrollPane scrollPane;

    // ===== CALLBACKS (LINK TO OTHER PARTS) =====
    private ChatController controller;

    public YapChatGUI(ChatController controller) {
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {

        // ===== ICON =====
        URL imageURL = YapChatGUI.class.getResource("/YAPnoBackground.png");
        if (imageURL != null) {
            setIconImage(new ImageIcon(imageURL).getImage());
        }

        setTitle("YAP - Let's Get Yappin!");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color darkPurple = new Color(64, 22, 73);
        Color lightPurple = new Color(232, 210, 243);
        Color mediumPurple = new Color(139, 94, 153);

        getContentPane().setBackground(darkPurple);

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(darkPurple);

        JLabel titleLabel = new JLabel("let's get yappin'");
        titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 24));
        titleLabel.setForeground(lightPurple);

        hostField = new JTextField("localhost", 12);
        portField = new JTextField("5000", 5);

        connectButton = new JButton("Connect");
        connectButton.setBackground(mediumPurple);
        connectButton.setForeground(Color.WHITE);
        connectButton.addActionListener(e ->
                controller.onConnect(
                        hostField.getText(),
                        Integer.parseInt(portField.getText())
                )
        );

        topPanel.add(titleLabel);
        topPanel.add(new JLabel("IP:"));
        topPanel.add(hostField);
        topPanel.add(new JLabel("Port:"));
        topPanel.add(portField);
        topPanel.add(connectButton);

        // ===== CHAT PANEL =====
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // ===== BOTTOM PANEL =====
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(lightPurple);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        messageField = new JTextField();
        messageField.setEnabled(false);
        messageField.addActionListener(e -> sendText());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(lightPurple);

        voiceButton = new JButton("🎤");
        voiceButton.setEnabled(false);
        voiceButton.setPreferredSize(new Dimension(50, 50));
        voiceButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.onStartRecording();
            }

            public void mouseReleased(MouseEvent e) {
                controller.onStopRecording();
            }
        });
        sendButton = new JButton("✈");
        sendButton.setEnabled(false);
        sendButton.setPreferredSize(new Dimension(50, 50));
        sendButton.addActionListener(e -> sendText());

        buttonPanel.add(voiceButton);
        buttonPanel.add(sendButton);

        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void sendText() {
        String msg = messageField.getText().trim();
        if (!msg.isEmpty()) {
            controller.onSendText(msg);
            messageField.setText("");
        }
    }

    // ===== METHODS CALLED BY OTHER PARTS =====
    public void enableChat() {
        messageField.setEnabled(true);
        sendButton.setEnabled(true);
        voiceButton.setEnabled(true);
    }



}
