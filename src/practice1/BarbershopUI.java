package practice1;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class BarbershopUI {
    private final JPanel panel;
    private final Queue<JLabel> labels;
    private BarberShopUIEventListener eventListener;
    public final static Color CLIENT_PROCESS_COLOR = Color.RED;
    public final static Color CLIENT_WAIT_COLOR = Color.DARK_GRAY;
    private final static short WIN_WIDTH_SIZE = 400;
    private final static short WIN_HEIGHT_SIZE = 300;

    public void setEventListener(BarberShopUIEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public BarbershopUI() {
        JFrame frame = new JFrame(BarbershopUI.class.getName());
        panel = new JPanel();
        JButton button = new JButton("Add client");
        labels = new LinkedList<>();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIN_WIDTH_SIZE, WIN_HEIGHT_SIZE);
        frame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        button.addActionListener(e -> {
            if (eventListener != null) {
                eventListener.onAddClientButtonClicked();
            }
        });

        panel.add(button);
        frame.setVisible(true);
    }

    public void addClient(Client client) {
        if (labels.size() < Barbershop.MAX_SEATS) {
            JLabel label = new JLabel(client.getNameClient());
            label.setOpaque(true);
            label.setBackground(Color.RED);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            labels.add(label);
            panel.add(label);
            panel.revalidate();
            panel.repaint();
        }
    }

    public void changeClientColor(Client client, Color color) {
        JLabel label = findClientLabel(client);
        if (label != null) {
            label.setBackground(color);
            panel.revalidate();
            panel.repaint();
        }
    }

    public void removeClient(Client client) {
        JLabel label = findClientLabel(client);
        if (label != null) {
            labels.remove(label);
            panel.remove(label);
            panel.revalidate();
            panel.repaint();
        }
    }

    private JLabel findClientLabel(Client client) {
        for (JLabel label : labels) {
            if (label.getText().equals(client.getNameClient())) {
                return label;
            }
        }
        return null;
    }
}