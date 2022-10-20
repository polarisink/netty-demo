package github.polarisink.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author lqs
 * @date 2022/9/18
 */
public class FileChannelTest {
  /**
   * 测试transferTo
   *
   * @param args
   */
  public static void main(String[] args) {
    try (FileChannel from = new FileInputStream("data.txt").getChannel(); FileChannel to = new FileOutputStream("to.txt").getChannel()) {
      long size = from.size();
      for (long left = size; left > 0; ) {
        //底层会使用零拷贝优化,最多不超过2g大小的文件,优化之后可以传输大于2g的内容
        left -= from.transferTo(size - left, left, to);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
