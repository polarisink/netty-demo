package github.polarisink.nettty.c3;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {
  public static void main(String[] args) {
    //1、创建事件循环组
    NioEventLoopGroup group = new NioEventLoopGroup(2);//io 时间 普通任务 定时任务
    //DefaultEventLoop defaultEventLoop = new DefaultEventLoop();
    //System.out.println(NettyRuntime.availableProcessors());
    //2、获取下一个事件循环对象
    System.out.println(group.next());

    //3、执行普通任务
    /*group.next().submit(()->{
      try {
        Thread.sleep(1000);
      }catch (InterruptedException e){
        e.printStackTrace();
      }
      log.debug("ok");
    });
    log.debug("main");*/


    //4、定时任务
    group.next().scheduleAtFixedRate(()->log.debug("ok"),0,1, TimeUnit.SECONDS);
    log.debug("main");
  }
}
