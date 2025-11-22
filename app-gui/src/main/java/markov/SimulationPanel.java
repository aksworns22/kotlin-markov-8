package markov;

import markov.map.Location;
import markov.map.Position;
import markov.map.SimulationMap;
import markov.output.SimulationOutput;
import markov.simulation.Simulation;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SimulationPanel extends JPanel implements SimulationOutput {
    private ArrayList<Simulation> resultQueue = new ArrayList<>();
    private int current = 0;

    SimulationPanel() {
        setName("simulationPanel");
        setLayout(new GridBagLayout());
    }

    @Override
    public void println(@NotNull Simulation simulation) {
        resultQueue.add(simulation);
    }

    public void paintSimulation() {
        if (current >= resultQueue.size()) return;
        Simulation currentSimulation = resultQueue.get(current);
        SimulationMap map = currentSimulation.getMap();
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        for (int r = 0; r < map.getSize().getHeight(); r++) {
            for (int c = 0; c < map.getSize().getWidth(); c++) {
                Position pos = new Position(c, r);
                Location locType;
                if (map.getStart().equals(pos)) {
                    locType = Location.START;
                } else if (map.getDestination().equals(pos)) {
                    locType = Location.DESTINATION;
                } else {
                    locType = Location.WAYPOINT;
                }
                gbc.gridx = c;
                gbc.gridy = r;
                add(new SimulationLocation(pos, locType), gbc);
            }
        }
        current += 1;
        revalidate();
        repaint();
    }
}
