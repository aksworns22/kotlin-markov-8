package markov;

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
        MainFrame frame = GuiActionRunner.execute(() -> new MainFrame());
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
