package markov;

import markov.manual.Manual;
import markov.manual.ManualController;
import markov.map.*;
import markov.movement.Movement;
import markov.movement.MovementController;
import markov.random.OneToHundredGenerator;
import markov.simulation.SimulationController;
import markov.simulation.SimulationTime;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GuiApplicationTest {
    private FrameFixture window;
    private MessageLogger messageLogger;
    private SimulationPanel simulationPanel;

    @Before
    public void onSetUp() {
        messageLogger = new MessageLogger();
        simulationPanel = new SimulationPanel();
        MainFrame frame = GuiActionRunner.execute(() -> new MainFrame(messageLogger, simulationPanel));
        window = new FrameFixture(frame);
        window.show();
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    public void shouldPrintRequiredTitle() {
        window.requireTitle("Open Mission: Markov Reward Process");
    }

    @Test
    public void shouldDisplayMessageWhenMapLoadsSuccessfully() {
        new SimulationMapController(messageLogger).readMap(List.of("2x2", "s .", ". d"));
        String text = window.textBox("messageLog").text();
        assertThat(text).contains("[SUCCESS] 지도를 불러왔습니다");
    }

    @Test
    public void shouldDisplayMessageWhenMovementLoadsSuccessfully() {
        new MovementController(
                new MapSize(1, 1),
                messageLogger
        ).readMovement(List.of("0,0:25,25,25,25"));
        String text = window.textBox("messageLog").text();
        assertThat(text).contains("[SUCCESS] 위치 별 이동 확률을 불러왔습니다");
    }

    @Test
    public void shouldDisplayMapContainsStartAndDestinationPosition() {
        SimulationMap map = new SimulationMap(
                new MapSize(2, 2),
                new Position(0, 0),
                new Position(1, 1),
                new Position(0, 0)
        );
        Movement movement = new MovementController(map.getSize(), messageLogger).readMovement(
                List.of("0,0:10,20,30,40", "1,0:25,25,25,25", "0,1:70,20,10,0", "1,1:100,0,0,0")
        );
        Manual manual = new ManualController(simulationPanel).findBestManual(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(0),
                movement,
                OneToHundredGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.panel("simulationPanel").panel("Position(0,0) - START").requireVisible();
        window.panel("simulationPanel").panel("Position(1,1) - DESTINATION").requireVisible();
    }

    @Test
    public void shouldDisplayCost() {
        SimulationMap map = new SimulationMap(
                new MapSize(2, 2),
                new Position(0, 0),
                new Position(1, 1),
                new Position(0, 0)
        );
        Movement movement = new MovementController(map.getSize(), messageLogger).readMovement(
                List.of("0,0:10,20,30,40", "1,0:25,25,25,25", "0,1:70,20,10,0", "1,1:100,0,0,0")
        );
        Manual manual = new ManualController(simulationPanel).findBestManual(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(0),
                movement,
                OneToHundredGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        Color defaultColor = new JPanel().getBackground();
        assertThat(window.panel("simulationPanel")).satisfies((panel) -> {
            panel.panel("Position(0,0) - START").background().requireNotEqualTo(defaultColor);
            panel.panel("Position(1,1) - DESTINATION").background().requireNotEqualTo(defaultColor);
            panel.panel("Position(1,0)").background().requireNotEqualTo(defaultColor);
            panel.panel("Position(0,1)").background().requireNotEqualTo(defaultColor);
        });
    }

    @Test
    public void shouldDisplayCurrentLocation() {
        SimulationMap map = new SimulationMap(
                new MapSize(2, 2),
                new Position(0, 0),
                new Position(1, 1),
                new Position(0, 0)
        );
        Movement movement = new MovementController(map.getSize(), messageLogger).readMovement(
                List.of("0,0:10,20,30,40", "1,0:25,25,25,25", "0,1:70,20,10,0", "1,1:100,0,0,0")
        );
        Manual manual = new ManualController(simulationPanel).findBestManual(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(0),
                movement,
                OneToHundredGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.panel("simulationPanel").panel("Position(0,0) - START").label("Current").requireVisible();
    }

    @Test
    public void shouldDisplayNextPositionWhenClickButton() {
        SimulationMap map = new SimulationMap(
                new MapSize(2, 2),
                new Position(0, 0),
                new Position(1, 1),
                new Position(0, 0)
        );
        Movement movement = new MovementController(map.getSize(), messageLogger).readMovement(
                List.of("0,0:0,100,0,0", "1,0:0,100,0,0", "0,1:0,100,0,0", "1,1:0,100,0,0")
        );
        Manual manual = new ManualController(simulationPanel).findBestManual(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(2),
                movement,
                OneToHundredGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.maximize();
        window.button("nextSimulation").click();
        window.panel("simulationPanel").panel("Position(1,0)").label("Current").requireVisible();
    }
}
