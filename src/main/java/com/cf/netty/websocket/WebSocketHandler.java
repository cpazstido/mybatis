package com.cf.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + ": " + msg.text());
        group.writeAndFlush(new TextWebSocketFrame("来自服务端: " + msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (Channel ch : group) {
            ch.writeAndFlush(
                    "[" + channel.remoteAddress() + "] " + "is comming");
        }
        group.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.remove(channel);
//        for (Channel ch : group) {
//            ch.writeAndFlush(new TextWebSocketFrame("[" + channel.remoteAddress() + "] " + "is outing"));
//        }
//        System.out.println("[" + channel.remoteAddress() + "] " + "is outing");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("[" + channel.remoteAddress() + "] " + "offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(
                "[" + ctx.channel().remoteAddress() + "]" + "exit the room");
        ctx.close().sync();
    }
}
