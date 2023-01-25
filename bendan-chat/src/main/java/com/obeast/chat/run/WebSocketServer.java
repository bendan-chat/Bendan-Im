package com.obeast.chat.run;


import com.obeast.chat.business.service.ChatRecordService;
import com.obeast.chat.business.domain.ChatChannelGroup;
import com.obeast.chat.handler.*;
import com.obeast.chat.handler.newHandler.ByteMsgHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wxl
 * Date 2022/12/27 13:08
 * @version 1.0
 * Description: websocket 服务器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketServer implements CommandLineRunner {

    @Value("${netty.host}")
    private String HOST;

    @Value("${netty.port}")
    private Integer PORT;

    private final RabbitTemplate rabbitTemplate;

    private final ChatRecordService chatRecordService;

    private final ChatChannelGroup chatChannelGroup;

    @Override
    public void run(String... args) throws Exception {

        EventLoopGroup master = new NioEventLoopGroup();
        EventLoopGroup slave = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        ChannelFuture bind = serverBootstrap
                .group(master, slave)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline
                                // HttpRequestHandler
                                .addLast(new HttpServerCodec())
                                // FullHttpRequestHandler
                                .addLast(new HttpObjectAggregator(65536))
                                // webSocket 协议解析
                                .addLast(new WebSocketServerProtocolHandler("/",null, false, 65536))
                                //客户端 (TimeUnit)后不发消息自动断开
                                .addLast(new ReadTimeoutHandler(10, TimeUnit.MINUTES))
//                                .addLast(new ByteMsgHandler())
                                //WebSocket客服端处理器
                                .addLast(new SeriChannelInHandler())
                                //关闭连接
                                .addLast(new ShutDownMsgHandler(chatChannelGroup))
                                // 新建连接处理器
                                .addLast(new ConnectMsgHandler(chatChannelGroup))
                                //心跳
                                .addLast(new HeardMsgHandler())
                                // 文本
                                .addLast(new ChatMsgHandler(chatChannelGroup, rabbitTemplate, chatRecordService))
                        ;


                    }
                })
                .bind(PORT);
        bind.sync();
        log.info("聊天服务器初始化启动成功------>");
    }
}
