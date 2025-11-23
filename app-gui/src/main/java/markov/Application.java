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
        SimulationMap map = new SimulationMapController(messageLogger).readMap(
                MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP))
        );
        Movement movement = new MovementController(
                map.getSize(),
                messageLogger
        ).readMovement(MovementReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.PROBABILITY)));
        SimulationPanel simulationPanel = new SimulationPanel(movement);
        MainFrame mainFrame = new MainFrame(messageLogger, simulationPanel);
        Manual manual = new ManualController(simulationPanel).findBestManual(map, movement);
        new SimulationController(simulationPanel).startFrom(
                map,
                new SimulationTime(10), movement, OneToHundredGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
    }
}
