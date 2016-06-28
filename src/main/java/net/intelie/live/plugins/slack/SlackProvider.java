package net.intelie.live.plugins.slack;

import net.intelie.live.LiveJson;
import net.intelie.live.NotificationProvider;
import net.intelie.live.QueryEvent;
import net.intelie.live.UserDef;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SlackProvider implements NotificationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlackProvider.class);
    private final String webhook;
    private final String defaultChannel;
    private final String username;
    private String host;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final Map<String, String> colors;

    public SlackProvider(String webhook, String defaultChannel, String username, String host) {
        this.webhook = webhook;
        this.defaultChannel = defaultChannel;
        this.username = username;
        this.host = host;

        // colors
        colors = new HashMap<>();
        colors.put("INFO", "#00bfff");
        colors.put("WARN", "#ff9326");
        colors.put("ERROR", "#ff4000");
        colors.put("FATAL", "#d90000");
    }

    public void test() throws Exception {
        Map<String, Object> message = new HashMap<>();
        message.put("channel", defaultChannel);
        message.put("username", username);
        message.put("text", String.format("Testing from %s", host));
        sendMessage(message);
    }

    protected Map<String, Object> buildMessage(QueryEvent event) {
        if (event.size() != 1) {
            return null;
        }

        Map<String, Object> message = event.get(0);
        Map<String, String> rule = (Map<String, String>) message.get("rule");
        Map<String, String> originalEvent = (Map<String, String>) message.get("originalEvent");

        Map<String, Object> attachment = new HashMap<>();
        attachment.put("fallback", message.get("text"));
        attachment.put("title", message.get("text"));
        String msg = String.format("%s\n\n Sent from <%s|%s>", rule.get("description"), host, host);
        attachment.put("text", msg);
        attachment.put("color", colors.get(message.get("severity")));
        List<Map<String, Object>> attachments = Arrays.asList(attachment);

        Map<String, Object> m = new HashMap<>();
        m.put("attachments", attachments);
        m.put("username", username);

        String channel = originalEvent.containsKey("slackChannel") ? originalEvent.get("slackChannel") : defaultChannel;
        if (!channel.startsWith("#")) {
            channel = "#" + channel;
        }
        m.put("channel", channel);

        return m;
    }

    @Override
    public void notify(QueryEvent event, Set<UserDef> users) throws Exception {
        Map<String, Object> message = buildMessage(event);
        if (message != null) {
            sendMessage(message);
        }
    }

    private void sendMessage(Map<String, Object> message) throws Exception {
        LOGGER.info("sending {} to channel {}", message, defaultChannel);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, LiveJson.toJson(message));
        Request request = new Request.Builder()
                .url(webhook)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            throw new Exception(String.format("HTTP/%d: %s", response.code(), response.message()));
        }
    }
}
