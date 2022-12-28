
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author aries
 * @date 2021/7/19
 */

@Slf4j
public class WriteClient {
  public static void main(String[] args) throws IOException {
    SocketChannel sc = SocketChannel.open();
    sc.connect(new InetSocketAddress("localhost", 8080));
    //3、接收数据
    int count = 0;
    while (true) {
      ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
      count += sc.read(buffer);
      log.info("count,{}", count);
      buffer.clear();
    }


  }
}
