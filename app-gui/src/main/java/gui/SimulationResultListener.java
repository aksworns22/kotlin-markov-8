package gui;

import java.awt.Component;

public interface SimulationResultListener {
    void onFail(Component component);

    void onSuccess(Component component);
}
