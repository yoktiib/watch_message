package com.pomohouse.message.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.pomohouse.message.R;
import com.pomohouse.message.abstractclass.AbstractFragment;
import com.pomohouse.message.adapter.MessageAdapter;
import com.pomohouse.message.WaffleApplication;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.controller.SendMessageController;
import com.pomohouse.message.db.RealmManage;
import com.pomohouse.message.db.SharePreferenceUser;
import com.pomohouse.message.dialog.MenuDialogFragment;
import com.pomohouse.message.global.ObjectDataInstance;
import com.pomohouse.message.http.HttpAPI;
import com.pomohouse.message.interfaceclass.StickerReceiveListener;
import com.pomohouse.message.interfaceclass.VoiceReceiveListener;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.ChatRoomDAO;
import com.pomohouse.message.model.ContactData;
import com.pomohouse.message.model.MessagesDAO;
import com.pomohouse.message.model.MessagesData;
import com.pomohouse.message.model.SendMessageDAO;
import com.pomohouse.message.tools.Tools;
import com.pomohouse.message.tools.Utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.internal.IOException;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MessageFragment extends AbstractFragment implements VoiceReceiveListener, StickerReceiveListener {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.item_view)
    RelativeLayout itemView;
    @BindView(R.id.msg_recycler_view)
    RecyclerView listMessage;
    @BindView(R.id.txt_title)
    TextView txtName;
    @BindView(R.id.iv_type_contact)
    ImageView ivTypeContact;
    @BindView(R.id.btn_menu_buttom)
    ImageButton btnMenuButton;

    @Inject
    HttpAPI service;

    private Subscription subscriptionMessage;
    private Subscription subscriptionInterval;
    private Observable<Long> observableInterval;
    private MessagesDAO messagesDAO;
    private ChatRoomDAO chatRoomDAO;
    private MessageAdapter messageAdapter;
    private ContactData contactData;
    private SendMessageController sendMessage;
    private int messageTimeInterval;
    private int chatRoomId;


    public static MessageFragment newInstanc(ContactData c) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putParcelable(Config.CONTRACT_PARAM, c);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
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
        if (contactData.getChatRoomId() != -1)
            initialUpdateViewMessage();

