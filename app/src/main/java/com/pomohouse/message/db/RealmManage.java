package com.pomohouse.message.db;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.ChatRoomTemp;
import com.pomohouse.message.model.MessageNewData;
import com.pomohouse.message.model.MessagesDAO;
import com.pomohouse.message.model.MessagesData;
import com.pomohouse.message.model.SendVoiceMessageModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;


/**
 * Created by SITTIPONG on 4/9/2559.
 */
public class RealmManage {

    private static final String TAG = "RealmManager";

    private Context context;
    private Realm realm;
    private RealmAddDataListener managerListener;
    private RealmDataChangeListener dataChangeListener;

    private RealmConfiguration realmConfiguration;

    public interface RealmAddDataListener {
        void onSuccess();

        void onError(Throwable error);
    }

    public interface RealmDataChangeListener {

    }

    public RealmManage(Context context) {
        this.context = context;
        startRealmManager();
    }

    public RealmManage(Context context, RealmAddDataListener managerListener) {
        this.context = context;
        this.managerListener = managerListener;

        startRealmManager();
    }

    public RealmManage(Context context, RealmDataChangeListener dataChangeListener) {
        this.context = context;
        this.dataChangeListener = dataChangeListener;

        startRealmManager();

        realm.addChangeListener(changeListener);
    }

    public RealmManage(Context context, RealmAddDataListener managerListener,
                       RealmDataChangeListener dataChangeListener) {
        this.context = context;
        this.managerListener = managerListener;
        this.dataChangeListener = dataChangeListener;

        startRealmManager();

        realm.addChangeListener(changeListener);
    }

    public void startRealmManager() {
        realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .build();

        realm = getRealm();
    }

    public void closeReamManager() {
        if (!realm.isClosed())
            realm.close();
    }

    public void addPathFileVoiceRecorde(final SendVoiceMessageModel svm) {
        if (!realm.isClosed()) {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(svm);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    if (managerListener != null)
                        managerListener.onSuccess();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    if (managerListener != null)
                        managerListener.onError(error);
                }
            });
        }
    }

    public void addMessageToDatabase(final MessagesDAO dao) {
        if (dao == null || dao.getData() == null || dao.getData().size() == 0)
            return;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (MessagesData d : dao.getData()) {
                    realm.copyToRealm(d);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (managerListener != null)
                    managerListener.onSuccess();

                AbstractLog.w(TAG + " Realm Add Msg", "Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (managerListener != null)
                    managerListener.onError(error);
            }
        });
    }

    public void addNewMessageToDatabase(final List<MessageNewData> messageNewData) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (MessageNewData d : messageNewData) {
                    realm.copyToRealm(d);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (managerListener != null)
                    managerListener.onSuccess();

                AbstractLog.w(TAG + " Realm Add Msg", "Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (managerListener != null)
                    managerListener.onError(error);
            }
        });
    }

    public void addMessageToDatabase(final MessagesData messagesData) {
        if (messagesData == null)
            return;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(messagesData);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (managerListener != null)
                    managerListener.onSuccess();

                AbstractLog.w(TAG + " Realm Add Msg", "Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (managerListener != null)
                    managerListener.onError(error);
            }
        });
    }

    public void addChatRoomDataToDataBase(final ChatRoomTemp chatRoomTemp) {
        if (chatRoomTemp == null)
            return;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(chatRoomTemp);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (managerListener != null)
                    managerListener.onSuccess();

                AbstractLog.w(TAG + " Realm Add ChatRoomTemp", "Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (managerListener != null)
                    managerListener.onError(error);

                AbstractLog.w(TAG + " Realm Add ChatRoomTemp", "Error");
            }
        });
    }

    RealmChangeListener<Realm> changeListener = new RealmChangeListener<Realm>() {
        @Override
        public void onChange(Realm element) {

        }
    };

    private Realm getRealm() {
        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
                //No Realm file to remove.
            }
        }
    }
}
