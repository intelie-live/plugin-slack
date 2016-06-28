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