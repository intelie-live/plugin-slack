package net.intelie.live.plugins.slack;

import net.intelie.live.PrefixedLive;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SlackHandleTest {
    @Test
    public void whenClosing() throws Exception {
        PrefixedLive live = mock(PrefixedLive.class);
        SlackHandler handle = new SlackHandler(live);
        handle.close();

        verify(live).undoAll();
    }
}
