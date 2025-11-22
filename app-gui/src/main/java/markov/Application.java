package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.manual.Manual;
import markov.manual.ManualController;
import markov.map.MapReader;
import markov.map.SimulationMap;
import markov.map.SimulationMapController;
import markov.movement.Movement;
import markov.movement.MovementController;
import markov.movement.MovementReader;
import markov.random.OneToHundredGenerator;
import markov.simulation.SimulationController;
import markov.simulation.SimulationTime;

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        MessageLogger messageLogger = new MessageLogger();
        SimulationPanel simulationPanel = new SimulationPanel();
        MainFrame mainFrame = new MainFrame(messageLogger, simulationPanel);
        SimulationMap map = new SimulationMapController(messageLogger).readMap(MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP)));
        Movement movement = new MovementController(map.getSize(), messageLogger).readMovement(MovementReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.PROBABILITY)));
        Manual manual = new ManualController(simulationPanel).findBestManual(map, movement);
        new SimulationController(simulationPanel).startFrom(map, new SimulationTime(100), movement, OneToHundredGenerator.INSTANCE);
        simulationPanel.paintSimulation();
    }
}
