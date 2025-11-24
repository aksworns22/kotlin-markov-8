package gui;

import javax.swing.*;
import markov.simulation.SimulationState;

public class SimulationControlPanel extends JPanel {
    private JLabel turnLabel;

    public SimulationControlPanel(SimulationPanel simulationPanel) {
        JButton nextButton = new JButton("Next Simulation");
        nextButton.setName("nextSimulation");
        nextButton.addActionListener(e -> {
            if (simulationPanel.paintSimulation() != SimulationState.RUNNING) {
                nextButton.setEnabled(false);
            }
            turnLabel.setText("현재 턴: " + simulationPanel.current);
        });
        add(nextButton);
        this.turnLabel = new JLabel("현재 턴: " + simulationPanel.current);
        this.turnLabel.setName("turn");
        add(turnLabel);
    }
}
