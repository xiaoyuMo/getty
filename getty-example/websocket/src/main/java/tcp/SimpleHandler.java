package tcp;


import com.gettyio.core.channel.SocketChannel;
import com.gettyio.core.handler.codec.websocket.frame.*;
import com.gettyio.core.pipeline.in.SimpleChannelInboundHandler;

public class SimpleHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    public void channelAdded(SocketChannel aioChannel) {

        System.out.println("连接成功");

    }

    @Override
    public void channelClosed(SocketChannel aioChannel) {
        System.out.println("连接关闭了");
    }


    @Override
    public void channelRead0(SocketChannel aioChannel, WebSocketFrame frame) {

        if (frame instanceof BinaryWebSocketFrame) {
            System.out.println("类型匹配:");
        }

    }

    @Override
    public void exceptionCaught(SocketChannel aioChannel, Throwable cause) throws Exception {
        System.out.println("出错了");
    }
}
