package gui;

import javax.swing.*;

public class SimulationControlPanel extends JPanel {
    private JLabel turnLabel;
    private int currentTurn = 0;

    public SimulationControlPanel(SimulationPanel simulationPanel) {
        JButton nextButton = new JButton("Next Simulation");
        nextButton.setName("nextSimulation");
        nextButton.addActionListener(e -> {
            simulationPanel.paintSimulation();
            currentTurn += 1;
            turnLabel.setText("현재 턴: " + currentTurn);
        });
        add(nextButton);
        this.turnLabel = new JLabel("현재 턴: 0");
        this.turnLabel.setName("turn");
        add(turnLabel);
    }
}
