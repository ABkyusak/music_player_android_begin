package com.example.kabhishek1.musicv1.jmusixmatch.snippet.get;

import com.example.kabhishek1.musicv1.jmusixmatch.entity.Header;
import com.google.gson.annotations.SerializedName;

public class SnippetGetContainer {

    @SerializedName("body")
    private SnippetGetBody body;

    @SerializedName("header")
    private Header header;

    public SnippetGetBody getBody() {
        return body;
    }

    public void setBody(SnippetGetBody body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
