package net.intelie.live.plugins.slack;

import net.intelie.live.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.InetAddress;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

public class SlackExtensionTypeTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Live live;
    private SlackExtensionType type;
    private PrefixedLive prefixed;
    private ExtensionQualifier qualifier;
    private MockWebServer server;

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Before
    public void setUp() throws Exception {
        live = mock(Live.class, RETURNS_DEEP_STUBS);
        type = new SlackExtensionType(live);
        prefixed = live.as("slack/aaaa");
        qualifier = new ExtensionQualifier("slack", "aaaa");
        server = new MockWebServer();
        server.start(InetAddress.getByName("localhost"), 9000);
    }

    @Test
    public void happyCase() throws Exception {
        SlackConfig config = type.parseConfig("{webhook:'http://localhost:9000', defaultChannel:'#channel'}");
        ElementHandle handle = type.register(qualifier, config);
        assertThat(handle.status()).isEqualTo(ElementState.OK);

        SlackProvider provider = config.makeSender("http://localhost:8080");

        QueryEvent event = new QueryEvent();
        provider.notify(event, null);
    }

    @Test
    public void successfulTest() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200));
        SlackConfig config = type.parseConfig("{webhook:'http://localhost:9000/slack', defaultChannel:'#intelie'}");
        assertThat(type.test(qualifier, config)).isEqualTo(ElementState.OK);
    }

    @Test
    public void failedTest() throws Exception {
        exception.expectMessage("HTTP/404: Client Error");
        server.enqueue(new MockResponse().setResponseCode(404));
        SlackConfig config = type.parseConfig("{webhook:'http://localhost:9000/eslequi', defaultChannel:'#intelie'}");
        type.test(qualifier, config);
    }

    @Test
    public void testAreaAndRole() throws Exception {
        assertThat(type.roles()).containsOnly(
                ExtensionRole.NOTIFICATION
        );
        assertThat(type.area()).isEqualTo(ExtensionArea.INTEGRATION);
        assertThat(type.typename()).isEqualTo("slack");
    }
}
