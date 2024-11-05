package org.cwinteractive.infinitune;

import com.datastax.astra.client.Collection;
import com.datastax.astra.client.DataAPIClient;
import com.datastax.astra.client.Database;
import com.datastax.astra.client.model.Document;
import com.datastax.astra.client.model.FindIterable;
import com.datastax.astra.client.model.FindOptions;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.cwinteractive.infinitune.models.Song;
import org.cwinteractive.infinitune.models.SongSearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class AstraConnector {

    private static Logger LOG = Logger.getLogger(AstraConnector.class.getName());

    public static String ASTRA_API_KEY_NAME = "ASTRA_API_KEY";
    public static String ASTRA_DB_URL_KEY_NAME = "ASTRA_DB_URL";

    private String dbUrl;

    private String astraApiKey;

    private DataAPIClient client;

    private Database songsDb;
    private Collection<Document> songsCollection;

    @PostConstruct
    public void initialize() {
        LOG.log(Level.INFO, "Initializing Astra connector ..");

        if(System.getenv().containsKey(ASTRA_API_KEY_NAME)) {
            astraApiKey = System.getenv().get(ASTRA_API_KEY_NAME);
        }

        if(System.getenv().containsKey(ASTRA_DB_URL_KEY_NAME)) {
            dbUrl = System.getenv().get(ASTRA_DB_URL_KEY_NAME);
        }

        client = new DataAPIClient(astraApiKey);
        songsDb = client.getDatabase(dbUrl);
        songsCollection = songsDb.getCollection("songs7");

        try {
            FindIterable<Document> docs = songsCollection.find(new FindOptions().limit(1));
            LOG.info("Printing docs ..");
            docs.forEach(System.out::println);
        } catch (Exception ex) {
            LOG.severe("Error occurred while reading songs collection: " + ex.getMessage());
        }
    }

    public SongSearchResult findSongs(float[] queryVec) {
        FindIterable<Document> documents = songsCollection.find(queryVec, 10);
        List<Song> matchingSongs = new ArrayList<>();
        documents.forEach(doc -> {
            Song song = new Song();
            song.setDocId(doc.get("_id").toString());
            song.setMsID(doc.get("msID").toString());
            song.setMxmID(doc.get("mxmID").toString());
            song.setArtist(doc.get("artist").toString());
            song.setTitle(doc.get("title").toString());
            song.setSongText(doc.get("songText").toString());
            matchingSongs.add(song);
        });

        return new SongSearchResult(matchingSongs.toArray(new Song[matchingSongs.size()]));
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getApiKey() {
        return astraApiKey;
    }

}
