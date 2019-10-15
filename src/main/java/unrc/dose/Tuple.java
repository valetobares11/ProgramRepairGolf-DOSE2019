package unrc.dose;

/**
 * Class Tuple generic.
 * @param <A> accept any type A.
 * @param <B> accept any type B.
 * @author Brusati Formento, Matias
*/
public class Tuple<A, B> {
	/**
	 * First object for tuple.
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

}
