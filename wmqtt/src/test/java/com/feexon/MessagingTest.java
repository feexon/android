package com.feexon;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttSimpleCallback;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Administrator
 * @version 1.0 12-5-29,下午2:25
 */
public class MessagingTest extends AbstractMQClientTest {

    public static final short RETRY_PER_MINUTE = 60;


    private final String sentMessage = "c2dm wmqtt protocol";
    private final String topic = "feexon/comps";
    private final boolean cleanSession = true;
    private MqttMessageReceiverSpy messageSpy;

    @Before
    public void setUp() throws Exception {
        messageSpy = new MqttMessageReceiverSpy();
    }

    @Test
    public void testPublishMessage() throws Exception {
        subscribe("feexon@foxmail.com", new String[]{topic});
        publish("feexon@qq.com", topic, sentMessage);
        messageSpy.assertReceivedMessage(sentMessage);
    }

    @Test
    public void testPublishMessage_whenSubscribeTopicUsingWildcard_willNotified() throws Exception {
        subscribe("feexon@foxmail.com", new String[]{"#"});
        publish("feexon@qq.com", topic, sentMessage);
        messageSpy.assertReceivedMessage(sentMessage);
    }

    @Test
    public void testPublishMessage_whenSubscribeTopicUsingPathMatchAfterSeparator_willNotified() throws Exception {
        subscribe("feexon@foxmail.com", new String[]{"feexon/+"});
        publish("feexon@qq.com", topic, sentMessage);
        messageSpy.assertReceivedMessage(sentMessage);
    }

    @Test
    public void testPublishMessage_withComplexTopic_whenSubscribeTopicUsingPathMatchOnly_willNotNotified() throws Exception {
        subscribe("feexon@foxmail.com", new String[]{"+"});
        publish("feexon@qq.com", topic, sentMessage);
        messageSpy.assertMessageNotArrived();
    }

    private void subscribe(String clientId, String[] interestingTopics) throws MqttException {
        int[] wishesQoS = new int[]{QoS.AT_MOST_ONCE};
        IMqttClient receiver = MQClientUtils.createLocalClient();
        receiver.registerSimpleHandler(messageSpy);
        receiver.connect(clientId, cleanSession, RETRY_PER_MINUTE);
        receiver.subscribe(interestingTopics, wishesQoS);
    }

    private void publish(String clientId, String topic, String sentMessage) throws MqttException {
        IMqttClient sender = MQClientUtils.createLocalClient();
        sender.connect(clientId, cleanSession, RETRY_PER_MINUTE);
        sender.publish(topic, sentMessage.getBytes(), QoS.AT_MOST_ONCE, false);
    }

    private class MqttMessageReceiverSpy implements MqttSimpleCallback {
        private final CountDownLatch latch;
        private String receivedMessage;

        public MqttMessageReceiverSpy() {
            this.latch = new CountDownLatch(1);
        }

        public void connectionLost() throws Exception {

        }

        public void publishArrived(String s, byte[] bytes, int i, boolean b) throws Exception {
            receivedMessage = new String(bytes);
            latch.countDown();
        }

        public void assertReceivedMessage(String expectedReceivedMessage) throws InterruptedException {
            assertTrue("message was not received", messageArrived());
            assertEquals("message", expectedReceivedMessage, receivedMessage);
        }


        public void assertMessageNotArrived() throws InterruptedException {
            assertFalse("message was arrived", messageArrived());
        }

        private boolean messageArrived() throws InterruptedException {
            int maxWaitTime = 800;
            return latch.await(maxWaitTime, TimeUnit.MILLISECONDS);
        }
    }
}
