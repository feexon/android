package com.feexon;

import org.junit.After;

/**
 * @author Administrator
 * @version 1.0 12-5-29,下午2:59
 */
abstract  public class AbstractMQClientTest {
    @After
    public void tearDown() throws Exception {
        MQClientUtils.destroy();
    }
}
