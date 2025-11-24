package gui;

import java.awt.Component;
import javax.swing.JOptionPane;

public class SimulationResultDialog implements SimulationResultListener {
    @Override
    public void onFail(Component component) {
        JOptionPane.showMessageDialog(
                component,
                "시간 제한 내에 목적지에 도착하지 못했습니다",
                "Simulation Fail",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void onSuccess(Component component) {
        JOptionPane.showMessageDialog(
                component,
                "시간 제한 내에 목적지에 도착했습니다",
                "Simulation Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
