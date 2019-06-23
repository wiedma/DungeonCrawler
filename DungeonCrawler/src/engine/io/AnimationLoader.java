package engine.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import engine.animation.Animation;

public class AnimationLoader {
	
	public static final String PATH_TO_ANIMATIONS = "res/animations/";
	
	private static HashMap <Class<?>, HashMap<String, Animation>> hashMap;
	
	/**
	 * Loads the {@link Animation} HashMap for a specific class
	 * @param requester the class which wants its fucking HashMap
	 * @return its fucking HashMap
	 */
	public static HashMap<String, Animation> loadAnimations(Class<?> requester){
		if(hashMap == null) {
			hashMap = new HashMap <Class<?>, HashMap<String, Animation>>();
		}
		if(!hashMap.containsKey(requester)) {
			return forceLoadAnimations(requester);
		}
		
		return hashMap.get(requester);
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Animation> forceLoadAnimations(Class<?> requester){
		if(hashMap == null) {
			hashMap = new HashMap <Class<?>, HashMap<String, Animation>>();
		}
		File file = new File(PATH_TO_ANIMATIONS + requester.getName().replaceAll("\\.", "/") + ".animations");
		if(!file.exists()) {
			System.err.println("The file with the following path '" + file.getPath() + "' could not be found.");
			return null;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fis);
			HashMap<String, Animation> animationMap = (HashMap<String, Animation>) in.readObject();
			in.close();
			hashMap.put(requester, animationMap);
			return animationMap;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void writeAnimations(Class<?> requester, HashMap<String, Animation> animationMap) {
		File file = new File(PATH_TO_ANIMATIONS + requester.getName().replaceAll("\\.", "/") + ".animations");
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();				
			} catch(IOException e) {System.err.println("The file '" + file.getPath() + "' could not be made!");}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(animationMap);
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
