package markov;

import markov.map.MapSize;
import markov.map.SimulationMapController;
import markov.movement.MovementController;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GuiApplicationTest {
    private FrameFixture window;
    private MessageLogger messageLogger;

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
}
