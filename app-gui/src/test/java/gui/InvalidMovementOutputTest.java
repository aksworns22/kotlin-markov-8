package gui;

import markov.InvalidFixtures;
import markov.map.MapSize;
import markov.movement.MovementController;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(Parameterized.class)
public class InvalidMovementOutputTest {
    private FrameFixture window;
    private MessageLogger messageLogger;

    private final List<String> invalidMovementData;
    private final MapSize mapSize;

    public InvalidMovementOutputTest(List<Object> data) {
        this.mapSize = (MapSize) data.getFirst();
        this.invalidMovementData = (List<String>) data.getLast();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return InvalidFixtures.INSTANCE.getRAW_MOVEMENTS().stream()
                .map(data -> new Object[]{data})
                .collect(Collectors.toList());
    }

    @Before
    public void onSetUp() {
        messageLogger = new MessageLogger();
        SimulationPanel simulationPanel = new SimulationPanel(new MovementController(new MapSize(2, 2), messageLogger).readMovement(
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
    public void shouldDisplayMessageWhenMovementLoadsFail() {
        assertThatThrownBy(() -> new MovementController(mapSize, messageLogger).readMovement(invalidMovementData));
        String text = window.textBox("messageLog").text();
        assertThat(text).contains("[ERROR]");
    }
}
