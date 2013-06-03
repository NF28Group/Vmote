package fr.nf28.vmote.play.classes;

public class Media {

	private String Name;
	private String State;
	private String Volume;
	
	//details
	private String duree;
	private String largeurTrame;
	private String hauteurtrame;
	private String frameRate;
	private String date;
	private String artist;
	private String history;
	private String album;
	private String gender;
	
	//subtitles
	private SubtitleList subtitleList;
	private SubtitleList audioList;
	private String audioEcart;
	private String subtitleEcart;
	
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

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public String getLargeurTrame() {
		return largeurTrame;
	}

	public void setLargeurTrame(String largeurTrame) {
		this.largeurTrame = largeurTrame;
	}

	public String getHauteurtrame() {
		return hauteurtrame;
	}

	public void setHauteurtrame(String hauteurtrame) {
		this.hauteurtrame = hauteurtrame;
	}

	public String getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(String frameRate) {
		this.frameRate = frameRate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public SubtitleList getSubtitleList() {
		return subtitleList;
	}

	public void setSubtitleList(SubtitleList subtitleList) {
		this.subtitleList = subtitleList;
	}

	public SubtitleList getAudioList() {
		return audioList;
	}

	public void setAudioList(SubtitleList audioList) {
		this.audioList = audioList;
	}

	public String getAudioEcart() {
		return audioEcart;
	}

	public void setAudioEcart(String audioEcart) {
		this.audioEcart = audioEcart;
	}

	public String getSubtitleEcart() {
		return subtitleEcart;
	}

	public void setSubtitleEcart(String subtitleEcart) {
		this.subtitleEcart = subtitleEcart;
	}
}
