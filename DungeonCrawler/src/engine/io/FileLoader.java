package engine.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import engine.Scene;
import engine.gameobjects.GameObject;
/**
 * This class provides basic utilities to save Scenes to a file and load them from the file
 * @author Marco, Daniel
 *
 */
public class FileLoader {
	
	/**
	 * This contains the first bit of the path to all {@link Scene} files. The filename is the only thing which needs to be added to this String.
	 */
	public static final String PATH_TO_FILES = "res/scenes/";
	
	/**
	 * Writes all the Objects in a given {@link Scene} to a file.
	 * @param s The {@link Scene} which is to be written.
	 * @param filePath The targeted file.
	 * @return indicates if all the I/O-Operations were successfull
	 */
	public static boolean writeToFile(Scene s, String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(GameObject obj : s.getGameObjects()) {
				bw.write(obj.toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
		
	
	/**
	 * Reads all the {@link GameObject objects} in a given file and adds them to a {@link Scene}.
	 * @param filePath The path of the file which is to be read
	 * @return the created {@link Scene} which contains all of the {@link GameObject objects} specified in the file
	 * @exception ArrayIndexOutOfBoundsException This Exception is thrown when the specified number of parameters in the file does not match the actual number.
	 * @exception NoSuchMethodException This Exception is thrown when there is no constructor specified for the given parameters.
	 */
	public static Scene readFromFile(String filePath) {
		Scene scene = new Scene();
		File file = new File(filePath);
		if(!file.exists()) {
			System.err.println("The file with the following path '" + filePath + "' could not be found.");
			return null;
		}
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {
				String[] args = line.split(";");
				int argsNr = Integer.parseInt(args[1]);
				Class<?>[] types = new Class[argsNr];
				Object[] values = new Object[argsNr];
				for(int i = 2; i < args.length; i+=2) {
					types[(i-2)/2] = toPrimitive(Class.forName(args[i]));
					values[(i-2)/2] = Class.forName(args[i]).getConstructor(java.lang.String.class).newInstance(args[i+1]);
				}
				scene.addGameObject((GameObject)Class.forName(args[0]).getConstructor(types).newInstance(values));
			}
			br.close();
		}catch(ArrayIndexOutOfBoundsException er) {
			System.err.println("The number of parameters specified in the line \n'" + line + "'\n"
					+ "from the file '" + filePath + "'\n"
					+ "does not match the actual number of parameters!");
		}catch(NoSuchMethodException e) {
			System.err.println("The type of the parameters specified in the line \n'" + line + "'\n"
					+ "does not match any constructor of the class.\n"
					+ "Please create a matching constructor or override the toString() method until the parameters match any constructor!");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return scene;
	}
	
	/**
	 * Converts any given Wrapperclass to its primitive type.
	 * @param clazz The Wrapperclass which is to be converted.
	 * @return <p>The primitve type corresponding to the given Wrapperclass.</p><p>If the argument is not a Wrapperclass, it is returned unaltered</p>
	 */
	private static Class<?> toPrimitive(Class<?> clazz) {
	    if (clazz.isPrimitive())
	        return clazz;

	    if (clazz == Integer.class)
	        return Integer.TYPE;
	    if (clazz == Long.class)
	        return Long.TYPE;
	    if (clazz == Boolean.class)
	        return Boolean.TYPE;
	    if (clazz == Byte.class)
	        return Byte.TYPE;
	    if (clazz == Character.class)
	        return Character.TYPE;
	    if (clazz == Float.class)
	        return Float.TYPE;
	    if (clazz == Double.class)
	        return Double.TYPE;
	    if (clazz == Short.class)
	        return Short.TYPE;
	    if (clazz == Void.class)
	        return Void.TYPE;

	    return clazz;
	}
}