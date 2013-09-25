package fmi.uni.grading.server.update;

public class UpdateManager {

	
	/**
	 * Update the relevant classes using a custom classloader thus bypassing 
	 * the class caching inside the application classload	er
	 */
//	class Server {
//		private Servicelnterface service; // use an interface
//		public void updateService(String location) {
//		MyClassLoader cl = new MyClassLoader(location);
//		Class c = cl.loadClass(<service>);
//		service = (Servicelnterface)c.newlnstance();
//		}
//		public void processRequest (,..) {
//		service.run(...);
//
	
//	public void processRequest (...) { 
//		Class c = service.getClass(); 
//		Method m = c.getMethod(“run” , . ..). 
//		m.invoke(service, . ..). 
//		1 
//	
//	public boolean  restartApplication( Object classInJarFile )
//	{
//	    String javaBin = System.getProperty("java.home") + "/bin/java";
//	    File jarFile;
//	    try{
//	        jarFile = new File
//	        (classInJarFile.getClass().getProtectionDomain()
//	        .getCodeSource().getLocation().toURI());
//	    } catch(Exception e) {
//	        return false;
//	    }
//
//	    /* is it a jar file? */
//	    if ( !jarFile.getName().endsWith(".jar") )
//	    return false;   //no, it's a .class probably
//
//	    String  toExec[] = new String[] { javaBin, "-jar", jarFile.getPath() };
//	    try{
//	        Process p = Runtime.getRuntime().exec( toExec );
//	    } catch(Exception e) {
//	        e.printStackTrace();
//	        return false;
//	    }
//
//	    System.exit(0);
//
//	    return true;
//	}

}
