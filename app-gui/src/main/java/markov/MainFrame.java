package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.map.MapReader;
import markov.map.SimulationMapController;

import javax.swing.*;
import java.util.List;

public class MainFrame extends JFrame {
    MainFrame(List<String> rawMap) {
        setTitle("Open Mission: Markov Reward Process");
        setSize(600, 600);
        MessageLogger messageLog = new MessageLogger(10, 10);
        add(messageLog);
        setVisible(true);
        new SimulationMapController(messageLog).readMap(rawMap);
    }
}
