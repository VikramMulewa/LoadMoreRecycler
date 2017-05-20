package com.example.vikram.loadmorerecycler.Model;

/**
 * Created by Vikram on 08-May-17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("final_id")
    @Expose
    private Integer finalId;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getFinalId() {
        return finalId;
    }

    public void setFinalId(Integer finalId) {
        this.finalId = finalId;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

}