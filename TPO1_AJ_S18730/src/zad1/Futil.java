package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardOpenOption.*;

public class Futil {
    public static void processDir(String dirName, String resultFileName)  {

        try {
            FileChannel fc = FileChannel.open(Paths.get(resultFileName), CREATE, WRITE, TRUNCATE_EXISTING);
            Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                    ByteBuffer buffer = ByteBuffer.allocate(4096);
                    FileChannel fout = FileChannel.open(path, READ);
                    fout.read(buffer);
                    buffer.flip();
                    CharBuffer in = Charset.forName("CP1250").decode(buffer);
                    buffer = Charset.forName("UTF-8").encode(in);
                    fc.write(buffer);
                    fout.close();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path path, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
