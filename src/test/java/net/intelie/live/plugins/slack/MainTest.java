package net.intelie.live.plugins.slack;

import net.intelie.live.Live;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class MainTest {
    @Test
    public void testStarting() throws Exception {
        Main main = new Main();

        Live live = mock(Live.class, RETURNS_DEEP_STUBS);
        main.start(live);

        verify(live.engine()).addExtensionType(isA(SlackExtensionType.class));
    }
}