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
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class SimulationPanel extends JPanel implements SimulationOutput, CostMapOutput {
    private ArrayList<Simulation> resultQueue = new ArrayList<>();
    private final Movement movement;
    private CostMap costMap;
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

    public void paintSimulation() {
        if (current >= resultQueue.size())
            return;
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
        if (current == resultQueue.size() - 1) {
            if (currentSimulation.isFail()) {
                JOptionPane.showMessageDialog(
                        this,
                        "시간 제한 내에 목적지에 도착하지 못했습니다",
                        "Simulation Fail",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
            if (currentSimulation.isSuccess()) {
                JOptionPane.showMessageDialog(
                        this,
                        "시간 제한 내에 목적지에 도착했습니다",
                        "Simulation Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
        current += 1;
        revalidate();
        repaint();
    }
}
