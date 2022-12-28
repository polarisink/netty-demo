package github.polarisink.nettty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient {
  public static void main(String[] args) throws InterruptedException {
    //启动类
    new Bootstrap()
        //添加eventGroup
        .group(new NioEventLoopGroup())
        //选择客户端channel实现
        .channel(NioSocketChannel.class)
        //添加处理器
        .handler(new ChannelInitializer<NioSocketChannel>() {
          @Override//链接建立后被调用
          protected void initChannel(NioSocketChannel ch) throws Exception {
            ch.pipeline().addLast(new StringEncoder());
          }
        })
        .connect(new InetSocketAddress("localhost", 8080))
        .sync()
        .channel()
        .writeAndFlush("hello world");
  }
}
