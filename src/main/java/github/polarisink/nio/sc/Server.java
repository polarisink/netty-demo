package github.polarisink.nio.sc;

import github.polarisink.nio.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aries
 * @date 2021/7/18
 */
@Slf4j
public class Server {
  public static void main(String[] args) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(16);
    //创建服务器
    ServerSocketChannel ssc = ServerSocketChannel.open();
    //设置非阻塞模式
    ssc.configureBlocking(false);
    //绑定端口
    ssc.bind(new InetSocketAddress(8080));
    //连接集合
    List<SocketChannel> channels = new ArrayList<>();
    //会导致cpu 100%,由此引入Selector
    while (true) {
      //建立与客户连接
      log.info("connecting.....");
      //阻塞，线程停止运行
      SocketChannel accept = ssc.accept();
      if (accept != null) {
        accept.configureBlocking(false);
        channels.add(accept);
        log.info("connected,{}", accept);
      }
      for (SocketChannel sc : channels) {
        //接收数据
        log.info("start read");
        //非阻塞，没有读到数据，仍然会返回0
        int read = sc.read(buffer);
        if (read > 0) {
          buffer.flip();
          ByteBufferUtil.debugRead(buffer);
          buffer.clear();
          log.info("stop read");
        }
      }
    }
  }
}
