package lisp;

import java.util.ArrayList;

public abstract class CollectionUtilities {

	public static <T> ArrayList<T> cloneArrayList(ArrayList<T> arrayList) {
		return (ArrayList<T>)arrayList.clone();
	}
}
