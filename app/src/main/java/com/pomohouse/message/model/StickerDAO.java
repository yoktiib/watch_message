package com.pomohouse.message.model;

import java.util.List;

/**
 * Created by SITTIPONG on 23/9/2559.
 */

public class StickerDAO {

    public List<StickerData> getStickerData() {
        return stickerData;
    }

    public void setStickerData(List<StickerData> stickerData) {
        this.stickerData = stickerData;
    }

    private List<StickerData> stickerData;
}
