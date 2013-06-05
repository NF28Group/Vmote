/** Pas urgent **/
package fr.nf28.vmote.history.model;

import fr.nf28.vmote.history.view.HistoryVideoFragment;
import fr.nf28.vmote.history.view.HistoryAudioFragment;
import fr.nf28.vmote.play.model.VLCConnection;

public class HistoryModel {
	
	private VLCConnection vlcConnection;
	private HistoryAudioFragment historyMusicView;
	private HistoryVideoFragment historyVideoView;
	
	private HistoryModel() {
		setVlcConnection(VLCConnection.getInstance());
	}
	
	private static class HistoryModelHolder {
		private final static HistoryModel instance = new HistoryModel();
	}
	
	public static HistoryModel getInstance() {
		return HistoryModelHolder.instance;
	}

	public VLCConnection getVlcConnection() {
		return vlcConnection;
	}

	public void setVlcConnection(VLCConnection vlcConnection) {
		this.vlcConnection = vlcConnection;
	}

	public HistoryAudioFragment getHistoryMusicView() {
		return historyMusicView;
	}

	public void setHistoryMusicView(HistoryAudioFragment historyMusicView) {
		this.historyMusicView = historyMusicView;
	}

	public HistoryVideoFragment getHistoryVideoView() {
		return historyVideoView;
	}

	public void setHistoryVideoView(HistoryVideoFragment historyVideoView) {
		this.historyVideoView = historyVideoView;
	}
}
