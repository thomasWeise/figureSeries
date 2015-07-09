package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * This is the main test class. With this class, we are looking for
 * situations where {@code figureSeries} fails.
 */
public final class Main {

  /**
   * The main routine
   *
   * @param args
   *          one string: the output folder
   * @throws IOException
   *           if i/o fails
   */
  public static void main(final String[] args) throws IOException {
    final Random rand;
    Path out, file1, file2;
    LaTeXDocument doc;
    int shortest, current;

    System.out.println(//
        "This test program tries to find documents using the figureSeries package that do not compile.");//$NON-NLS-1$
    System.out.println(//
        "The reason is that we have notorious 'Float(s) lost!' errors in version 0.9.2 and 0.9.3.");//$NON-NLS-1$
    System.out.println(//
        "In order to get confidence in a bug fix for this, I made this program.");//$NON-NLS-1$
    System.out.println(//
        "If the program cannot find documents which cause errors anymore, the fix should be OK.");//$NON-NLS-1$

    System.out.println(//
        "Usage: java test.Main outputFolder.");//$NON-NLS-1$
    System.out.println(//
        "The program will then try to put successively smaller documents generating errors into the folder outputFolder.");//$NON-NLS-1$

    if ((args != null) && (args.length > 0)) {
      out = Paths.get(args[0]);
    } else {
      out = Paths.get(System.getProperty("java.io.tmpdir")); //$NON-NLS-1$
    }
    out = out.normalize();
    LaTeXCompiler.delete(out);
    Files.createDirectories(out);

    file1 = out.resolve("firstExample.tex"); //$NON-NLS-1$
    file2 = out.resolve("secondExample.tex"); //$NON-NLS-1$
    try {
      Files.delete(file1);
    } catch (final NoSuchFileException ignore) { // ignore
    }
    try {
      Files.delete(file2);
    } catch (final NoSuchFileException ignore) { // ignore
    }

    System.out.println("Printing files to " + out); //$NON-NLS-1$
    shortest = Integer.MAX_VALUE;

    rand = new Random();
    for (;;) {
      doc = LaTeXDocument.makeRandomDocument(rand);
      if (!(LaTeXCompiler.compile(doc))) {
        current = doc.size();
        if (shortest > current) {
          if (shortest < Integer.MAX_VALUE) {
            try {
              Files.delete(file2);
            } catch (final NoSuchFileException ignore) { // ignore
            }
            Files.copy(file1, file2);
            try {
              Files.delete(file1);
            } catch (final NoSuchFileException ignore) { // ignore
            }
          } else {
            LaTeXCompiler.loadFigureSeries(out);
          }
          doc.writeTo(file1);
          shortest = current;
          System.out.println("New shortest non-compiling example found.");//$NON-NLS-1$
        }

      }
    }
  }

}
