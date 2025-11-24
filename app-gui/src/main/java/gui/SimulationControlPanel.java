package gui;

import javax.swing.*;
import markov.simulation.SimulationState;

public class SimulationControlPanel extends JPanel {
    private JLabel turnLabel;
    private int currentTurn = 1;

    public SimulationControlPanel(SimulationPanel simulationPanel) {
        JButton nextButton = new JButton("Next Simulation");
        nextButton.setName("nextSimulation");
        nextButton.addActionListener(e -> {
            if (simulationPanel.paintSimulation() != SimulationState.RUNNING) {
                return;
            }
            currentTurn += 1;
            turnLabel.setText("현재 턴: " + currentTurn);
        });
        add(nextButton);
        this.turnLabel = new JLabel("현재 턴: " + currentTurn);
        this.turnLabel.setName("turn");
        add(turnLabel);
    }
}
