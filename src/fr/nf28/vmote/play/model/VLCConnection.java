package fr.nf28.vmote.play.model;

public class VLCConnection {

	private VLCConnection() {	
	}
	
	private static class ConnectionHolder {
		private final static VLCConnection instance = new VLCConnection();
	}
	
	public static VLCConnection getInstance() {
		return ConnectionHolder.instance;
	}
}
