package fr.nf28.vmote.db.tvshow;

public class TvShow {
	// Notez que l'identifiant est un long
	private long id;
	private String name;
	private String posterUrl;
	private String posterPath;
	private String network; //Chaine de diffusion
	private String genre;
	private int runtime;
	private float rating;
	private String overview;

	public TvShow(long id, String name, String posterUrl, String posterPath, String network, String genre, int runtime, float rating, String overview) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.runtime = runtime;
		this.posterUrl = posterUrl;
		this.posterPath = posterPath;
		this.rating = rating;
		this.overview = overview;
		this.network = network;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getGenre() {
		return genre;
	}

	public int getRuntime() {
		return runtime;
	}
	
	public String getPosterPath() {
		return posterPath;
	}

	public String getPosterUrl() {
		return posterUrl;
	}
	
	public float getRating() {
		return rating;
	}
	
	public String getOverview() {
		return overview;
	}

	public String getNetwork() {
		return network;
	}
}
