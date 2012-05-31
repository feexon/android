package com.feexon;

import com.ibm.mqtt.IMqttClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Administrator
 * @version 1.0 12-5-29,下午2:15
 */
public class MQClientTest extends AbstractMQClientTest {
    private IMqttClient client;

    @Before
    public void setUp() throws Exception {
        this.client = MQClientUtils.createLocalClient();
    }

    @Test
    public void testConnect() throws Exception {
        String clientId = "feexon@qq.com";
        boolean cleanSession = true;
        short keepAliveIntervalPerMinute = (short) 60;    //one minute
        client.connect(clientId, cleanSession, keepAliveIntervalPerMinute);
        assertTrue("connected", client.isConnected());
        client.ping();
    }
}
