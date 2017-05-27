package com.example.kabhishek1.musicv1.jmusixmatch.entity.error;

import com.example.kabhishek1.musicv1.jmusixmatch.entity.Header;
import com.google.gson.annotations.SerializedName;

public class ErrorMessageContainer {
	@SerializedName("body")
	private String body;

	@SerializedName("header")
	private Header header;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}
}
