package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.map.MapReader;
import markov.movement.MovementReader;

public class Application {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(
                MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP))
        );
    }
}
