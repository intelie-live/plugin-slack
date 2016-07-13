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

import net.intelie.live.ExtensionRole;
import net.intelie.live.LiveJson;
import net.intelie.live.ValidationBuilder;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class SlackConfigTest {
    @Test
    public void testSummarize() throws Exception {
        SlackConfig config = LiveJson.fromJson("{webhook:aaa, defaultChannel:bbb}", SlackConfig.class);
        assertThat(config.summarize()).isEqualTo("live");
    }

    @Test
    public void testValidate() throws Exception {
        SlackConfig config = LiveJson.fromJson("{defaultChannel:bbb}", SlackConfig.class);
        assertThat(config.validate(new ValidationBuilder()).toStatus().messages().size()).isEqualTo(1);
    }

    @Test
    public void testAllRoles() throws Exception {
        SlackConfig config = LiveJson.fromJson("{enableForNotification: true}", SlackConfig.class);
        assertThat(config.roles()).containsOnly(ExtensionRole.NOTIFICATION);
    }

    @Test
    public void testNoRoles() throws Exception {
        SlackConfig config = LiveJson.fromJson("{enableForNotification: false}", SlackConfig.class);
        assertThat(config.roles()).isEmpty();
    }
}