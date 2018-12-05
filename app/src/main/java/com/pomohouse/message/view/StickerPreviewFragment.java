package com.pomohouse.message.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pomohouse.message.R;
import com.pomohouse.message.abstractclass.AbstractFragment;
import com.pomohouse.message.WaffleApplication;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.controller.SendMessageController;
import com.pomohouse.message.global.ObjectDataInstance;
import com.pomohouse.message.model.ChatRoomData;
import com.pomohouse.message.model.StickerData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class StickerPreviewFragment extends AbstractFragment {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.iv_sticker_preview)
    ImageView ivStickerPreview;
    @BindView(R.id.btn_back)
    ImageButton btnBack;

    private SendMessageController sendMessage;
    private ChatRoomData chatRoomData;
    private StickerData stickerData;

    public static StickerPreviewFragment newInstanc(ChatRoomData chatRoomData, StickerData stickerData) {
        StickerPreviewFragment fragment = new StickerPreviewFragment();
        Bundle args = new Bundle();
        args.putParcelable(Config.CHAT_ROOM_PARAM, chatRoomData);
        args.putParcelable(Config.STICKER_ID_PARAM, stickerData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initial();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sticker_preview, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView(view);
    }

    private void initial() {
        ((WaffleApplication) activity.getApplication()).getComponent().inject(this);
        if (getArguments() != null) {
            chatRoomData = getArguments().getParcelable(Config.CHAT_ROOM_PARAM);
            stickerData = getArguments().getParcelable(Config.STICKER_ID_PARAM);
        }
        sendMessage = new SendMessageController(context);
    }

    private void initialView(View view) {
        ButterKnife.bind(this, view);

        ivStickerPreview.setImageResource(stickerData.getStickerImg());
    }

    @OnClick(R.id.iv_sticker_preview)
    void onSend() {
        send(chatRoomData, stickerData.getId());

        //กดส่ง sticker แล้ว back กลับเลย ไม่ต้องรอให้ส่งเสร็จ
        //Ref เหมือน Line
        for (int i = 0; i < 2; i++) {
            onBack();
        }
    }

    @OnClick(R.id.btn_back)
    void onBack() {
        activity.onBackPressed();
    }

    private void send(ChatRoomData chatRoomData, Integer obj) {
        String myImei = ObjectDataInstance.getInstance().getMyImei();
        sendMessage.sendMessagesToServer(myImei,
                String.valueOf(chatRoomData.getChatRoomId()),
                "",
                obj.toString(),
                Config.TypeMessage.STICKER,
                0);
    }
}
