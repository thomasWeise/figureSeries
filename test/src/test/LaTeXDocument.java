package test;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

/**
 * A simple class to store a LaTeX document.
 */
public final class LaTeXDocument {

  /** the new line characters */
  private static final char[] NEWLINE;

  static {
    char[] chars;

    chars = null;
    try (final CharArrayWriter writer = new CharArrayWriter()) {
      try (final BufferedWriter bw = new BufferedWriter(writer)) {
        bw.append('}');
        bw.append('%');
        bw.newLine();
        bw.flush();
      }
      chars = writer.toCharArray();
    } catch (final Throwable t) {
      t.printStackTrace();
    }
    NEWLINE = chars;
  }

  /** the string builder */
  private StringBuilder m_text;

  /** the finalized data */
  private char[] m_data;

  /** the figure element counter */
  private int m_figureElement;

  /** the document class */
  private final EDocumentClass m_class;

  /** the current section depth */
  private int m_secDepth;

  /** do we have a figure series? */
  private boolean m_hasFigSer;

  /**
   * create the document
   *
   * @param clazz
   *          the document class
   */
  private LaTeXDocument(final EDocumentClass clazz) {
    super();

    char next;
    List<String> params;

    this.m_text = new StringBuilder();
    this.m_class = clazz;
    this.m_secDepth = (-1);

    this.__add("\\documentclass"); //$NON-NLS-1$

    params = clazz.getParams();
    if (!(params.isEmpty())) {
      next = '[';
      for (final String param : params) {
        this.__add(next);
        next = ',';
        this.__add(param);
      }
      this.__add(']');
    }

    this.__add('{');
    this.__add(clazz.getName());
    this.__endCommand();

    this.__add("\\RequirePackage{lipsum"); //$NON-NLS-1$
    this.__endCommand();

    this.__add("\\RequirePackage{graphicx"); //$NON-NLS-1$
    this.__endCommand();

    this.__add("\\RequirePackage{figureSeries"); //$NON-NLS-1$
    this.__endCommand();

    this.__add("\\begin{document"); //$NON-NLS-1$
    this.__endCommand();
  }

  /** Add the next figure char sequence */
  private final void __addNextFigureElement() {
    int code, i;

    code = this.m_figureElement;
    if (code >= Integer.MAX_VALUE) {
      this.m_figureElement = 0;
    } else {
      this.m_figureElement = (code + 1);
    }

    do {
      i = code % 61;
      code /= 61;
      if (i < 26) {
        this.__add((char) ('a' + i));
      } else {
        if (i < 52) {
          this.__add((char) (('A' + i) - 26));
        } else {
          this.__add((char) (('0' + i) - 52));
        }
      }
    } while (code != 0);
  }

  /**
   * Print the given text
   *
   * @param text
   *          the text
   */
  private final synchronized void __add(final String text) {
    this.m_text.append(text);
  }

  /**
   * Print the given char
   *
   * @param chr
   *          the char
   */
  private final synchronized void __add(final char chr) {
    this.m_text.append(chr);
  }

  /** Append a new line to the document */
  private final synchronized void __endLine() {
    this.m_text.append(LaTeXDocument.NEWLINE, 1,
        (LaTeXDocument.NEWLINE.length - 1));
  }

  /** End an open command */
  private final synchronized void __endCommand() {
    this.m_text.append(LaTeXDocument.NEWLINE);
  }

  /** end the document */
  private final void __endDocument() {
    final int size;
    this.__add("\\end{document"); //$NON-NLS-1$
    this.__endCommand();

    size = this.m_text.length();
    this.m_data = new char[size];
    this.m_text.getChars(0, size, this.m_data, 0);
    this.m_text = null;
  }

  /**
   * Get the size of the document
   *
   * @return the size of the document
   */
  public final long size() {
    int size, lines;
    lines = 0;
    size = this.m_data.length;
    for (final char ch : this.m_data) {
      if (ch == '\n') {
        lines++;
      }
    }
    return ((((long) lines) << 32L) | size);
  }

