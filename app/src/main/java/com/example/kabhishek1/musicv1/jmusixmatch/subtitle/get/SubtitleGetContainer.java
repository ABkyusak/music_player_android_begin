package com.example.kabhishek1.musicv1.jmusixmatch.subtitle.get;

import com.example.kabhishek1.musicv1.jmusixmatch.entity.Header;
import com.google.gson.annotations.SerializedName;

public class SubtitleGetContainer {

    @SerializedName("body")
    private SubtitleGetBody body;

    @SerializedName("header")
    private Header header;

    public SubtitleGetBody getBody() {
        return body;
    }

    public void setBody(SubtitleGetBody body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
