package com.example.vikram.loadmorerecycler.Model;

/**
 * Created by Vikram on 08-May-17.
 */
import com.example.vikram.loadmorerecycler.Model.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
}
