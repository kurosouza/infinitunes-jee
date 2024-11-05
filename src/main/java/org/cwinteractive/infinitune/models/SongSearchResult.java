package org.cwinteractive.infinitune.models;

public class SongSearchResult {
    private final Song[] songs;

    public SongSearchResult(Song[] songs) {
        this.songs = songs;
    }

    public Song[] getSongs() {
        return songs;
    }

}
