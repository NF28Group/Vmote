package fr.nf28.vmote.play.classes;

public class LaunchError {
	private int Etat;
	private String Message;

	public LaunchError(){
		
	} 
	
	public int getEtat() {
		return Etat;
	}

	public void setEtat(int etat) {
		Etat = etat;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
