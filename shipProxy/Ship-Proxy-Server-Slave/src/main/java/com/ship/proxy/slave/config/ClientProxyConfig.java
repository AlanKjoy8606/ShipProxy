package com.ship.proxy.slave.config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ClientProxyConfig {
    private static final String SERVER_HOST = "offshore-proxy";
    private static final int SERVER_PORT = 6000;
    private EventLoopGroup group;
    private Channel channel;
    private final BlockingQueue<String> requestQueue = new LinkedBlockingQueue<>();
    private volatile boolean isProcessing = false;

    @PostConstruct
    public void start() throws InterruptedException {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());

        // Establish single persistent connection
        ChannelFuture future = bootstrap.connect(SERVER_HOST, SERVER_PORT).sync();
        channel = future.channel();
        System.out.println("Established persistent connection to Offshore Proxy");
        
        // Start sequential request processing
        new Thread(this::processRequests).start();
    }

    public void sendMessage(String message) {
        requestQueue.add(message);
    }

    private void processRequests() {
        while (true) {
            try {
                if (!isProcessing && channel.isActive()) {
                    isProcessing = true;
                    String message = requestQueue.take();
                    channel.writeAndFlush(message);
                    System.out.println("Request sent through persistent connection: " + message);
                    isProcessing = false;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}