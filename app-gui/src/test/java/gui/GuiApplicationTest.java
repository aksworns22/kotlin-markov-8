package gui;

import markov.cost.CostMapController;
import markov.map.*;
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
        simulationPanel = new SimulationPanel(new MovementController(new MapSize(2, 2), messageLogger).readMovement(
                List.of("0,0:0,100,0,0", "1,0:0,100,0,0", "0,1:0,100,0,0", "1,1:0,100,0,0")
        ), new SimulationResultDialog());
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
        new CostMapController(simulationPanel).findCostMap(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(10),
                movement,
                OneToHundredRandomGenerator.INSTANCE
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
        new CostMapController(simulationPanel).findCostMap(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(10),
                movement,
                OneToHundredRandomGenerator.INSTANCE
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
        new CostMapController(simulationPanel).findCostMap(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(10),
                movement,
                OneToHundredRandomGenerator.INSTANCE
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
        new CostMapController(simulationPanel).findCostMap(map, movement);
        new SimulationController(simulationPanel).startFrom(
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(10),
                movement,
                OneToHundredRandomGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.moveTo(new Point(0, 0));
        window.resizeTo(new Dimension(1000, 800));
        window.button("nextSimulation").click();
        window.panel("simulationPanel").panel("Position(1,0)").label("Current").requireVisible();
    }

    @Test
    public void shouldDisplayMovementProbability() {
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
                new SimulationMap(
                        new MapSize(2, 2),
                        new Position(0, 0),
                        new Position(1, 1),
                        new Position(0, 0)
                ),
                new SimulationTime(2),
                movement,
                OneToHundredRandomGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        assertThat(window.panel("simulationPanel")).satisfies((panel) -> {
            panel.panel("Position(0,0) - START").label("UP").requireVisible();
            panel.panel("Position(0,0) - START").label("DOWN").requireVisible();
            panel.panel("Position(0,0) - START").label("LEFT").requireVisible();
            panel.panel("Position(0,0) - START").label("RIGHT").requireVisible();
            panel.panel("Position(1,1) - DESTINATION").label("UP").requireVisible();
            panel.panel("Position(1,1) - DESTINATION").label("DOWN").requireVisible();
            panel.panel("Position(1,1) - DESTINATION").label("LEFT").requireVisible();
            panel.panel("Position(1,1) - DESTINATION").label("RIGHT").requireVisible();
            panel.panel("Position(1,0)").label("UP").requireVisible();
            panel.panel("Position(1,0)").label("DOWN").requireVisible();
            panel.panel("Position(1,0)").label("LEFT").requireVisible();
            panel.panel("Position(1,0)").label("RIGHT").requireVisible();
            panel.panel("Position(0,1)").label("UP").requireVisible();
            panel.panel("Position(0,1)").label("DOWN").requireVisible();
            panel.panel("Position(0,1)").label("LEFT").requireVisible();
            panel.panel("Position(0,1)").label("RIGHT").requireVisible();
        });
    }

    @Test
    public void shouldDisplayCurrentTurn() {
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
                new SimulationTime(10),
                movement,
                OneToHundredRandomGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.moveTo(new Point(0, 0));
        window.resizeTo(new Dimension(1000, 800));
        window.button("nextSimulation").click();
        window.label("turn").requireText("현재 턴: 2");
    }

    @Test
    public void shouldDisplaySuccessMessage() {
        SimulationMap map = new SimulationMap(
                new MapSize(2, 2),
                new Position(0, 0),
                new Position(1, 1),
                new Position(0, 1)
        );
        Movement movement = new MovementController(map.getSize(), messageLogger).readMovement(
                List.of("0,0:0,100,0,0", "1,0:0,100,0,0", "0,1:0,100,0,0", "1,1:0,100,0,0")
        );
        new CostMapController(simulationPanel).findCostMap(map, movement);
        new SimulationController(simulationPanel).startFrom(
                map,
                new SimulationTime(1),
                movement,
                OneToHundredRandomGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.moveTo(new Point(0, 0));
        window.resizeTo(new Dimension(1000, 800));
        window.button("nextSimulation").click();
        DialogFixture dialog = window.dialog();
        assertThat(dialog.target().getTitle()).isEqualTo("Simulation Success");
    }

    @Test
    public void shouldDisplayFailMessage() {
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
                new SimulationTime(1),
                movement,
                OneToHundredRandomGenerator.INSTANCE
        );
        simulationPanel.paintSimulation();
        window.moveTo(new Point(0, 0));
        window.resizeTo(new Dimension(1000, 800));
        window.button("nextSimulation").click();
        DialogFixture dialog = window.dialog();
        assertThat(dialog.target().getTitle()).isEqualTo("Simulation Fail");
    }
}
