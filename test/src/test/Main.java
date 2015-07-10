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
public final class Main implements Runnable {

  /** the output path */
  private Path m_out;
  /** the first file */
  private Path m_file1;
  /** the second file */
  private Path m_file2;
  /** the shortest example */
  private long m_shortest;

  /**
   * The main routine
   *
   * @param args
   *          one string: the output folder
   * @throws IOException
   *           if i/o fails
   */
  public static void main(final String[] args) throws IOException {
    final Main job;

    System.out.println(//
        "This test program tries to find documents using the figureSeries package that do not compile.");//$NON-NLS-1$
    System.out.println(//
        "The reason is that we have notorious 'Float(s) lost.' errors in version 0.9.2 and 0.9.3.");//$NON-NLS-1$
    System.out.println(//
        "In order to get confidence in a bug fix for this, I made this program.");//$NON-NLS-1$
    System.out.println(//
        "If the program cannot find documents which cause errors anymore, the fix should be OK.");//$NON-NLS-1$

    System.out.println(//
        "Usage: java test.Main outputFolder.");//$NON-NLS-1$
    System.out.println(//
        "The program will then try to put successively smaller documents generating errors into the folder outputFolder.");//$NON-NLS-1$

    job = new Main();
    if ((args != null) && (args.length > 0)) {
      job.m_out = Paths.get(args[0]);
    } else {
      job.m_out = Paths.get(System.getProperty("java.io.tmpdir")); //$NON-NLS-1$
    }
    job.m_out = job.m_out.normalize();
    LaTeXCompiler.delete(job.m_out);
    Files.createDirectories(job.m_out);

    job.m_file1 = job.m_out.resolve("firstExample.tex"); //$NON-NLS-1$
    job.m_file2 = job.m_out.resolve("secondExample.tex"); //$NON-NLS-1$
    try {
      Files.delete(job.m_file1);
    } catch (final NoSuchFileException ignore) { // ignore
    }
    try {
      Files.delete(job.m_file2);
    } catch (final NoSuchFileException ignore) { // ignore
    }

    System.out.println("Printing files to " + job.m_out); //$NON-NLS-1$
    job.m_shortest = Long.MAX_VALUE;

    for (int i = Math.max(1, Runtime.getRuntime().availableProcessors()); (--i) >= 0;) {
      new Thread(job).start();
    }
  }

  /** run! */
  @Override
  public final void run() {
    final Random rand;
    LaTeXDocument doc;
    long current;

    rand = new Random();
    for (;;) {
      doc = null;
      doc = LaTeXDocument.makeRandomDocument(rand);
      if (!(LaTeXCompiler.compile(doc))) {
        current = doc.size();
        synchronized (this) {
          try {
            if (this.m_shortest > current) {
              if (this.m_shortest < Long.MAX_VALUE) {
                try {
                  Files.delete(this.m_file2);
                } catch (final NoSuchFileException ignore) { // ignore
                }
                Files.copy(this.m_file1, this.m_file2);
                try {
                  Files.delete(this.m_file1);
                } catch (final NoSuchFileException ignore) { // ignore
                }
              } else {
                LaTeXCompiler.loadFigureSeries(this.m_out);
              }
              doc.writeTo(this.m_file1);
              doc = null;
              this.m_shortest = current;
              System.out.println(//
                  "New shortest non-compiling example found.");//$NON-NLS-1$
            }
          } catch (final Throwable error) {
            error.printStackTrace();
          }
        }
      }
    }
  }
}
