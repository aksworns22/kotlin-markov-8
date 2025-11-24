package gui;

import markov.InvalidFixtures;
import markov.map.MapSize;
import markov.map.SimulationMapController;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(Parameterized.class)
public class InvalidMapOutputTest {
    private FrameFixture window;
    private MessageLogger messageLogger;

    private final List<String> invalidMapData;

    public InvalidMapOutputTest(List<String> invalidMapData) {
        this.invalidMapData = invalidMapData;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return InvalidFixtures.INSTANCE.getRAW_MAPS().stream()
                .map(map -> new Object[]{map})
                .collect(Collectors.toList());
    }

    @Before
    public void onSetUp() {
        messageLogger = new MessageLogger();
        SimulationPanel simulationPanel = new SimulationPanel(
                new MovementController(new MapSize(2, 2), messageLogger).readMovement(
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
    public void shouldDisplayMessageWhenMapLoadsFail() {
        assertThatThrownBy(() -> new SimulationMapController(messageLogger).readMap(invalidMapData))
                .isInstanceOf(IllegalArgumentException.class);

        String text = window.textBox("messageLog").text();
        assertThat(text).contains("[ERROR]");
    }
}
