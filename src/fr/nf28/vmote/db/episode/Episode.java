package fr.nf28.vmote.db.episode;

import java.util.Date;

import fr.nf28.vmote.lib.DateHelper;

public class Episode {
	// Notez que l'identifiant est un long
	private long id;
	private long tvShow_id;
	private int seasonNumber;
	private int episodeNumber;
	private String episodeName;
	private String overview;
	private Date firstAired; // Première date de diffusion
	private boolean seen;
	
	public Episode(long id, long tvShow_id, int seasonNumber, int episodeNumber, String episodeName, 
			String overview, Date firstAired, boolean seen) {
		super();
		this.id = id;
		this.tvShow_id = tvShow_id;
		this.seasonNumber = seasonNumber;
		this.episodeNumber = episodeNumber;
		this.episodeName = episodeName;
		this.overview = overview;
		this.firstAired = firstAired;
		this.seen = seen;
	}
	
	public Episode(long id, long tvShow_id, int seasonNumber, int episodeNumber, String episodeName, 
			String overview, String firstAired, boolean seen) {
		this(id, tvShow_id, seasonNumber, episodeNumber, episodeName, overview, DateHelper.stringToDate(firstAired), seen);
	}

	public long getId() {
		return id;
	}

	public long getTvShow_id() {
		return tvShow_id;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}
	
	public String getSeasonNumberString() {
		String seasonNumberString;
		if(seasonNumber < 10){
			seasonNumberString = "0" + seasonNumber;
		}
		else {
			seasonNumberString = String.valueOf(seasonNumber);
		}
		return seasonNumberString;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}
	
	public String getEpisodeNumberString() {
		String episodeNumberString;
		if(episodeNumber < 10){
			episodeNumberString = "0" + episodeNumber;
		}
		else {
			episodeNumberString = String.valueOf(episodeNumber);
		}
		return episodeNumberString;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public String getOverView() {
		return overview;
	}

	public Date getFirstAired() {
		return firstAired;
	}
	
	public String getFirstAiredString() {
		return DateHelper.dateToString(firstAired);
	}

	public boolean isSeen() {
		return seen;
	}
	
	public void setSeen(boolean isSeen) {
		this.seen = isSeen;
	}
}
