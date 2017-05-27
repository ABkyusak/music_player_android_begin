package com.example.kabhishek1.musicv1.jmusixmatch.entity.track.search;

import com.example.kabhishek1.musicv1.jmusixmatch.entity.Header;
import com.google.gson.annotations.SerializedName;

public class TrackSearchContainer {

	@SerializedName("body")
	private TrackSearchBody body;

	@SerializedName("header")
	private Header header;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public TrackSearchBody getBody() {
		return body;
	}

	public void setBody(TrackSearchBody body) {
		this.body = body;
	}
}
