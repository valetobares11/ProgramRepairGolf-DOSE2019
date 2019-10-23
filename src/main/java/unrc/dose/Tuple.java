package unrc.dose;

/**
 * Class Tuple generic.
 * @param <A> accept any type A.
 * @param <B> accept any type B.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
*/
public class Tuple<A, B> {
	/**
	 * First object of tuple.
	 */
	private A first;
	/**
	 * Second object of tuple.
	 */
	private B second;

	/**
	 * Constructor of the Tuple class.
	 * @param a first object to be set in the tuple.
	 * @param b second object to be set in the tuple.
	 */
	public Tuple(final A a, final B b) {
		first = a;
		second = b;
	}

	/**
	 * This method returns the first object of the tuple.
	 * @return first object of the tuple.
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * This method returns the second object of the tuple.
	 * @return second object of the tuple.
	 */
	public B getSecond() {
		return second;
	}

	/**
	 * hashCode() redefined.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + first.hashCode();
		result = prime * result + second.hashCode();
		return result;
	}

	/**
	 * equals() redefined.
	 * @param obj tuple to compare.
	 * @return true if two tuples are equal.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		} else {
			Tuple<A, B> t = (Tuple<A, B>) obj;
			return (this.first.equals(t.first)
			&& this.second.equals(t.second));
		}
	}

}
