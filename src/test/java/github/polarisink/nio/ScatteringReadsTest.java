<<<<<<<< HEAD:src/test/java/github/polarisink/ScatteringReadsTest.java
package github.polarisink;

========
package github.polarisink.nio;

import github.polarisink.nio.c1.ByteBufferUtil;
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/ScatteringReadsTest.java
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

<<<<<<<< HEAD:src/test/java/github/polarisink/ScatteringReadsTest.java
import static github.polarisink.c1.ByteBufferUtil.*;

========
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/ScatteringReadsTest.java
/**
 * @author aries
 * @date 2021/7/18
 */

@Slf4j
public class ScatteringReadsTest {
  @Test
  public void test() {
    try (FileChannel channel = new RandomAccessFile("words.txt", "r").getChannel()) {
      ByteBuffer buffer1 = ByteBuffer.allocate(3);
      ByteBuffer buffer2 = ByteBuffer.allocate(3);
      ByteBuffer buffer3 = ByteBuffer.allocate(3);
      channel.read(new ByteBuffer[]{buffer1, buffer2, buffer3});
      buffer1.flip();
      buffer2.flip();
      buffer3.flip();
      ByteBufferUtil.debugAll(buffer1);
      ByteBufferUtil.debugAll(buffer2);
      ByteBufferUtil.debugAll(buffer3);
    } catch (IOException e) {
    }
  }
}
