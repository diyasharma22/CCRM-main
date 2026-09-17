package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Recursive utilities (e.g., print total size of directory).
 */
public class RecursionUtils {

    public static long computeTotalSize(Path path) throws IOException {
        final long[] size = {0};

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });

        return size[0];
    }

    public static void printFilesByDepth(Path path, int maxDepth) throws IOException {
        Files.walk(path, maxDepth)
                .forEach(p -> System.out.println(p.toString()));
    }
}
