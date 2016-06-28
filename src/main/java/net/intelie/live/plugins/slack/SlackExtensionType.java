package net.intelie.live.plugins.slack;

import net.intelie.live.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SlackExtensionType implements ExtensionType<SlackConfig> {
    private final Live live;

    @Autowired
    public SlackExtensionType(Live live) {
        this.live = live;
    }

    @Override
    public String typename() {
        return "slack";
    }

    @Override
    public ExtensionArea area() {
        return ExtensionArea.INTEGRATION;
    }


    @Override
    public Set<ExtensionRole> roles() {
        return ExtensionRole.start().with(ExtensionRole.NOTIFICATION).ok();
    }

    @Override
    public ElementHandle register(ExtensionQualifier qualifier, final SlackConfig config) throws Exception {
        return SafeElement.create(live, qualifier, new SafeElement.Starter() {
            @Override
            public ElementHandle run(PrefixedLive prefixed, ExtensionQualifier qualifier) throws Exception {
                return config.create(prefixed, qualifier);
            }
        });
    }

    @Override
    public ElementState test(ExtensionQualifier qualifier, SlackConfig config) throws Exception {
        PrefixedLive prefixed = live.as(qualifier.fullQualifier());

        try {
            return config.test(prefixed);
        } finally {
            prefixed.undoAll();
        }
    }

    @Override
    public SlackConfig parseConfig(String config) throws Exception {
        return LiveJson.fromJson(config, SlackConfig.class);
    }
}
