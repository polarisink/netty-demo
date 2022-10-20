package github.polarisink.nio;

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
