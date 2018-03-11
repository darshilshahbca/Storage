package com.example.x_men.storage.events;

import com.example.x_men.storage.model.DataItem;

public class DataItemEvent {

    private DataItem dataItem;

    public DataItemEvent(DataItem dataItem) {
        this.dataItem = dataItem;
    }

    public DataItem getDataItem() {
        return dataItem;
    }

    public void setDataItem(DataItem dataItem) {
        this.dataItem = dataItem;
    }
}
