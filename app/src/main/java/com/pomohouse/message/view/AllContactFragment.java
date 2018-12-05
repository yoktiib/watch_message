package com.pomohouse.message.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pomohouse.message.MainActivity;
import com.pomohouse.message.R;
import com.pomohouse.message.abstractclass.AbstractFragment;
import com.pomohouse.message.adapter.AllContactAdapter;
import com.pomohouse.message.WaffleApplication;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.db.RealmManage;
import com.pomohouse.message.db.SharePreferenceUser;
import com.pomohouse.message.interfaceclass.MessageReceiveListener;
import com.pomohouse.message.interfaceclass.OnItemClickListener;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.ContactDAO;
import com.pomohouse.message.model.ContactData;
import com.pomohouse.message.model.MessageNewData;
import com.pomohouse.message.model.MessagesData;
import com.pomohouse.message.provider.MessageEventProvider;
import com.pomohouse.message.receiver.MessageReceiver;
import com.pomohouse.message.tools.Tools;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscription;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class AllContactFragment extends AbstractFragment implements MessageReceiveListener {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_empty_room)
    TextView txtNoContact;
    @BindView(R.id.member_recycler_view)
    RecyclerView listChatRoom;

    private LinearLayoutManager layoutManager;
    private AllContactAdapter allContactAdapter;
    private Subscription subscription;
    private ContactDAO contactDAO;
    private MessageReceiver messageReceiver;
    private List<MessageNewData> messageNewDataList;
    private RealmManage realmManage;

    public static AllContactFragment newInstanc(ContactDAO c) {
        AllContactFragment fragment = new AllContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(Config.CONTRACT_PARAM, c);
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
        updateViewAllContact(contactDAO);
        getNewMessage();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (subscription != null) {
            if (!subscription.isUnsubscribed())
                subscription.unsubscribe();
        }
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
        View view = inflater.inflate(R.layout.fragment_all_contact_list, container, false);
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
            contactDAO = getArguments().getParcelable(Config.CONTRACT_PARAM);
//            AbstractLog.e("ContactDAO From Content Provider", new GsonBuilder()
//                    .setPrettyPrinting().create().toJson(contactDAO));
        }

        realmManage = new RealmManage(getContext(), addDataListener);

        allContactAdapter = new AllContactAdapter(context);
        allContactAdapter.setOnItemClickListener(onItemClickListener);
    }

    private void initialView(View view) {
        ButterKnife.bind(this, view);

        Tools.setTypeface(context, txtTitle);
        Tools.setTypeface(context, txtNoContact);
        txtTitle.setText(getString(R.string.message));
        txtNoContact.setVisibility(View.GONE);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        listChatRoom.setHasFixedSize(true);
        listChatRoom.setLayoutManager(layoutManager);
        listChatRoom.setAdapter(allContactAdapter);
    }

    private void updateViewAllContact(ContactDAO c) {
        if (c == null || c.getContactData() == null || c.getContactData().size() == 0) {
            txtNoContact.setVisibility(View.VISIBLE);
        } else {
            allContactAdapter.setContactDao(c);
            allContactAdapter.notifyDataSetChanged();
        }
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(Object obj) {
            final ContactData contactData = (ContactData) obj;
            if (contactData.isHaveNewMsg != null && contactData.isHaveNewMsg.equals("T")) {
                final String contactId = contactData.getContactId();
                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<MessageNewData> result = realm.where(MessageNewData.class)
                                .equalTo("sender", contactId)
                                .findAll();

                        result.deleteAllFromRealm();
                    }
                });
            }
            //KillAppReceiver.getInstance().stopEventService(getContext());
            contactData.setChatRoomId(SharePreferenceUser.readInteger(context, SharePreferenceUser.CHATROOM_ID + contactData.contactId, -1));
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("contact_data", contactData);
            startActivity(i);
        }
    };

    private void registerReceiver() {
        messageReceiver = new MessageReceiver();
        messageReceiver.setMessageReceiveListener(this);
        context.registerReceiver(messageReceiver, new IntentFilter("com.pomohouse.event.EVENT_MESSAGE"));
    }

    private void unRegisterReceiver() {
        if (messageReceiver != null) {
            context.unregisterReceiver(messageReceiver);
        }
    }

    @Override
    public void receive(MessagesData data) {

    }

    private void getNewMessage() {
        try {
            new MessageEventProvider((AppCompatActivity) activity).onNewMessageProvider(new MessageEventProvider.OnLoadMessageEventListener() {
                @Override
                public void success(List<MessageNewData> data) {
                    AbstractLog.e("Get New Msg Success", "Size " + data.size());

                    messageNewDataList = data;
                    updateNewMessageView(messageNewDataList);
                }

                @Override
                public void error(Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    private void updateNewMessageView(List<MessageNewData> newMessageData) {
        //ลบ New Message จาก DB ลันเชอร์
        deleteNewMessageFromLauncher();

//        AbstractLog.e("List NewMessageData", new GsonBuilder()
//                .setPrettyPrinting().create().toJson(newMessageData));

        for (Iterator<MessageNewData> it = newMessageData.iterator(); it.hasNext(); ) {
            MessageNewData newMessageObj = it.next();
//            AbstractLog.e("NewMessageData", new GsonBuilder()
//                    .setPrettyPrinting().create().toJson(newMessageObj));

            MessagesData oldMessagesObjLast;
            try {
                oldMessagesObjLast = Realm.getDefaultInstance()
                        .where(MessagesData.class)
                        .equalTo("chatRoomId", newMessageObj.getChatRoomId())
                        .findAll()
                        .last();
            } catch (IndexOutOfBoundsException e) {
                oldMessagesObjLast = null;
            }

            if (oldMessagesObjLast != null) {
//                AbstractLog.e("ChatRoomID : LastMsg", oldMessagesObjLast.getChatRoomId() + " : " + oldMessagesObjLast.getMessageId());
                if (newMessageObj.getMessageId() <= oldMessagesObjLast.getMessageId()) {
                    it.remove();
                }
            }
        }
        realmManage.addNewMessageToDatabase(newMessageData);
    }

    RealmManage.RealmAddDataListener addDataListener = new RealmManage.RealmAddDataListener() {
        @Override
        public void onSuccess() {
            AbstractLog.e("Add to DB", "Success");
            setDataNewMessage();
        }

        @Override
        public void onError(Throwable error) {
            AbstractLog.e("Add to DB", "Error");
        }
    };

    private void setDataNewMessage() {
        if (contactDAO == null || contactDAO.getContactData() == null)
            return;

        try {
            for (int i = 0; i < contactDAO.getContactData().size(); i++) {
//                AbstractLog.e("Set Data ContactDAO", new GsonBuilder()
//                        .setPrettyPrinting().create().toJson(contactDAO.getContactData().get(i)));

                String contactId = contactDAO.getContactData().get(i).getContactId();
                RealmResults<MessageNewData> result = Realm.getDefaultInstance()
                        .where(MessageNewData.class)
                        .equalTo("sender", contactId)
                        .findAll();

                //จัดเรียงลำดับคนที่คุยล่าสุดไปบนสุด
                if (result.size() > 0) {
                    contactDAO.getContactData().get(i).setHaveNewMsg("T");
                    contactDAO.getContactData().get(i).setLastTimeStamp(result.last().getTimeStamp());
                } else {
                    contactDAO.getContactData().get(i).setHaveNewMsg("F");
                    String timeStamp = "";
                    try {
                        int chatRoomId = SharePreferenceUser.readInteger(context, SharePreferenceUser.CHATROOM_ID + contactId, -1);
                        if (chatRoomId != -1) {
                            timeStamp = Realm.getDefaultInstance()
                                    .where(MessagesData.class)
                                    .equalTo("chatRoomId", chatRoomId)
                                    .findAll()
                                    .last()
                                    .getTimeStamp();
                        } else {
                            timeStamp = Realm.getDefaultInstance()
                                    .where(MessagesData.class)
                                    .equalTo("sender", contactId)
                                    .findAll()
                                    .last()
                                    .getTimeStamp();
                        }
                        contactDAO.getContactData().get(i).setLastTimeStamp(timeStamp);
                    } catch (Exception e) {
                        contactDAO.getContactData().get(i).setLastTimeStamp(timeStamp);
                    }
                }
            }

            Collections.sort(contactDAO.getContactData(), new Comparator<ContactData>() {
                public int compare(ContactData o1, ContactData o2) {
                    return o2.getLastTimeStamp().compareTo(o1.getLastTimeStamp());
                }
            });

            //อัพเดทวิวใหม่
            updateViewAllContact(contactDAO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteNewMessageFromLauncher() {
        new MessageEventProvider((AppCompatActivity) activity).deleteNewMessage();
    }
}
