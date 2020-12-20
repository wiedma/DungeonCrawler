package engine.io;

public class Logger {
	
	public static void log(String log) {
		System.out.println(log);
	}
	
	public static void warn(String msg) {
		System.err.println("[WARNGING] " + msg);
	}
	
	public static void warn(String... strings) {
		System.err.print("[WARNING] ");		
		for(String str : strings) {
			if(str != strings[0])
				System.err.print("           ");
			
			System.err.print(str);			
			
//			if(str != strings[strings.length-1])
				System.err.println();
		}
	}
	
	
	public static void notify(String... strings) {
		System.out.print("[NOTIFICATION] ");		
		for(String str : strings) {
			if(str != strings[0])
				System.out.print("           ");
			
			System.out.print(str);			
			
//			if(str != strings[strings.length-1])
				System.out.println();
		}
	}	
}
