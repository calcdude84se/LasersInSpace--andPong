package lisp;

import java.util.ArrayList;
import java.util.Collection;

public abstract class CollectionUtilities {

	public static <T> ArrayList<T> cloneArrayList(Collection<T> arrayList) {
		return (ArrayList<T>)((ArrayList)arrayList).clone();
	}
}
