package com.example.kabhishek1.musicv1.jmusixmatch;

import android.util.Log;

import com.example.kabhishek1.musicv1.jmusixmatch.config.Constants;
import com.example.kabhishek1.musicv1.jmusixmatch.config.Methods;
import com.example.kabhishek1.musicv1.jmusixmatch.config.StatusCode;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.error.ErrorMessage;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.lyrics.Lyrics;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.lyrics.get.LyricsGetMessage;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.track.Track;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.track.TrackData;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.track.get.TrackGetMessage;
import com.example.kabhishek1.musicv1.jmusixmatch.entity.track.search.TrackSeachMessage;
import com.example.kabhishek1.musicv1.jmusixmatch.http.MusixMatchRequest;
import com.example.kabhishek1.musicv1.jmusixmatch.snippet.Snippet;
import com.example.kabhishek1.musicv1.jmusixmatch.snippet.get.SnippetGetMessage;
import com.example.kabhishek1.musicv1.jmusixmatch.subtitle.Subtitle;
import com.example.kabhishek1.musicv1.jmusixmatch.subtitle.get.SubtitleGetMessage;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static android.content.ContentValues.TAG;

public class MusixMatch {

	public String TAG = "check";

	/**
	 * A musiXmatch API Key.
	 */
	private final String apiKey;
	                                      
	/**
	 * MusixMatch Constructor with API-Key.
	 * 
	 * @param apiKey
	 *            A musiXmatch API Key.
	 */
	public MusixMatch(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * Get Lyrics for the specific trackID.
	 * 
	 * @param trackID
	 * @return
	 * @throws MusixMatchException
	 */
	public Lyrics getLyrics(int trackID) throws MusixMatchException {
		Lyrics lyrics = null;
		LyricsGetMessage message = null;
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(Constants.API_KEY, apiKey);
		params.put(Constants.TRACK_ID, new String("" + trackID));

		String response = null;

		response = MusixMatchRequest.sendRequest(Helper.getURLString(
				Methods.TRACK_LYRICS_GET, params));

		Gson gson = new Gson();

		try {
			message = gson.fromJson(response, LyricsGetMessage.class);

		} catch (JsonParseException jpe) {
			handleErrorResponse(response);
		}

		lyrics = message.getContainer().getBody().getLyrics();

		return lyrics;
	}

    /**
     * Get Snippet for the specified trackID.
     * @param trackID
	 * @return
	 * @throws MusixMatchException
     */

    public Snippet getSnippet(int trackID) throws MusixMatchException {
        Snippet snippet = null;
        SnippetGetMessage message = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, new String("" + trackID));

        String response = null;

        response = MusixMatchRequest.sendRequest(Helper.getURLString(
				Methods.TRACK_SNIPPET_GET, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, SnippetGetMessage.class);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }

        snippet = message.getContainer().getBody().getSnippet();

        return snippet;
    }



    /**
     * Get Subtitle for the specific trackID.
     *
     * @param trackID
     * @return
     * @throws MusixMatchException
     */
    public Subtitle getSubtitle(int trackID) throws MusixMatchException {


        Subtitle subtitle = null;
        SubtitleGetMessage message = null;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(Constants.API_KEY, apiKey);
        params.put(Constants.TRACK_ID, new String("" + trackID));

        String response = null;

        response = MusixMatchRequest.sendRequest(Helper.getURLString(
				Methods.TRACK_SUBTITLE_GET, params));

        Gson gson = new Gson();

        try {
            message = gson.fromJson(response, SubtitleGetMessage.class);
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
            handleErrorResponse(response);
        }

        subtitle = message.getContainer().getBody().getSubtitle();

        return subtitle;
    }

