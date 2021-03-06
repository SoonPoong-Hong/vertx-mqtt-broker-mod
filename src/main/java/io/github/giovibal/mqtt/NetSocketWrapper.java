package io.github.giovibal.mqtt;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

/**
 * Created by giova_000 on 29/06/2015.
 */
public class NetSocketWrapper {

    private NetSocket netSocket;

    public NetSocketWrapper(NetSocket netSocket) {
        if(netSocket==null)
            throw new IllegalArgumentException("MQTTNetSocketWrapper: netSocket cannot be null");
        this.netSocket = netSocket;
    }

    public void sendMessageToClient(Buffer bytes) {
        try {
            netSocket.write(bytes);
            if (netSocket.writeQueueFull()) {
                netSocket.pause();
                netSocket.drainHandler( done -> netSocket.resume() );
            }
        } catch(Throwable e) {
            Container.logger().error(e.getMessage());
        }
    }
}
