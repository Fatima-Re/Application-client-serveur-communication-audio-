import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MessageBubble extends JPanel {

    // ===== Constructor for a String (text messages) =====
    public MessageBubble(String message, boolean isMe) {
        this(new JLabel("<html><body style='width:200px'>" + message + "</body></html>"), isMe);
        ((JLabel)((JPanel)getComponent(0)).getComponent(0)).setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    // ===== Constructor for a JComponent (buttons, audio, images...) =====
    public MessageBubble(JComponent content, boolean isMe) {

        setLayout(new BorderLayout());
        // inside MessageBubble
        setBorder(new EmptyBorder(4, 6, 4, 6));

        setBackground(isMe ?  new Color(139, 94, 153) : new Color(232, 210, 243));
        setOpaque(false);

        JPanel bubble = new JPanel(new BorderLayout());
        bubble.setBackground(isMe ? new Color(139, 94, 153) : new Color(232, 210, 243));
        bubble.setBorder(new EmptyBorder(4, 6, 4, 6));

        bubble.add(content, BorderLayout.CENTER);
        add(bubble);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}
