package markov;

import javax.swing.*;

public class SimulationControlPanel extends JPanel {

    public SimulationControlPanel(SimulationPanel simulationPanel) {
        JButton nextButton = new JButton("Next Simulation");
        nextButton.setName("nextSimulation");
        nextButton.addActionListener(e -> simulationPanel.paintSimulation());
        add(nextButton);
    }
}