//        requestChatRoomData(contactData);
        notifyAdapter();
    }

    private void notifyAdapter() {
        if (messageAdapter != null) {
            messageAdapter.setOutPosition(-1);
            messageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        unSubscripMessage();
        unsubscriptionInterval();

        messageAdapter.stopPlayVoice();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscriptionInterval();
        messageAdapter.stopPlayVoice();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialView(view);
    }

    private void initial() {
        ((WaffleApplication) activity.getApplication()).getComponent().inject(this);
        if (getArguments() != null) {
            contactData = getArguments().getParcelable(Config.CONTRACT_PARAM);
            AbstractLog.e("RequestChatRoomData", "contactData : " + new GsonBuilder()
                    .setPrettyPrinting().create().toJson(contactData));
        }

        sendMessage = new SendMessageController(getContext(), sendMessageListener);
        messagesDAO = new MessagesDAO();
        messageAdapter = new MessageAdapter(context);
        messageTimeInterval = ObjectDataInstance.getInstance().getApiLoopTime();

        requestChatRoomData(contactData);
    }

    private void callApiLoop() {
        if (Utils.isLoadMessage) {
            subscriptionInterval = observableInterval.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Long count) {
                            // requestMessage วนลูปไปเรื่อยๆ
                            requestMessage(chatRoomDAO);
                        }
                    });
        }
    }

    private void initialView(View view) {
        ButterKnife.bind(this, view);

        //Set title name
        txtName.setText(contactData.getName());
        Tools.setTypeface(context, txtName);

        if (contactData.getContactType().equals(Config.TypeRelation.FAMILY)) {
            ivTypeContact.setImageResource(R.drawable.contact_type_family);
        } else {
            ivTypeContact.setImageResource(R.drawable.contact_type_bff);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        listMessage.setLayoutManager(layoutManager);
        listMessage.setItemAnimator(new DefaultItemAnimator());
        listMessage.setAdapter(messageAdapter);
    }

    private void requestChatRoomData(ContactData m) {
        if (m == null)
            return;

        String myImei = ObjectDataInstance.getInstance().getMyImei();
        String memberId = m.getContactId();

//        AbstractLog.w("RequestChatRoomData Param", "memberId " + memberId + "\n" + "imei " + myImei);

        Observable<Response<ChatRoomDAO>> observableChatRoom = service.getChatRoomList(myImei, memberId);
        subscriptionMessage = observableChatRoom.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ChatRoomDAO>>() {
                    @Override
                    public void onCompleted() {
                        AbstractLog.e("network", "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AbstractLog.e("network", "error " + e);
                    }

                    @Override
                    public void onNext(Response<ChatRoomDAO> response) {
                        AbstractLog.e("RequestChatRoomData", "response " + response.code());
                        if (response.isSuccessful()) {
                            AbstractLog.e("RequestChatRoomData", "response : " + new GsonBuilder()
                                    .setPrettyPrinting().create().toJson(response.body()));
                            chatRoomDAO = response.body();
                            messageTimeInterval = chatRoomDAO.getData().getConfig().getMessageTime();
                            observableInterval = Observable.interval(messageTimeInterval, TimeUnit.SECONDS);
                            saveChatRoomDataToDataBase(chatRoomDAO);
                            initialUpdateViewMessage();
                            requestMessage(response.body());
                            callApiLoop();
                        }
                    }
                });
    }

    private void saveChatRoomDataToDataBase(ChatRoomDAO chatRoomDAO) {
        if (chatRoomDAO == null)
            return;

        try {
            int chatRoomId = chatRoomDAO.getData().getChatRoomId();
            String memberId = chatRoomDAO.getData().getChatRoomMember().get(1).getMember();
            SharePreferenceUser.writeInteger(context, SharePreferenceUser.CHATROOM_ID + memberId, chatRoomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestMessage(ChatRoomDAO chatRoomDAO) {
        if (chatRoomDAO != null) {
            String myImei = ObjectDataInstance.getInstance().getMyImei();
            int msgLastId = getLastMessageID(); // เอา message id ล่าสุดส่งไป

            requestMessageList(msgLastId, chatRoomDAO.getData().getChatRoomId(), myImei);
        }
    }

    private void requestMessageList(int msgLastId, int roomId, String myImei) {
//        AbstractLog.w("RequestMessage Param",
//                "msgLastId " + msgLastId + "\n" +
//                        "roomId " + roomId + "\n" +
//                        "myImei " + myImei);

        Observable<Response<MessagesDAO>> observableMessage = service.getMessageList(msgLastId, roomId, myImei);
        subscriptionMessage = observableMessage.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<MessagesDAO>>() {
                    @Override
                    public void call(Response<MessagesDAO> response) {
                        AbstractLog.e("RequestMessage", "response code " + response.code());
                        AbstractLog.e("RequestMessage", "response " + new GsonBuilder()
                                .setPrettyPrinting().create().toJson(response.body()));
                        if (response.isSuccessful()) {
                            saveMessageToDatabase(response.body());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AbstractLog.e(TAG, "error " + throwable);
                    }
                });
    }

    private void updateViewMessage(MessagesDAO dao) {
        messageAdapter.setMessagesDAO(dao);
        messageAdapter.notifyDataSetChanged();

        //set List View Scroll To Buttom
        setRecyclerViewScrollToButtom(dao.getData().size() - 1);

        AbstractLog.w(TAG + " Message List Size", dao.getData().size() + "");
    }

    private void setRecyclerViewScrollToButtom(int position) {
        if (position < 0)
            return;

        listMessage.scrollToPosition(position);
    }

    private void saveMessageToDatabase(MessagesDAO dao) {
        RealmManage manage = new RealmManage(context, new RealmManage.RealmAddDataListener() {
            @Override
            public void onSuccess() {
                initialUpdateViewMessage();
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        manage.addMessageToDatabase(initialMessagesDAO(dao));
    }

    private void initialUpdateViewMessage() {
        if (chatRoomDAO == null || chatRoomDAO.getData() == null) {
            chatRoomId = contactData.getChatRoomId();
        } else {
            chatRoomId = chatRoomDAO.getData().getChatRoomId();
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<MessagesData> result = realm.where(MessagesData.class)
                .equalTo("chatRoomId", chatRoomId)
                .findAllAsync();
        result.addChangeListener(new RealmChangeListener<RealmResults<MessagesData>>() {
            @Override
            public void onChange(RealmResults<MessagesData> result) {
                messagesDAO.getData().clear();
                messagesDAO.getData().addAll(result);
                updateViewMessage(messagesDAO);
            }
        });
    }

    private int getLastMessageID() {
        int lastMessageID = 0;
        try {
            lastMessageID = Realm.getDefaultInstance().where(MessagesData.class)
                    .equalTo("chatRoomId", chatRoomDAO.getData().getChatRoomId())
                    .findAll()
                    .last()
                    .getMessageId();
            return lastMessageID;
        } catch (IOException e) {
            return lastMessageID;
        } catch (Exception e) {
            return lastMessageID;
        }
    }

    @OnClick(R.id.btn_back)
    void onBack() {
        activity.onBackPressed();
    }


    @OnClick(R.id.btn_menu_buttom)
    void onOpenMenuButtom() {
        openMenuButtom();
    }

    //เอา Data มาเซ็ทใหม่ เพื่อที่จะเอาไปแสดงใน List View
    private MessagesDAO initialMessagesDAO(MessagesDAO dao) {
        String myImei = ObjectDataInstance.getInstance().getMyImei();
        for (int i = 0; i < dao.getData().size(); i++) {
            MessagesData m = dao.getData().get(i);
            if (m.getSender().equals(myImei)) {
                //set contactType msg is watch
                if (m.getMessageType() == 0) {
                    m.setMsgTypeForView(Config.TYPE_MY_STICKER);
                } else if (m.getMessageType() == 1) {
                    m.setMsgTypeForView(Config.TYPE_MY_VOICE);
                }
            } else {
                //set contactType msg is mobile app
                if (m.getMessageType() == 0) {
                    m.setMsgTypeForView(Config.TYPE_FRIEND_STICKER);
                } else if (m.getMessageType() == 1) {
                    m.setMsgTypeForView(Config.TYPE_FRIEND_VOICE);
                } else if (m.getMessageType() == 2) {
                    m.setMsgTypeForView(Config.TYPE_FRIEND_TEXT);
                }
            }
        }
        return dao;
    }

    private void unsubscriptionInterval() {
        if (subscriptionInterval != null) {
            if (!subscriptionInterval.isUnsubscribed())
                subscriptionInterval.unsubscribe();
        }
    }

//    private void deleteTempFile() {
//        //ลบไฟล์เสียงที่ Temp ก่อนส่งออกจาก Device
//        if (recordFile != null)
//            if (recordFile.exists())
//                recordFile.delete();
//    }

    private void unSubscripMessage() {
        if (subscriptionMessage != null && !subscriptionMessage.isUnsubscribed()) {
            subscriptionMessage.unsubscribe();
        }
    }

    SendMessageController.SendMessageListener sendMessageListener = new SendMessageController.SendMessageListener() {
        @Override
        public void onSuccess(SendMessageDAO data) {
            initialUpdateViewMessage();
        }

        @Override
        public void onError(Throwable error) {
            initialUpdateViewMessage();
        }
    };

    private void openMenuButtom() {
        MenuDialogFragment dialogFragment = MenuDialogFragment.newInstanc(this, this);
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.show(getFragmentManager(), null);
    }

    @Override
    public void voiceReceive(String tempFileName, File recordFile) {
        try {
            sendMessage.sendVoiceFileToServer(ObjectDataInstance.getInstance().getMyImei(),
                    String.valueOf(chatRoomDAO.getData().getChatRoomId()), recordFile, tempFileName,
                    Config.TypeMessage.VOICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stickerReceive(String stickerId) {
        try {
            String myImei = ObjectDataInstance.getInstance().getMyImei();
            sendMessage.sendMessagesToServer(myImei,
                    String.valueOf(chatRoomDAO.getData().getChatRoomId()),
                    "",
                    stickerId,
                    Config.TypeMessage.STICKER,
                    0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
