package unrc.dose;

public class Belly {
  private static final int capacity = 40;
  private int current;

  public void eat(int cukes) {
    current = cukes;
  }

  public boolean isFull() {
    return capacity <= current;
  }

}
