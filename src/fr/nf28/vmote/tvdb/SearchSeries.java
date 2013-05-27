package fr.nf28.vmote.tvdb;

public class SearchSeries {
	private final long seriesId; 
	private final String seriesName;
	private final String overview;
	
	public SearchSeries(long seriesId, String seriesName, String overview) {
		super();
		this.seriesId = seriesId;
		this.seriesName = seriesName;
		this.overview = overview;
	}

	public long getSeriesId() {
		return seriesId;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public String getOverview() {
		return overview;
	}
}
