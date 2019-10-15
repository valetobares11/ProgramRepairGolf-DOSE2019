/**
 * This is the main project package
 *
 * @author franco
 * @version 0.1
 */
package unrc.dose;

/** This is just an example class. */
public class Belly {
  /** Belly's capacity. */
  private static final int CAPACITY = 40;

  /** keeps the value of the current level of elements into Belly. */
  private int current;

  /** Belly identifier. */
  private int id;

  /** Belly's owner. */
  private String name;

  /** Constructor.
   * @param id Identifier
   * @param name slug
  */
  public Belly(final int id, final String name) {
    this.id = id;
    this.name = name;
  }

  /** Adds cukes into belly.
   * @param cukes int
   */
  public void eat(final int cukes) {
    current = cukes;
  }

  /** Checks if belly is full.
   * @return boolean
   */
  public boolean isFull() {
    return CAPACITY <= current;
  }
}
