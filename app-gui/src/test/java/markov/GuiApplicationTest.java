package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.map.MapReader;
import markov.movement.MovementReader;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class GuiApplicationTest {
    private FrameFixture window;

    @Before
    public void onSetUp() {
        MainFrame frame = GuiActionRunner.execute(() -> new MainFrame(
                MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP)),
                MovementReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.PROBABILITY))
        ));
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
        String text = window.textBox("messageLog").text();
        assertThat(text).contains("[SUCCESS] 지도를 불러왔습니다");
    }

    @Test
    public void shouldDisplayMessageWhenMovementLoadsSuccessfully() {
        String text = window.textBox("messageLog").text();
        assertThat(text).contains("[SUCCESS] 위치 별 이동 확률을 불러왔습니다");
    }
}
