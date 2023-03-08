package org.game.hs;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.game.hs.cmdHandler.CmdHandlerFactory;

/**
 * Function:
 *
 * @author ZhangWY
 * @date: 2023/3/6
 * @since JDK 1.8
 */
public class ServerMain {
    public static void main(String[] args) {
        CmdHandlerFactory.init();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(
                        new HttpServerCodec(),
                        new HttpObjectAggregator(65535),
                        new WebSocketServerProtocolHandler("/webSocket"),
                        new GameMsgDecoder(), // 自定义的消息解码器
                        new GameMsgEncoder(),//自定义消息编码器
                        new GameMsgHandle()
                );
            }
        });
        try {
            ChannelFuture channelFuture = bootstrap.bind(12345).sync();
            if(channelFuture.isSuccess()){
                System.out.println("server run success");
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
