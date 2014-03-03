package lisp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.imageio.ImageIO;

public abstract class Utilities {

	public static <T> ArrayList<T> cloneArrayList(Collection<T> arrayList) {
		return (ArrayList<T>)((ArrayList)arrayList).clone();
	}
	
	public static <T> HashSet<T> cloneHashSet(Collection<T> hashSet) {
		return (HashSet<T>)((HashSet<T>)hashSet).clone();
	}
	public static BufferedImage loadImage(URL url){
		BufferedImage image = null;
			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				
			}
		return image;
	}
}
