package markov;

import markov.map.SimulationMap;
import markov.map.SimulationMapController;
import markov.movement.MovementController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    MainFrame(MessageLogger messageLog, SimulationPanel simulationPanel) {
        setTitle("Open Mission: Markov Reward Process");
        setSize(600, 700);
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(
                messageLog,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setPreferredSize(new Dimension(200, 100));
        add(simulationPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.SOUTH);
        add(new SimulationControlPanel(simulationPanel));
        setVisible(true);
    }
}
