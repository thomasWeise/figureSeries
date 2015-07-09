package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** The document class */
public enum EDocumentClass {

  /** ieee tran */
  IEEE_TRAN("IEEEtran", 2), //$NON-NLS-1$

  /** article */
  ARTICLE("article", 1), //$NON-NLS-1$

  /** book */
  BOOK("book", 1); //$NON-NLS-1$

  /** The list of all document classes */
  public static final List<EDocumentClass> ALL = Collections
      .unmodifiableList(Arrays.asList(EDocumentClass.values()));

  /** the class name */
  private final String m_name;

  /** the columns */
  private final int m_cols;

  /**
   * create the document class
   *
   * @param name
   *          the name
   * @param cols
   *          the columns
   */
  EDocumentClass(final String name, final int cols) {
    this.m_name = name;
    this.m_cols = cols;
  }

  /**
   * Get the name of the document class
   *
   * @return the name of the document class
   */
  public final String getName() {
    return this.m_name;
  }

  /**
   * Get the number of columns
   *
   * @return the number of columns
   */
  public final int getColumnCount() {
    return this.m_cols;
  }
}
