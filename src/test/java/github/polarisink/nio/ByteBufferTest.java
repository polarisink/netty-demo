<<<<<<<< HEAD:src/test/java/github/polarisink/ByteBufferTest.java
package github.polarisink;
========
package github.polarisink.nio;
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/ByteBufferTest.java

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author aries
 * @date 2021/7/17
 */

@Slf4j
public class ByteBufferTest {
  @Test
  public void testInput() {
    //获取输入流
    try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
      //获取buffer
      ByteBuffer buffer = ByteBuffer.allocate(10);
      //从channel读数据，写入buffer
      while (true) {
        int len = channel.read(buffer);
        log.info("length,{}", len);
        if (len == -1) {
          break;
        }
        //切换至读模式
        buffer.flip();
        //打印buffer
        while (buffer.hasRemaining()) {//有剩余
          byte b = buffer.get();
          log.debug("读到的字节,{}", b);
        }
        //切换至写模式
        buffer.clear();
      }
    } catch (Exception e) {
      log.error("exception,{}", e);
      e.printStackTrace();
    }

  }
}
