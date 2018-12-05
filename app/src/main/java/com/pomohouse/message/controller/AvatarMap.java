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
        avatarMap.put("0", R.drawable.avatar_boy);
        avatarMap.put("1", R.drawable.avatar_girl);
        avatarMap.put("2", R.drawable.avatar_dad);
        avatarMap.put("3", R.drawable.avatar_mom);
        avatarMap.put("4", R.drawable.avatar_grand_dad);
        avatarMap.put("5", R.drawable.avatar_grand_mom);
        avatarMap.put("6", R.drawable.avatar_uncle);
        avatarMap.put("7", R.drawable.avatar_aunt);
        avatarMap.put("8", R.drawable.avatar_other01);
        avatarMap.put("9", R.drawable.avatar_other02);
        avatarMap.put("10", R.drawable.avatar_other03);
        avatarMap.put("11", R.drawable.avatar_other04);
        avatarMap.put("12", R.drawable.avatar_other05);
        avatarMap.put("13", R.drawable.avatar_other06);
    }

    public int getAvatarIcon(String id) {
        if (avatarMap.containsKey(id))
            return avatarMap.get(id);

        return R.drawable.placeholder_avatar;
    }
}
