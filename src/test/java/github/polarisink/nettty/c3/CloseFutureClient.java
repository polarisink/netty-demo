package github.polarisink.nettty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class CloseFutureClient {
  public static void main(String[] args) throws Exception {
    NioEventLoopGroup group = new NioEventLoopGroup();
    ChannelFuture channelFuture = new Bootstrap()
        .group(group)
        .channel(NioServerSocketChannel.class)
        .handler(new ChannelInitializer<NioSocketChannel>() {
          @Override
          protected void initChannel(NioSocketChannel ch) throws Exception {
            //ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
            ch.pipeline().addLast(new StringEncoder());
          }})
        .connect(new InetSocketAddress("localhost", 8080));
    Channel channel = channelFuture.sync().channel();
    log.debug("channel: {}", channel);
    new Thread(() -> {
      Scanner scanner = new Scanner(System.in);
      while (true) {
        String line = scanner.nextLine();
        if ("q".equals(line)) {
          ChannelFuture close = channel.close();
          //log.debug("处理关闭之后的操作"); // 不能在这里善后
          break;
        }
        channel.writeAndFlush(line);
      }
    }, "input").start();
    //同步处理关闭
    ChannelFuture closeFuture = channel.closeFuture();
    closeFuture.sync();
    log.debug("waiting close...");
    closeFuture.sync();
    log.debug("处理关闭之后的操作");
    //异步处理关闭
    closeFuture.addListener((ChannelFutureListener) channelFuture1 -> {
      log.debug("异步处理关闭之后的操作");
      group.shutdownGracefully();
    });
  }
}
