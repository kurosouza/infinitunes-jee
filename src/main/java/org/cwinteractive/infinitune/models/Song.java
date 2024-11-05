package org.cwinteractive.infinitune.models;

public class Song {
    private String docId;
    private String msID;
    private String mxmID;
    private String title;
    private String artist;
    private String songText;

    public Song() { }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getMsID() {
        return msID;
    }

    public void setMsID(String msID) {
        this.msID = msID;
    }

    public String getMxmID() {
        return mxmID;
    }

    public void setMxmID(String mxmID) {
        this.mxmID = mxmID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongText() {
        return songText;
    }

    public void setSongText(String songText) {
        this.songText = songText;
    }
}
