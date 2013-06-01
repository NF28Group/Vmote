package fr.nf28.vmote.play.classes;

public class SubtitleList {
	private String[] list;
 
	public SubtitleList(int s) {
		setList(new String[s]);
	}

	public String[] getList() {
		return list;
	}

	public void setList(String[] list) {
		this.list = list;
	}
}
