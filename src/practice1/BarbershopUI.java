package practice1;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class BarbershopUI {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private Queue<JLabel> labels;

    public BarbershopUI(Barbershop barbershop) {
        frame = new JFrame("Barbershop");
        panel = new JPanel();
        button = new JButton("Add client");
        labels = new LinkedList<>();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        button.addActionListener(e -> {
            barbershop.addClient();
        });

        panel.add(button);
        frame.setVisible(true);
    }

    // Метод для обновления UI
    // Метод для обновления UI (добавление клиента)
    public void addClientUI() {
        if (labels.size() < Barbershop.MAX_SEATS) {
            JLabel label = new JLabel("Client");
            label.setOpaque(true);
            label.setBackground(Color.RED);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
            labels.add(label);
            panel.add(label);
            panel.revalidate();
            panel.repaint();
        }
    }

    // Метод для изменения цвета клиента при стрижке
    public void changeClientColorToOrange() {
        if(!labels.isEmpty()) {
            JLabel firstClient = labels.peek();
            firstClient.setBackground(Color.ORANGE);
            panel.revalidate();
            panel.repaint();
        }
    }

    public void removeClientUI() {
        if (!labels.isEmpty()) {
            JLabel firstClient = labels.poll();
            panel.remove(firstClient);
            panel.revalidate();
            panel.repaint();
        }
    }
}
