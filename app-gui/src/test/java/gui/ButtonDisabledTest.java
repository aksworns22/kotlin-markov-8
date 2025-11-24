package gui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import markov.cost.CostMapController;
import markov.map.MapSize;
import markov.map.Position;
import markov.map.SimulationMap;
import markov.map.SimulationMapController;
import markov.movement.Movement;
import markov.movement.MovementController;
import markov.random.OneToHundredRandomGenerator;
import markov.simulation.SimulationController;
import markov.simulation.SimulationTime;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ButtonDisabledTest {
    private FrameFixture window;
    private MessageLogger messageLogger;
    private SimulationPanel simulationPanel;

    @Before
    public void onSetUp() {
        messageLogger = new MessageLogger();
        simulationPanel = new SimulationPanel(new MovementController(new MapSize(2, 2), messageLogger).readMovement(
                List.of("0,0:0,100,0,0", "1,0:0,100,0,0", "0,1:0,100,0,0", "1,1:0,100,0,0")
        ), new DoNothingResultListener());
        MainFrame frame = GuiActionRunner.execute(() -> new MainFrame(messageLogger, simulationPanel));
        window = new FrameFixture(frame);
        window.show();
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }


    @Test
    public void shouldDisableButtonWhenSimulationEnd() {
        SimulationMap map = new SimulationMap(
                new MapSize(2, 2),
                new Position(0, 0),
                new Position(1, 1),
                new Position(0, 0)
        );
        Movement movement = new MovementController(map.getSize(), messageLogger).readMovement(
                List.of("0,0:0,100,0,0", "1,0:0,100,0,0", "0,1:0,100,0,0", "1,1:0,100,0,0")
        );
        new CostMapController(simulationPanel).findCostMap(map, movement);
        new SimulationController(simulationPanel).startFrom(
                map,
                new SimulationTime(0),
                movement,
                OneToHundredRandomGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.moveTo(new Point(0, 0));
        window.resizeTo(new Dimension(1000, 800));
        window.label("turn").requireText("현재 턴: 1");
        window.button("nextSimulation").click();
        window.label("turn").requireText("현재 턴: 1");
    }

}

class DoNothingResultListener implements SimulationResultListener {

    @Override
    public void onFail(Component component) {
    }

    @Override
    public void onSuccess(Component component) {
    }
}
