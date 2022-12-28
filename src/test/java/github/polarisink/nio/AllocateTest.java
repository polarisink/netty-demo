<<<<<<<< HEAD:src/test/java/github/polarisink/AllocateTest.java
package github.polarisink;
========
package github.polarisink.nio;
>>>>>>>> 72dab76e0171801e5a72deab9d87652051c0cbab:src/test/java/github/polarisink/nio/AllocateTest.java

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author aries
 * @date 2021/7/18
 */

@Slf4j
public class AllocateTest {

  /**
   * HeapByteBuffer:堆内存，受gc影响,读写效率低
   * DirectByteBuffer：系统内存，读写效率高,分配效率低，不受gc影响，小心内存泄漏
   */
  @Test
  public void test() {
    ByteBuffer allocate = ByteBuffer.allocate(16);
    ByteBuffer allocateDirect = ByteBuffer.allocateDirect(16);
    log.info("allocate,{}", allocate.getClass());//allocate,class java.nio.HeapByteBuffer
    log.info("allocateDirect,{}", allocateDirect.getClass());//java.nio.DirectByteBuffer
  }
}
