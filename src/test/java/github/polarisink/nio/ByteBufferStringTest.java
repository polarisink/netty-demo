package github.polarisink.nio;

import github.polarisink.nio.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author aries
 * @date 2021/7/18
 * <p>
 * 字符串 ByteBuffer互相转换
 */
@Slf4j
public class ByteBufferStringTest {
  @Test
  public void testString() {
    String hello = "hello";

    //1 手动创建,还处于写模式
    ByteBuffer allocate = ByteBuffer.allocate(16);
    byte[] bytes = hello.getBytes(StandardCharsets.UTF_8);
    allocate.put(bytes);
    ByteBufferUtil.debugAll(allocate);

    //2 encode方法，自动进入读模式
    ByteBuffer encode = StandardCharsets.UTF_8.encode(hello);
    ByteBufferUtil.debugAll(encode);

    //3 wrap，自动进入读模式
    ByteBuffer wrap = ByteBuffer.wrap(bytes);
    ByteBufferUtil.debugAll(wrap);

    String string = StandardCharsets.UTF_8.decode(encode).toString();
    //需要进入读模式才能进行正常读,allocate.flip()
    String string2 = StandardCharsets.UTF_8.decode(allocate).toString();

    log.info("trans to string,{}", string);
    log.info("trans to string,{}", string2);

  }
}
