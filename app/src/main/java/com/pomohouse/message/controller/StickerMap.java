package com.pomohouse.message.controller;

import com.pomohouse.message.R;
import com.pomohouse.message.model.StickerDAO;
import com.pomohouse.message.model.StickerData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 9/23/2016 AD.
 */

public class StickerMap {

    public static int getStickerImg(String id) {
//        AbstractLog.w("StickerId", id);
        if(id.startsWith(":") && id.endsWith(":")){
            id = id.replaceAll(":", "");
        }

        try {
            HashMap<String, Integer> m = new HashMap<>();
            m.put("0", R.drawable.msg_st01);
            m.put("1", R.drawable.msg_st02);
            m.put("2", R.drawable.msg_st03);
            m.put("3", R.drawable.msg_st04);
            m.put("4", R.drawable.msg_st05);
            m.put("5", R.drawable.msg_st06);
            m.put("6", R.drawable.msg_st07);
            m.put("7", R.drawable.msg_st08);
            m.put("8", R.drawable.msg_st09);
            m.put("9", R.drawable.msg_st10);
            m.put("10", R.drawable.msg_st11);
            m.put("11", R.drawable.msg_st12);
            m.put("12", R.drawable.msg_st13);
            m.put("13", R.drawable.msg_st14);
            m.put("14", R.drawable.msg_st15);
            m.put("15", R.drawable.msg_st16);
            return m.get(id);
        }catch (NullPointerException e){
            e.printStackTrace();
            return R.drawable.msg_st01;
        }
    }

    public static StickerDAO getStickerList() {
        StickerDAO dao = new StickerDAO();
        List<StickerData> data = new ArrayList<StickerData>();
        StickerData d;
        for (int i = 0; i <= 21; i++) {
            d = new StickerData();
            d.setId(i);
            d.setStickerImg(getStickerImg(i+""));
            data.add(d);
        }
        dao.setStickerData(data);
        return dao;
    }
}
