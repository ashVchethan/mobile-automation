package utilities;

import org.testng.ISuite;

import java.util.List;

public class SlackReportUtil {
    // TODO: Configure Slack webhook URL
    private static final String SLACK_WEBHOOK_URL = System.getProperty("slackWebhookUrl", "");

    public static void sendReportToSlack(ISuite suite, List<String> exceptions) {
        if (SLACK_WEBHOOK_URL.isEmpty()) {
            System.out.println("Slack webhook URL not configured, skipping report.");
            return;
        }
        // TODO: Implement Slack notification via webhook
    }
}
