package org.cwinteractive.infinitune;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.cwinteractive.infinitune.models.SongSearchResult;

@Path("songs")
public class InfinituneApiResource {

	@Inject
	private QueryVectorizer queryVectorizer;

	@Inject
	private AstraConnector astraConnector;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public SongSearchResult findSongs(@QueryParam("q") String query) {
		if ((query == null) || query.trim().isEmpty())  {
			query = "love";
		}

		float[] queryVec = queryVectorizer.getQueryVector(query);
		SongSearchResult result = astraConnector.findSongs(queryVec);
		return result;
	}
}