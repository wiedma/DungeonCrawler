package engine.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import engine.Scene;
import engine.gameobjects.GameObject;
/**
 * This class provides basic utilities to save Scenes to a file and load them from the file
 * @author Marco, Daniel
 *
 */
public class FileLoader {
	
	//TODO MaKe the I/O-System adapt to changes in class definition
	
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
//	public static boolean writeToFile(Scene s, String filePath) {
//		File file = new File(filePath);
//		if(file.exists()) {
//			file.delete();
//		}
//		try {
//			file.createNewFile();
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//			for(GameObject obj : s.getGameObjects()) {
//				bw.write(obj.toString());
//				bw.newLine();
//			}
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
	
	public static boolean writeToFile(Scene s, String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(s);
			out.close();
		} catch (Exception e) {
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
//	public static Scene readFromFile(String filePath) {
//		Scene scene = new Scene();
//		scene.setInitialFilePath(filePath);
//		File file = new File(filePath);
//		if(!file.exists()) {
//			System.err.println("The file with the following path '" + filePath + "' could not be found.");
//			return null;
//		}
//		String line = "";
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			GameObject gameObject;
//			while((line = br.readLine()) != null) {
//				String[] args = line.split(";");
//				
//				
//				int argsNr = args.length > 3 ? Integer.parseInt(args[3]) : 0;
//				
//				Class<?>[] types = new Class[argsNr];
//				Object[] values = new Object[argsNr];
//				for(int i = 4; i < args.length; i+=2) {
//					types[(i-4)/2] = toPrimitive(Class.forName(args[i]));
//					values[(i-4)/2] = Class.forName(args[i]).getConstructor(java.lang.String.class).newInstance(args[i+1]);
//				}
//				gameObject = (GameObject)Class.forName(args[0]).getConstructor(types).newInstance(values);
//				gameObject.setX(Double.parseDouble(args[1]));
//				gameObject.setY(Double.parseDouble(args[2]));
//				scene.addGameObject(gameObject);
//			}
//			br.close();
//		}catch(ArrayIndexOutOfBoundsException er) {
//			System.err.println("The number of parameters specified in the line \n'" + line + "'\n"
//					+ "from the file '" + filePath + "'\n"
//					+ "does not match the actual number of parameters!");
//		}catch(NoSuchMethodException e) {
//			System.err.println("The type of the parameters specified in the line \n'" + line + "'\n"
//					+ "does not match any constructor of the class.\n"
//					+ "Please create a matching constructor or override the toString() method until the parameters match any constructor!");
//		}catch(NumberFormatException e) {
//			System.err.println("The contents of the line \n'" + line + "'\n"
//					+ "in the scene file that was being tried to load threw a NumberFormatException while trying to convert a String to a Number\n"
//					+ "please check if the .toString() method in the specified class outputs a correct string\n"
//					+ "Details:");
//			e.printStackTrace();
//		}catch(FileNotFoundException e) {
//			System.err.println("The file at '" + filePath + "' could not be read");
//			e.printStackTrace();
//			return null;
//		}catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		return scene;
//	}
	
	public static Scene readFromFile(String filePath) {
		File file = new File(filePath);
		if(!file.exists()) {
			System.err.println("The file with the following path '" + filePath + "' could not be found.");
			return null;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fis);
			Scene s = (Scene) in.readObject();
			in.close();
			return s;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Converts any given Wrapperclass to its primitive type.
	 * @param clazz The Wrapperclass which is to be converted.
	 * @return <p>The primitve type corresponding to the given Wrapperclass.</p><p>If the argument is not a Wrapperclass, it is returned unaltered</p>
	 */
//	private static Class<?> toPrimitive(Class<?> clazz) {
//	    if (clazz.isPrimitive())
//	        return clazz;
//
//	    if (clazz == Integer.class)
//	        return Integer.TYPE;
//	    if (clazz == Long.class)
//	        return Long.TYPE;
//	    if (clazz == Boolean.class)
//	        return Boolean.TYPE;
//	    if (clazz == Byte.class)
//	        return Byte.TYPE;
//	    if (clazz == Character.class)
//	        return Character.TYPE;
//	    if (clazz == Float.class)
//	        return Float.TYPE;
//	    if (clazz == Double.class)
//	        return Double.TYPE;
//	    if (clazz == Short.class)
//	        return Short.TYPE;
//	    if (clazz == Void.class)
//	        return Void.TYPE;
//
//	    return clazz;
//	}
}
