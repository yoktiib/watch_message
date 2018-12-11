package com.pomohouse.message.controller;


import com.pomohouse.message.R;

import java.util.HashMap;

/**
 * Created by mac on 12/5/2016 AD.
 */

public class AvatarMap {

    private HashMap<String, Integer> avatarMap;

    public AvatarMap() {
        avatarMap = new HashMap<>();
        avatarMap.put("0", R.drawable.avatar_1);
        avatarMap.put("1", R.drawable.avatar_2);
        avatarMap.put("2", R.drawable.avatar_3);
        avatarMap.put("3", R.drawable.avatar_4);
        avatarMap.put("4", R.drawable.avatar_5);
        avatarMap.put("5", R.drawable.avatar_6);
        avatarMap.put("6", R.drawable.avatar_7);
        avatarMap.put("7", R.drawable.avatar_8);
    }

    public int getAvatarIcon(String id) {
        if (avatarMap.containsKey(id))
            return avatarMap.get(id);

        return R.drawable.placeholder_avatar;
    }
}
