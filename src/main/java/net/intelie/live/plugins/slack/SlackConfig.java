package net.intelie.live.plugins.slack;

import net.intelie.live.*;

import java.util.Set;

public class SlackConfig implements ExtensionConfig {
    private String webhook = "";
    private String defaultChannel = "";
    private String username = "live";
    private boolean enableForNotification = true;

    @Override
    public ValidationBuilder validate(ValidationBuilder builder) {
        return builder
                .push(enableForNotification)
                .requiredValue(username, "username required")
                .requiredValue(webhook, "webhook required")
                .requiredValue(defaultChannel, "default channel required");
    }

    public ElementHandle create(PrefixedLive live, ExtensionQualifier qualifier) throws Exception {
        SlackProvider sender = makeSender(live.web().getSystemUrl());

        if (enableForNotification) {
            live.engine().addNotificationProvider(qualifier.fullQualifier(), sender);
        }

        return new SlackHandler(live);
    }

    public ElementState test(PrefixedLive live) throws Exception {
        SlackProvider sender = makeSender(live.web().getSystemUrl());
        sender.test();
        return ElementState.OK;
    }

    protected SlackProvider makeSender(String host) {
        return new SlackProvider(webhook, defaultChannel, username, host);
    }

    @Override
    public String summarize() {
        return username;
    }

    @Override
    public Set<ExtensionRole> roles() {
        return ExtensionRole.start()
                .define(enableForNotification, ExtensionRole.NOTIFICATION)
                .ok();
    }
}
