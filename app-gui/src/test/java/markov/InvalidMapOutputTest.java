package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.map.SimulationMapController;
import markov.movement.MovementReader;
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
        MainFrame frame = GuiActionRunner.execute(() -> new MainFrame(messageLogger));
        window = new FrameFixture(frame);
        window.show();
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    public void shouldDisplayMessageWhenMapLoadsFail() {
        new SimulationMapController(messageLogger).readMap(invalidMapData);
        String text = window.textBox("messageLog").text();
        assertThat(text).contains("[ERROR]");
    }
}
