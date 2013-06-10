package fr.nf28.vmote.play.classes;

public class Subtitle {

	private String text;
	private int id;
	
	public Subtitle() {
		text = "";
		id = -1;
	}
	
	public Subtitle(String t, int i) {
		text = t;
		id = i;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
