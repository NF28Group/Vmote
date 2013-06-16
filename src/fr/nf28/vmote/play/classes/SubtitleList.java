package fr.nf28.vmote.play.classes;

import java.util.ArrayList;

public class SubtitleList {
	private ArrayList<Subtitle> list;
	private int current;
 
	public SubtitleList() {
		current = 0;
		setList(new ArrayList<Subtitle>());
	}

	public ArrayList<Subtitle> getList() {
		return list;
	}

	public void setList(ArrayList<Subtitle> list) {
		this.list = list;
	}

	public void add(Subtitle currSub) {
		this.list.add(currSub);
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
}
