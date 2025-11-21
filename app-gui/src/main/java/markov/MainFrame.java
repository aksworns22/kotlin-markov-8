package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.map.MapReader;
import markov.map.SimulationMapController;

import javax.swing.*;

public class MainFrame extends JFrame {
    MainFrame() {
        setTitle("Open Mission: Markov Reward Process");
        setSize(600, 600);
        MessageLogger messageLog = new MessageLogger(10, 10);
        add(messageLog);
        setVisible(true);
        new SimulationMapController(messageLog).readMap(MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP)));
    }
}
