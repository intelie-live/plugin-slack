package net.intelie.live.plugins.slack;

import net.intelie.live.ElementHandle;
import net.intelie.live.ElementState;
import net.intelie.live.PrefixedLive;

public class SlackHandler extends ElementHandle {
    private final PrefixedLive live;
    private ElementState status = ElementState.OK;

    public SlackHandler(PrefixedLive live) {
        this.live = live;
    }

    @Override
    public ElementState status() {
        return status;
    }

    @Override
    public void close() {
        live.undoAll();
    }
}
