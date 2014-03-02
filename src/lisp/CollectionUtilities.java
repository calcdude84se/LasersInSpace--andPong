package lisp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public abstract class CollectionUtilities {

	public static <T> ArrayList<T> cloneArrayList(Collection<T> arrayList) {
		return (ArrayList<T>)((ArrayList)arrayList).clone();
	}
	
	public static <T> HashSet<T> cloneHashSet(Collection<T> hashSet) {
		return (HashSet<T>)((HashSet<T>)hashSet).clone();
	}
}