  /**
   * Write this document to the given path
   *
   * @param path
   *          the path
   * @throws IOException
   *           if i/o fails
   */
  public final void writeTo(final Path path) throws IOException {
    try (final BufferedWriter bw = Files.newBufferedWriter(path,
        Charset.defaultCharset())) {
      bw.write(this.m_data);
    }
  }

  /**
   * add an integer
   *
   * @param val
   *          the integer
   */
  private final void __add(final int val) {
    this.m_text.append(val);
  }

  /**
   * Let's add a lipsum
   *
   * @param rand
   *          the random number generator
   */
  private final void __addLipsum(final Random rand) {
    final int start;

    this.__add("\\lipsum"); //$NON-NLS-1$
    if (rand.nextBoolean()) {
      this.__add('*');
    }
    if (rand.nextBoolean()) {
      this.__add('[');
      start = (rand.nextInt(100) + 1);
      this.__add(start);
      if (rand.nextBoolean()) {
        this.__add('-');
        this.__add(start + 1 + rand.nextInt(100));
      }
      this.__add(']');
    }

    this.__endLine();
  }

  /**
   * add a percentage as float
   *
   * @param percent
   *          the percentage
   */
  private final void __addPercent(final int percent) {
    int i;
    if (percent >= 100) {
      this.__add('1');
    } else {
      this.__add('0');
      if (percent > 0) {
        this.__add('.');
        this.__add((char) ('0' + (percent / 10)));
        i = (percent % 10);
        if (i > 0) {
          this.__add((char) ('0' + i));
        }
      }
    }
  }

  /**
   * add a caption text
   *
   * @param rand
   *          the caption text
   */
  private final void __addCaption(final Random rand) {
    boolean capital, first;
    char base;

    capital = first = true;
    do {
      if (first) {
        first = false;
      } else {
        this.__add(' ');
      }

      base = (capital ? 'A' : 'a');
      do {
        this.__add((char) (base + rand.nextInt(26)));
        base = 'a';
      } while (rand.nextInt(4) > 0);

      capital = rand.nextBoolean();
    } while (rand.nextInt(4) > 0);
    this.__add('.');
  }

  /**
   * Let's add a figure
   *
   * @param rand
   *          the random number generator
   */
  private final void __addFigure(final Random rand) {
    final boolean multicol, star;
    final int dims;

    multicol = (this.m_class.getColumnCount() > 1);
    star = (multicol ? rand.nextBoolean() : false);

    this.__add("\\begin{figure"); //$NON-NLS-1$
    if (star) {
      this.__add('*');
    }
    this.__endCommand();

    this.__add("\\begin{center"); //$NON-NLS-1$
    this.__endCommand();

    this.__add("\\resizebox{"); //$NON-NLS-1$
    dims = (1 + rand.nextInt(3));
    if ((dims & 1) == 0) {
      this.__add('!');
    } else {
      this.__addPercent(11 + rand.nextInt(90));
      if (multicol && (!(star))) {
        this.__add("\\columnwidth"); //$NON-NLS-1$
      } else {
        this.__add("\\linewidth"); //$NON-NLS-1$
      }
    }
    this.__add('}');
    this.__add('{');
    if ((dims & 2) == 0) {
      this.__add('!');
    } else {
      this.__addPercent(21 + rand.nextInt(80));
      this.__add("\\textheight"); //$NON-NLS-1$
    }
    this.__add('}');
    this.__add('{');
    this.__addNextFigureElement();
    this.__endCommand();

    this.__add("\\end{center"); //$NON-NLS-1$
    this.__endCommand();

    if (rand.nextInt(3) > 0) {
      this.__add("\\caption{"); //$NON-NLS-1$
      this.__addCaption(rand);
      this.__endCommand();
    }

    this.__add("\\end{figure"); //$NON-NLS-1$
    if (star) {
      this.__add('*');
    }
    this.__endCommand();
  }

