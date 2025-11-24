package gui;

import markov.cost.Cost;
import markov.cost.CostMap;
import markov.map.Location;
import markov.map.Position;
import markov.map.SimulationMap;
import markov.movement.Movement;
import markov.output.CostMapOutput;
import markov.output.SimulationOutput;
import markov.simulation.Simulation;
import markov.simulation.SimulationState;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class SimulationPanel extends JPanel implements SimulationOutput, CostMapOutput {
    private ArrayList<Simulation> resultQueue = new ArrayList<>();
    private SimulationResultListener simulationResultListener;
    private final Movement movement;
    private CostMap costMap;
    private double maxManualValue = Double.MIN_VALUE;
    private double minManualValue = Double.MAX_VALUE;
    public int current = 0;

    SimulationPanel(Movement movement, SimulationResultListener simulationResultListener) {
        this.movement = movement;
        this.simulationResultListener = simulationResultListener;
        setName("simulationPanel");
        setLayout(new GridBagLayout());
    }

    @Override
    public void println(@NotNull Simulation simulation) {
        resultQueue.add(simulation);
    }


    @Override
    public void println(@NotNull CostMap costMap) {
        this.costMap = costMap;
        Map<Position, Cost> value = costMap.getCostMap();
        for (Cost cost : value.values()) {
            if (cost.getValue() > maxManualValue)
                maxManualValue = cost.getValue();
            if (cost.getValue() < minManualValue)
                minManualValue = cost.getValue();
        }
    }

    private double normalize(Cost cost) {
        return (cost.getValue() - minManualValue) / (maxManualValue - minManualValue);
    }

    public SimulationState paintSimulation() {
        if (current >= resultQueue.size()) {
            return resultQueue.getLast().getState();
        }

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
                            normalize(costMap.getCostMap().get(new Position(c, r))),
                            movement.getProbabilities().get(new Position(c, r))), gbc);
                }
            }
        }
        current += 1;
        revalidate();
        repaint();
        if (current == resultQueue.size()) {
            if (currentSimulation.isFail()) {
                simulationResultListener.onFail(this);
            }
            if (currentSimulation.isSuccess()) {
                simulationResultListener.onSuccess(this);
            }
            return resultQueue.getLast().getState();
        }
        return SimulationState.RUNNING;
    }
}
