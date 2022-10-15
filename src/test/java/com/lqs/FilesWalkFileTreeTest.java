package com.lqs;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 遍历文件夹
 *
 * @author lqs
 * @date 2022/9/18
 */
public class FilesWalkFileTreeTest {
  public static void main(String[] args) throws IOException {
    walk();
  }

  private static void walk() throws IOException {
    Files.walkFileTree(Paths.get("/Users/lqs/Desktop/cloud-disk"), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) throws IOException {
        System.out.println("====>" + dir);
        return super.preVisitDirectory(dir, attr);
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
        System.out.println(file);
        return super.visitFile(file, attr);
      }

      public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
        System.out.println("<====推出" + dir);
        return super.postVisitDirectory(dir, e);
      }
    });
  }

  private static void walk2() throws IOException {
    AtomicInteger dirCount = new AtomicInteger();
    Files.walkFileTree(Paths.get("/Users/lqs/Desktop/cloud-disk"), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) throws IOException {
        dirCount.incrementAndGet();
        System.out.println("====>" + dir);
        return super.preVisitDirectory(dir, attr);
      }
    });
    System.out.println("dirCount: " + dirCount.get());
  }

  private static void walk3() throws IOException {
    AtomicInteger dirCount = new AtomicInteger();
    AtomicInteger fileCount = new AtomicInteger();
    Files.walkFileTree(Paths.get("/Users/lqs/Desktop/cloud-disk"), new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) throws IOException {
        dirCount.incrementAndGet();
        System.out.println("====>" + dir);
        return super.preVisitDirectory(dir, attr);
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
        System.out.println(file);
        fileCount.incrementAndGet();
        return super.visitFile(file, attr);
      }
    });
    System.out.println("dirCount: " + dirCount.get());
    System.out.println("fileCount: " + fileCount.get());
  }
}
