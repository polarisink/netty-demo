package github.polarisink.nettty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class EventLoopServer {
  public static void main(String[] args) {
    DefaultEventLoop loop = new DefaultEventLoop();
    new ServerBootstrap()
        .group(new NioEventLoopGroup())
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
          @Override
          protected void initChannel(NioSocketChannel ch) throws Exception {
            ch.pipeline().addLast("handler", new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                log.debug(buf.toString(Charset.defaultCharset()));
                //把消息传递给下一个handler
                ctx.fireChannelRead(msg);
              }
            }).addLast(loop, "handler2", new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                log.debug(buf.toString(Charset.defaultCharset()));
              }
            });
          }
        });
  }
}
