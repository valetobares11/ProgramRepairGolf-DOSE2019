package unrc.dose;

/** This is just an example class. */
public class Belly {
  private static final int capacity = 40;
  /** keeps the value of the current level of elements into Belly. */
  private int current;

  public void eat(int cukes) {
    current = cukes;
  }

  public boolean isFull() {
    return capacity <= current;
  }

}
