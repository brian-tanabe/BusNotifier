package com.bttanabe.busnotifier.test.unit.factories;

import com.btanabe.busnotifier.growl.factories.GrowlApplicationInfoFactory;
import com.btanabe.busnotifier.growl.factories.GrowlNotificationInfoFactory;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpNotificationInfo;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/20/16.
 */
@ContextConfiguration("classpath:*spring-configuration/unit-testing-configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NotificationInfoFactoryTests {

    private static final String NOTIFICATION_NAME = "test-notification-name";
    private static final String NOTIFICATION_TITLE = "test-notification-title";
    private static final String NOTIFICATION_TEXT = "notification-text";
    private static final String EXPECTED_APPLICATION_NAME = "Bus Notifier";

    private static GntpApplicationInfo applicationInfo;

    @BeforeClass
    public static void createTestApplicationInfo() {
        GrowlApplicationInfoFactory factory = new GrowlApplicationInfoFactory(EXPECTED_APPLICATION_NAME);
        applicationInfo = factory.createApplicationInfo();
    }

    @Test
    public void shouldBeAbleToCreateNotificationInfoWithTheCorrectNotificationName() throws Exception {
        GntpNotificationInfo testNotification = GrowlNotificationInfoFactory.createNotificationInfo(applicationInfo, NOTIFICATION_NAME);
        assertThat(testNotification.getName(), is(equalTo(NOTIFICATION_NAME)));
    }

    @Test
    public void shouldBeAbleToCreateNotificationInfoWithTheCorrectApplicationName() throws Exception {
        GntpNotificationInfo testNotification = GrowlNotificationInfoFactory.createNotificationInfo(applicationInfo, NOTIFICATION_NAME);
        assertThat(testNotification.getApplicationInfo().getName(), is(equalTo(EXPECTED_APPLICATION_NAME)));
    }
}
