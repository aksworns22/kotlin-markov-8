package markov;

import markov.map.SimulationMap;
import markov.map.SimulationMapController;
import markov.movement.MovementController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    MainFrame(List<String> rawMap, List<String> rawMovement) {
        setTitle("Open Mission: Markov Reward Process");
        setSize(600, 600);
        setLayout(new BorderLayout());
        MessageLogger messageLog = new MessageLogger(1, 1);
        JScrollPane scrollPane = new JScrollPane(
                messageLog,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setPreferredSize(new Dimension(200, 100));
        add(scrollPane, BorderLayout.SOUTH);
        setVisible(true);
        SimulationMap map = new SimulationMapController(messageLog).readMap(rawMap);
        if (map != null) {
            new MovementController(map.getSize(), messageLog).readMovement(rawMovement);
        }
    }
}
