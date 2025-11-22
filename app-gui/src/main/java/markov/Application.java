package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.map.MapReader;
import markov.map.SimulationMap;
import markov.map.SimulationMapController;
import markov.movement.MovementController;
import markov.movement.MovementReader;

public class Application {
    public static void main(String[] args) {
        MessageLogger messageLogger = new MessageLogger();
        MainFrame mainFrame = new MainFrame(messageLogger);
        SimulationMap map = new SimulationMapController(messageLogger).readMap(MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP)));
        new MovementController(map.getSize(), messageLogger).readMovement(MovementReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.PROBABILITY)));
    }
}
