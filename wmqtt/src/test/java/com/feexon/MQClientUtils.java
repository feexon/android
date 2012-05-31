package com.feexon;

import com.ibm.mqtt.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 * @version 1.0 12-5-29,下午2:28
 */
public class MQClientUtils {
    private MQClientUtils() {
    }

    private static Set<IMqttClient> clients = new HashSet<IMqttClient>();

    static IMqttClient createMQClient(String serverHost, int listenPort) throws MqttException {
        MqttPersistence persistence = null;
        String connectionUrl = MqttClient.TCP_ID + serverHost + "@" + listenPort;
        IMqttClient client = MqttClient.createMqttClient(connectionUrl, persistence);
        clients.add(client);
        return client;
    }

    static IMqttClient createLocalClient() throws MqttException {
        return createMQClient("127.0.0.1", 1883);
    }

    public static void destroy() {
        for (IMqttClient client : clients) {
            client.terminate();
            try {
                if (client.isConnected())
                    client.disconnect();
            } catch (MqttPersistenceException ignored) {

            }
        }
        clients.clear();
    }
}