	/**
	 * Search tracks using the given criteria.
	 * 
	 * @param q
	 *            search into every available field
	 *            (track_name,artist_name,lyrics)
	 * @param q_artist
	 *            search for text string among artist names
	 * @param q_track
	 *            search for text string among track names
	 * @param page
	 *            request specific result page
	 * @param pageSize
	 *            specify number of items per result page
	 * @param f_has_lyrics
	 *            specify number of items per result page
	 * @return a list of tracks.
	 * @throws MusixMatchException
	 *             if any error occur
	 */
	public List<Track> searchTracks(String q, String q_artist, String q_track,
			int page, int pageSize, boolean f_has_lyrics)
			throws MusixMatchException {
		List<Track> trackList = null;
		TrackSeachMessage message = null;
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(Constants.API_KEY, apiKey);
		params.put(Constants.QUERY, q);
		params.put(Constants.QUERY_ARTIST, q_artist);
		params.put(Constants.QUERY_TRACK, q_track);
		params.put(Constants.PAGE, page);
		params.put(Constants.PAGE_SIZE, pageSize);

		if (f_has_lyrics) {
			params.put(Constants.F_HAS_LYRICS, "1");
		} else {
			params.put(Constants.F_HAS_LYRICS, "0");
		}

		String response = null;

		response = MusixMatchRequest.sendRequest(Helper.getURLString(
				Methods.TRACK_SEARCH, params));

		Gson gson = new Gson();

		try {
			message = gson.fromJson(response, TrackSeachMessage.class);
		} catch (JsonParseException jpe) {
			handleErrorResponse(response);
		}

		int statusCode = message.getTrackMessage().getHeader().getStatusCode();

		if (statusCode > 200) {
			throw new MusixMatchException("Status Code is not 200");
		}

		trackList = message.getTrackMessage().getBody().getTrack_list();

		return trackList;
	}

	/**
	 * Get the track details using the specified trackId.
	 * 
	 * @param trackID
	 *            track identifier in musiXmatch catalog
	 * @return the track
	 * @throws MusixMatchException
	 */
	public Track getTrack(int trackID) throws MusixMatchException {
		Track track = new Track();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(Constants.API_KEY, apiKey);
		params.put(Constants.TRACK_ID, new String("" + trackID));

		track = getTrackResponse(Methods.TRACK_GET, params);

		return track;
	}

	/**
	 * Get the most matching track which was retrieved using the search.
	 * 
	 * @param q_track
	 *            search for text string among track names
	 * @param q_artist
	 *            search for text string among artist names
	 * @return the track
	 * @throws MusixMatchException
	 */
	public Track getMatchingTrack(String q_track, String q_artist) throws MusixMatchException {

		Log.d(TAG,"beg");
		Track track = new Track();
		Map<String, Object> params = new HashMap<>();

		Log.d(TAG,q_artist+q_track+apiKey);

		params.put(Constants.API_KEY, apiKey);
		params.put(Constants.QUERY_TRACK, q_track);
		params.put(Constants.QUERY_ARTIST, q_artist);

		track = getTrackResponse(Methods.MATCHER_TRACK_GET, params);

          System.out.println("check"+track);


		Log.d(TAG,"end");

		return track;
	}

	/**
	 * Returns the track response which was returned through the query.
	 * 
	 * @param methodName
	 *            the name of the API method.
	 * @param params
	 *            a map which contains the key-value pair
	 * @return the track details.
	 * @throws MusixMatchException
	 *             if any error occurs.
	 */
	private Track getTrackResponse(String methodName, Map<String, Object> params)
			throws MusixMatchException {
		Track track = new Track();
		String response = null;
		TrackGetMessage message = null;

		response = MusixMatchRequest.sendRequest(Helper.getURLString(
				methodName, params));

		Gson gson = new Gson();

		try {
			message = gson.fromJson(response, TrackGetMessage.class);
		} catch (JsonParseException jpe) {
			handleErrorResponse(response);
		}

		TrackData data = message.getTrackMessage().getBody().getTrack();

		track.setTrack(data);

		return track;
	}

	/**
	 * Handle the error response.
	 * 
	 * @param jsonResponse
	 *            the jsonContent.
	 * @throws MusixMatchException
	 *             if any error occurs
	 */
	private void handleErrorResponse(String jsonResponse)
			throws MusixMatchException {
		StatusCode statusCode;
		Gson gson = new Gson();

		System.out.println(jsonResponse);

		ErrorMessage errMessage = gson.fromJson(jsonResponse,
				ErrorMessage.class);
		int responseCode = errMessage.getMessageContainer().getHeader()
				.getStatusCode();

		switch (responseCode) {
		case 400:
			statusCode = StatusCode.BAD_SYNTAX;
			break;
		case 401:
			statusCode = StatusCode.AUTH_FAILED;
			break;
		case 402:
			statusCode = StatusCode.LIMIT_REACHED;
			break;
		case 403:
			statusCode = StatusCode.NOT_AUTHORIZED;
			break;
		case 404:
			statusCode = StatusCode.RESOURCE_NOT_FOUND;
			break;
		case 405:
			statusCode = StatusCode.METHOD_NOT_FOUND;
			break;
		default:
			statusCode = StatusCode.ERROR;
			break;
		}

		throw new MusixMatchException(statusCode.getStatusMessage());
	}
}
