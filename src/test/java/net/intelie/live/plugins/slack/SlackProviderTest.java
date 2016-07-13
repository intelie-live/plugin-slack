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

import com.google.gson.JsonElement;
import net.intelie.live.LiveJson;
import net.intelie.live.QueryEvent;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class SlackProviderTest {

    @Test
    public void testBuildMessage() throws Exception {
        SlackProvider provider = new SlackProvider(null, "#devops", "live", "http://localhost:8080");
        Map<String, Object> msg = provider.buildMessage(newQueryEvent(null));
        assertThat(msg.get("username")).isEqualTo("live");
        assertThat(msg.get("channel")).isEqualTo("#devops");
        List attachments = (List) msg.get("attachments");
        assertThat(attachments.size()).isEqualTo(1);
        assertThat(LiveJson.create().toJsonTree(attachments)).isEqualTo(LiveJson.fromJson(
                "[{\"text\":\"Hello World\\n\\n Sent from <http://localhost:8080|http://localhost:8080>\",\"title\":\"Loren ipsun\",\"fallback\":\"Loren ipsun\",\"color\":\"#00bfff\"}]", JsonElement.class));
    }

    @Test
    public void testBuildMessageWithCustomChannel() {
        SlackProvider provider = new SlackProvider(null, "#devops", "live", "http://localhost:8080");
        Map<String, Object> msg = provider.buildMessage(newQueryEvent("#foo"));
        assertThat(msg.get("username")).isEqualTo("live");
        assertThat(msg.get("channel")).isEqualTo("#foo");
    }

    @Test
    public void testBuildmessageWithoutSharp() {
        SlackProvider provider = new SlackProvider(null, "devops", "live", "http://localhost:8080");
        Map<String, Object> msg = provider.buildMessage(newQueryEvent(null));
        assertThat(msg.get("username")).isEqualTo("live");
        assertThat(msg.get("channel")).isEqualTo("#devops");
    }


    private QueryEvent newQueryEvent(String slackChannel) {
        Map<String, Object> element = new HashMap<>();
        Map<String, String> originalEvent = new HashMap<>();
        Map<String, String> rule = new HashMap<>();
        rule.put("description", "Hello World");
        element.put("text", "Loren ipsun");

        if (slackChannel != null) {
            originalEvent.put("slackChannel", slackChannel);
        }

        element.put("originalEvent", originalEvent);
        element.put("rule", rule);
        element.put("severity", "INFO");

        return new QueryEvent(element);
    }


}