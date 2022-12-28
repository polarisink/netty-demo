package github.polarisink.sc;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static github.polarisink.c1.ByteBufferUtil.*;

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
    ssc.configureBlocking(false);//设置非阻塞模式

    //绑定端口
    ssc.bind(new InetSocketAddress(8080));
    //连接集合
    List<SocketChannel> channels = new ArrayList<>();

    //会导致cpu 100%,由此引入Selector
    while (true) {

      //建立与客户连接
      log.info("connecting.....");
      SocketChannel accept = ssc.accept();//阻塞，线程停止运行
      if (accept != null) {
        accept.configureBlocking(false);
        channels.add(accept);
        log.info("connected,{}", accept);
      }
      for (SocketChannel sc : channels) {
        //接收数据
        log.info("start read");
        int read = sc.read(buffer);//非阻塞，没有读到数据，仍然会返回0
        if (read > 0) {
          buffer.flip();
          debugRead(buffer);
          buffer.clear();
          log.info("stop read");
        }
      }
    }
  }
}
