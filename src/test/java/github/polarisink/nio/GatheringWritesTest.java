<<<<<<<< HEAD:src/test/java/github/polarisink/GatheringWritesTest.java
package github.polarisink;
========
package github.polarisink.nio;
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/GatheringWritesTest.java

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author aries
 * @date 2021/7/18
 */
public class GatheringWritesTest {
  /**
   * 减少数据拷贝，提高效率
   */
  @Test
  public void test() {
    ByteBuffer hello = StandardCharsets.UTF_8.encode("hello");
    ByteBuffer world = StandardCharsets.UTF_8.encode("world");
    ByteBuffer lqs = StandardCharsets.UTF_8.encode("lqs");

    try (FileChannel channel = new RandomAccessFile("words.txt", "rw").getChannel()) {
      channel.write(new ByteBuffer[]{hello, world, lqs});
    } catch (IOException e) {
    }
  }
}
