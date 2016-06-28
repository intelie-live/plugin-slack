package net.intelie.live.plugins.slack;

import net.intelie.live.HtmlTag;
import net.intelie.live.Live;
import net.intelie.live.LivePlugin;

public class Main implements LivePlugin {
    @Override
    public void start(Live live) throws Exception {
        live.engine().addExtensionType(new SlackExtensionType(live));

        live.web().addContent("", this.getClass().getResource("/webcontent"));
        live.web().addTag(HtmlTag.Position.BEGIN, new HtmlTag.JsFile(live.web().resolveContent("bundle.js")));
    }
}
