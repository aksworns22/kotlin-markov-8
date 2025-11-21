package markov;

import markov.input.Data;
import markov.input.DataLoader;
import markov.map.MapReader;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class GuiApplicationTest {
    private FrameFixture window;

    @Before
    public void onSetUp() {
        MainFrame frame = GuiActionRunner.execute(() -> new MainFrame(
                MapReader.INSTANCE.read(DataLoader.INSTANCE.load(Data.MAP))
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
        assertThat(text).contains("[SUCCESS]");
    }
}
