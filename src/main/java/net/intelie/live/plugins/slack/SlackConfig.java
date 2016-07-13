/*
 * (C) Copyright 2016 Intelie (http://intelie.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
