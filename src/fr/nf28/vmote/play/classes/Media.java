package fr.nf28.vmote.play.classes;

public class Media {

	private String Name;
	private String State;
	private String Volume;
	
	public Media(){
		
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = manageName(name);
	}
	
	private String manageName(String n){
		String final_name = n;
		final_name = final_name.replace("\"", ""); // enlever les quotes

		int i = final_name.lastIndexOf('.');
		if (i > 0) {
			final_name = final_name.substring(0,i); // enlever l'extension
		}
		return final_name;
	}
	
	@Override
	public String toString() {
		return "call function toString LaunchError Message = "+this.Name +" Etat = "+ this.State +" Volume = "+ this.Volume;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		this.State = state;
	}

	public int getVolume() {
		return (int) (Integer.parseInt(Volume)/(2.56*2));
	}

	public void setVolume(String volume) {
		Volume = volume;
	}
}
