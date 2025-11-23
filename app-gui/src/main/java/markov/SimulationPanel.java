package markov;

import markov.manual.Cost;
import markov.manual.Manual;
import markov.map.Location;
import markov.map.Position;
import markov.map.SimulationMap;
import markov.movement.Movement;
import markov.output.ManualOutput;
import markov.output.SimulationOutput;
import markov.simulation.Simulation;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class SimulationPanel extends JPanel implements SimulationOutput, ManualOutput {
    private ArrayList<Simulation> resultQueue = new ArrayList<>();
    private final Movement movement;
    private Manual manual;
    private double maxManualValue = Double.MIN_VALUE;
    private double minManualValue = Double.MAX_VALUE;
    private int current = 0;

    SimulationPanel(Movement movement) {
        this.movement = movement;
        setName("simulationPanel");
        setLayout(new GridBagLayout());
    }

    @Override
    public void println(@NotNull Simulation simulation) {
        resultQueue.add(simulation);
    }


    @Override
    public void println(@NotNull Manual manual) {
        this.manual = manual;
        Map<Position, Cost> costMap = manual.getCostMap();
        for (Cost cost : costMap.values()) {
            if (cost.getValue() > maxManualValue) maxManualValue = cost.getValue();
            if (cost.getValue() < minManualValue) minManualValue = cost.getValue();
        }
    }

    private double normalize(Cost cost) {
        return (cost.getValue() - minManualValue) / (maxManualValue - minManualValue);
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
                boolean isCurrentPosition = false;
                Position pos = new Position(c, r);
                Location locType;
                if (map.getStart().equals(pos)) {
                    locType = Location.START;
                } else if (map.getDestination().equals(pos)) {
                    locType = Location.DESTINATION;
                } else {
                    locType = Location.WAYPOINT;
                }
                if (map.getCurrent().equals(pos)) {
                    isCurrentPosition = true;
                }
                gbc.gridx = c;
                gbc.gridy = r;
                if (!movement.getProbabilities().isEmpty()) {
                    add(new SimulationLocation(
                            pos,
                            isCurrentPosition,
                            locType,
                            normalize(manual.getCostMap().get(new Position(c, r))),
                            movement.getProbabilities().get(new Position(c, r))), gbc);
                }

            }
        }
        current += 1;
        revalidate();
        repaint();
    }
}