  /**
   * Let's add a figure series
   *
   * @param rand
   *          the random number generator
   */
  private final void __addFigureSeries(final Random rand) {
    int height, width, m;

    this.m_hasFigSer = true;

    this.__add("\\figureSeries"); //$NON-NLS-1$
    if (rand.nextBoolean()) {
      this.__add("Float{"); //$NON-NLS-1$
    } else {
      this.__add("Here{"); //$NON-NLS-1$
    }

    this.__addCaption(rand);
    this.__add('}');
    this.__add('{');
    this.__endLine();

    do {
      m = (rand.nextInt(5) + 1);
      width = Math.max(1, Math.min(100,//
          ((int) (Math.round((90d + (10d * rand.nextDouble())) / m)))));
      if (rand.nextBoolean() || (width > 50)) {
        height = (21 + rand.nextInt(80));
      } else {
        height = (-1);
      }

      this.__add("\\figureSeriesRow{");//$NON-NLS-1$
      this.__endLine();

      for (; (--m) >= 0;) {
        this.__add("\\figureSeriesElement{");//$NON-NLS-1$
        this.__addCaption(rand);
        this.__add('}');
        this.__add('{');
        this.__endLine();

        this.__add("\\resizebox{"); //$NON-NLS-1$
        this.__addPercent(width);
        this.__add("\\linewidth"); //$NON-NLS-1$

        this.__add('}');
        this.__add('{');
        if (height <= 0) {
          this.__add('!');
        } else {
          this.__addPercent(height);
          this.__add("\\textheight"); //$NON-NLS-1$
        }

        this.__add('}');
        this.__add('{');
        this.__addNextFigureElement();
        this.__endCommand();

        this.__endCommand();
      }

      this.__endCommand();

    } while (rand.nextInt(5) > 0);

    this.__endCommand();
  }

  /**
   * make a title
   *
   * @param rand
   *          the random number generator
   */
  private final void __makeTitle(final Random rand) {
    this.__add("\\title{");//$NON-NLS-1$
    this.__addCaption(rand);
    this.__endCommand();
    if (rand.nextBoolean()) {
      this.__add("\\author{");//$NON-NLS-1$
      this.__addCaption(rand);
      this.__endCommand();
    }
    this.__add("\\maketitle");//$NON-NLS-1$
    this.__endLine();
  }

  /**
   * make a section
   *
   * @param rand
   *          the random number generator
   */
  private final void __addSection(final Random rand) {
    final int depth;

    this.m_secDepth = depth = Math.max(0,
        Math.min(2, rand.nextInt(this.m_secDepth + 2)));
    switch (depth) {
      case 0: {
        this.__add("\\section{"); //$NON-NLS-1$
        break;
      }
      case 1: {
        this.__add("\\subsection{");//$NON-NLS-1$
        break;
      }
      default: {
        this.__add("\\subsubsection{");//$NON-NLS-1$
        break;
      }
    }
    this.__addCaption(rand);
    this.__endCommand();
  }

  /**
   * Create a random LaTeX document
   *
   * @param rand
   *          the randomizer
   * @return the document
   */
  public static final LaTeXDocument makeRandomDocument(final Random rand) {
    final LaTeXDocument doc;

    doc = new LaTeXDocument(EDocumentClass.ALL.get(//
        rand.nextInt(EDocumentClass.ALL.size())));

    if (rand.nextBoolean()) {
      doc.__makeTitle(rand);
    }

    do {
      switch (rand.nextInt(4)) {
        case 0: {//
          doc.__addFigure(rand);
          break;
        }

        case 1: {
          doc.__addFigureSeries(rand);
          break;
        }

        case 2: {
          doc.__addSection(rand);
          break;
        }

        default: {
          doc.__addLipsum(rand);
          break;
        }
      }
    } while ((!(doc.m_hasFigSer)) || (rand.nextInt(3) > 0));

    doc.__endDocument();
    return doc;
  }
}
