package com.pomohouse.message.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pomohouse.message.R;
import com.pomohouse.message.abstractclass.AbstractFragment;
import com.pomohouse.message.adapter.StickerListAdapter;
import com.pomohouse.message.WaffleApplication;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.controller.SendMessageController;
import com.pomohouse.message.controller.StickerMap;
import com.pomohouse.message.global.ObjectDataInstance;
import com.pomohouse.message.http.HttpAPI;
import com.pomohouse.message.interfaceclass.OnItemTouchListener;
import com.pomohouse.message.model.ChatRoomDAO;
import com.pomohouse.message.model.ChatRoomData;
import com.pomohouse.message.model.StickerData;
import com.pomohouse.message.tools.Tools;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class StickerListFragment extends AbstractFragment {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.sticker_recycler_view)
    RecyclerView listSticker;
    @BindView(R.id.iv_type_contact)
    ImageView btnPoint;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    @Inject
    HttpAPI service;

    private Observable<Response<ChatRoomDAO>> observable;
    private StickerListAdapter stickerListAdapter;
    private ChatRoomData chatRoomData;
    private SendMessageController sendMessage;

    public static StickerListFragment newInstanc(ChatRoomData chatRoomData) {
        StickerListFragment fragment = new StickerListFragment();
        Bundle args = new Bundle();
        args.putParcelable(Config.STICKER_PARAM, chatRoomData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initial();
    }

    @Override
    public void onStop() {
        super.onStop();
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
        View view = inflater.inflate(R.layout.fragment_sticker_list, container, false);
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
            chatRoomData = getArguments().getParcelable(Config.STICKER_PARAM);
        }
        sendMessage = new SendMessageController(context);
        stickerListAdapter = new StickerListAdapter(context);
    }

    private void initialView(View view) {
        ButterKnife.bind(this, view);

        Tools.setTypeface(context, txtTitle);
        txtTitle.setText(getString(R.string.sticker));

        btnPoint.setVisibility(View.INVISIBLE);

        stickerListAdapter.setStickerData(StickerMap.getStickerList());
        stickerListAdapter.setOnItemClickListener(onItemTouchListener);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        listSticker.setLayoutManager(layoutManager);
        listSticker.setItemAnimator(new DefaultItemAnimator());
        listSticker.setLayoutManager(new GridLayoutManager(context, 2));
        listSticker.setAdapter(stickerListAdapter);
    }

    OnItemTouchListener onItemTouchListener = new OnItemTouchListener() {

        @Override
        public void onSingleTap(Object obj) {
            StickerData data = (StickerData) obj;
            Fragment fragment = StickerPreviewFragment.newInstanc(chatRoomData, data);
            changeFragmentReplace(fragment, true);
        }

        @Override
        public void onDoubleTap(Object obj) {
            StickerData data = (StickerData) obj;
            send(chatRoomData, data.getId());

            //กดส่ง sticker แล้ว back กลับเลย ไม่ต้องรอให้ส่งเสร็จ
            //Ref เหมือน Line
            for (int i = 0; i < 1; i++) {
                onBack();
            }
        }
    };

    private void send(ChatRoomData chatRoomData, int id) {
        String myImei = ObjectDataInstance.getInstance().getMyImei();
        if (chatRoomData != null && chatRoomData.getChatRoomId() != 0) {
            sendMessage.sendMessagesToServer(myImei,
                    String.valueOf(chatRoomData.getChatRoomId()),
                    "",
                    String.valueOf(id),
                    Config.TypeMessage.STICKER,
                    0);
        }
    }

    @OnClick(R.id.root)
    void doNoting() {
        //Do Noting
    }

    @OnClick(R.id.btn_back)
    void onBack() {
        activity.onBackPressed();
    }
}
