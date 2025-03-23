package com.ship.proxy.slave.config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.stereotype.Component;


@Component
public class ClientProxyConfig {

    private static final String SERVER_HOST = "127.0.0.1"; // Offshore Proxy IP
    private static final int SERVER_PORT = 6000;
    private EventLoopGroup group;
    private Channel channel;

    @PostConstruct
    public void start() throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());

        ChannelFuture future = bootstrap.connect(SERVER_HOST, SERVER_PORT).sync();
        channel = future.channel();
        System.out.println("Connected to Offshore Proxy at " + SERVER_HOST + ":" + SERVER_PORT);
    }

    public void sendMessage(String message) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(message);
            System.out.println("Message sent to Offshore Proxy: " + message);
        } else {
            System.err.println("Connection not active. Unable to send message.");
        }
    }

    @PreDestroy
    public void shutdown() {
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}

