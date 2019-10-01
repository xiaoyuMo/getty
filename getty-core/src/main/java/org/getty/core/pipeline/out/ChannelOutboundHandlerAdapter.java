/**
 * 包名：org.getty.core.pipeline.out
 * 版权：Copyright by www.getty.com
 * 描述：
 * 邮箱：189155278@qq.com
 * 时间：2019/9/27
 */
package org.getty.core.pipeline.out;

import org.getty.core.channel.AioChannel;
import org.getty.core.channel.ChannelState;
import org.getty.core.handler.timeout.IdleState;
import org.getty.core.pipeline.ChannelHandlerAdapter;
import org.getty.core.pipeline.ChannelInOutBoundHandlerAdapter;
import org.getty.core.pipeline.PipelineDirection;

/**
 * 类名：ChannelOutboundHandlerAdapter.java
 * 描述：
 * 修改人：gogym
 * 时间：2019/9/27
 */
public abstract class ChannelOutboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelOutboundHandler {


    @Override
    public void exceptionCaught(AioChannel aioChannel, Throwable cause, PipelineDirection pipelineDirection) {
        ChannelHandlerAdapter channelHandlerAdapter = aioChannel.getDefaultChannelPipeline().lastOne(this);
        if (channelHandlerAdapter != null && channelHandlerAdapter instanceof ChannelOutboundHandlerAdapter) {
            channelHandlerAdapter.exceptionCaught(aioChannel, cause,pipelineDirection);
        }
    }


    @Override
    public void handler(ChannelState channelStateEnum, byte[] bytes, Throwable cause, AioChannel aioChannel, PipelineDirection pipelineDirection) {
        //把任务传递给下一个处理器
        ChannelHandlerAdapter channelHandlerAdapter = aioChannel.getDefaultChannelPipeline().lastOne(this);
        if (channelHandlerAdapter != null && (channelHandlerAdapter instanceof ChannelOutboundHandlerAdapter || channelHandlerAdapter instanceof ChannelInOutBoundHandlerAdapter)) {
            channelHandlerAdapter.handler(channelStateEnum, bytes, cause, aioChannel, pipelineDirection);
        } else {
            //没有下一个处理器，表示责任链已经走完，写出
            aioChannel.writeToChannel(bytes);
        }
    }

    @Override
    public void userEventTriggered(AioChannel aioChannel, IdleState evt) {
        ChannelHandlerAdapter channelHandlerAdapter = aioChannel.getDefaultChannelPipeline().lastOne(this);
        if (channelHandlerAdapter != null && (channelHandlerAdapter instanceof ChannelOutboundHandlerAdapter || channelHandlerAdapter instanceof ChannelInOutBoundHandlerAdapter)) {
            channelHandlerAdapter.userEventTriggered(aioChannel, evt);
        }
    }

    /**
     * 消息编码
     *
     * @return void
     * @params [aioChannel, bytes]
     */
    public abstract void encode(AioChannel aioChannel, byte[] bytes);

}