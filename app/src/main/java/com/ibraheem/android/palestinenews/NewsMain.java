package com.ibraheem.android.palestinenews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsMain {
    @SerializedName("response")
    @Expose
    private NewsResponse response;

    public NewsResponse getResponse() {
        return response;
    }

    public void setResponse(NewsResponse response) {
        this.response = response;
    }
}
