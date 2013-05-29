package fr.nf28.vmote.play.media;

public class Media {

	private String name;
	
	public Media(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = manageName(name);
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
}
