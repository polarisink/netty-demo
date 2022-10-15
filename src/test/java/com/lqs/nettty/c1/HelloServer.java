package com.lqs.nettty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
  public static void main(String[] args) {
    //1、启动器，负责组装netty组件，启动服务器
    new ServerBootstrap()
        //2、BossEventLoop WorkerEventLoop
        .group(new NioEventLoopGroup())
        //3、选择服务器的ServerSocketChannel实现
        .channel(NioServerSocketChannel.class)
        //4、boss负责处理链接worker(child)复杂读写，
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
          //5、channel代表和客户端进行数据读写的通道Initializer初始化，负责添加别的handler
          @Override
          protected void initChannel(NioSocketChannel ch) throws Exception {
            //6、添加具体handler
            ch.pipeline().addLast(new StringDecoder());//将ByteBuf转为字符串
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {//自定义的handler
              @Override//读事件
              public void channelRead(ChannelHandlerContext ctx,Object msg){
                //打印转换好的字符串
                System.out.println(msg);
              }
            });
          }
        })
        //7、绑定端口
        .bind(8080);
  }
}
