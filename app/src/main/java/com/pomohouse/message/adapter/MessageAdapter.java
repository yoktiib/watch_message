package com.pomohouse.message.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pomohouse.message.R;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.controller.StickerMap;
import com.pomohouse.message.interfaceclass.OnItemClickListener;
import com.pomohouse.message.log.AbstractLog;
import com.pomohouse.message.model.MessagesDAO;
import com.pomohouse.message.model.MessagesData;
import com.pomohouse.message.tools.Tools;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MessagesDAO messagesDAO;
    private OnItemClickListener onItemClickListener;
    private int outPosition = -1;
    private MediaPlayer mediaPlayer;
    private TextView txtDurationPlaing;
    private ProgressBar progressBarVoice;
    private ProgressBar progressBarLoadVoice;

    public MessageAdapter(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == Config.TYPE_MY_VOICE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_my_message_voice, parent, false);
            return new MyVoiceViewHolder(view);
        } else if (viewType == Config.TYPE_MY_STICKER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_my_message_sticker, parent, false);
            return new MyStickerViewHolder(view);
        } else if (viewType == Config.TYPE_FRIEND_VOICE) {
//            view = LayoutInflater.from(context).inflate(R.layout.item_list_friend_message_voice, parent, false);
            view = LayoutInflater.from(context).inflate(R.layout.item_list_my_message_voice, parent, false);
//            return new FriendVoiceViewHolder(view);
            return new MyVoiceViewHolder(view);
        } else if (viewType == Config.TYPE_FRIEND_STICKER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_friend_message_sticker, parent, false);
            return new FriendStickerViewHolder(view);
        } else if (viewType == Config.TYPE_FRIEND_TEXT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_friend_message_text, parent, false);
            return new FriendTextViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MessagesData data = messagesDAO.getData().get(position);

        if (holder instanceof MyVoiceViewHolder) {
            final MyVoiceViewHolder viewHolder = (MyVoiceViewHolder) holder;

            viewHolder.txtVoiceDuration.setText("0:" + String.format("%02d", data.getVoiceLength()));
            viewHolder.progressBar.setMax(data.getVoiceLength());
            Tools.setTypeface(context, viewHolder.txtVoiceDuration);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.getRootView().getLayoutParams();
            if (data.getMsgTypeForView() == Config.TYPE_FRIEND_VOICE) {
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                viewHolder.getRootView().setLayoutParams(params);
            } else {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                viewHolder.getRootView().setLayoutParams(params);
            }

            if (outPosition != -1) {
                if (outPosition == position) {
                    viewHolder.ivMyVoice.setImageResource(R.drawable.stop_btn);
                    viewHolder.progressBarLoadVoice.setVisibility(View.VISIBLE);
                    viewHolder.txtVoiceDuration.setVisibility(View.GONE);
                    txtDurationPlaing = viewHolder.txtVoiceDuration;
                    viewHolder.progressBar.setProgress(data.getVoiceLength());
                    progressBarVoice = viewHolder.progressBar;
                    progressBarLoadVoice = viewHolder.progressBarLoadVoice;
                } else {
                    viewHolder.ivMyVoice.setImageResource(R.drawable.play_btn);
                    viewHolder.progressBarLoadVoice.setVisibility(View.GONE);
                    viewHolder.txtVoiceDuration.setVisibility(View.VISIBLE);
                    viewHolder.progressBar.setProgress(data.getVoiceLength());
                }
            } else {
                viewHolder.progressBar.setProgress(data.getVoiceLength());
                viewHolder.ivMyVoice.setImageResource(R.drawable.play_btn);
                viewHolder.progressBarLoadVoice.setVisibility(View.GONE);
                viewHolder.txtVoiceDuration.setVisibility(View.VISIBLE);
            }

            viewHolder.ivMyVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (updateVoiceTimeRunnable != null)
                        countDownHandler.removeCallbacks(updateVoiceTimeRunnable);

                    if (outPosition == position) {
                        outPosition = -1;
                        stopPlayVoice();
                    } else {
                        outPosition = position;
                        playVoice(data.getVoicePath().toString());
                    }
                    notifyDataSetChanged();
                }
            });

        } else if (holder instanceof MyStickerViewHolder) {
            MyStickerViewHolder viewHolder = (MyStickerViewHolder) holder;
            viewHolder.ivMySticker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickMessage(data);
                }
            });
            viewHolder.ivMySticker.setImageResource(StickerMap.getStickerImg(data.getStickerId()));

        } else if (holder instanceof FriendVoiceViewHolder) {

        } else if (holder instanceof FriendStickerViewHolder) {
            FriendStickerViewHolder viewHolder = (FriendStickerViewHolder) holder;
            viewHolder.ivFriendSticker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickMessage(data);
                }
            });
            viewHolder.ivFriendSticker.setImageResource(StickerMap.getStickerImg(data.getStickerId()));

        } else if (holder instanceof FriendTextViewHolder) {
            FriendTextViewHolder viewHolder = (FriendTextViewHolder) holder;
            viewHolder.txtFriendMsg.setText(data.getText());
            Tools.setTypeface(context, viewHolder.txtFriendMsg);
        }
    }

    private void onClickMessage(Object data) {
        if (onItemClickListener != null)
            onItemClickListener.onClick(data);
    }

    @Override
    public int getItemViewType(int position) {
        switch (messagesDAO.getData().get(position).getMsgTypeForView()) {
            case 0:
                return Config.TYPE_MY_VOICE;
            case 1:
                return Config.TYPE_MY_STICKER;
            case 2:
                return Config.TYPE_FRIEND_VOICE;
            case 3:
                return Config.TYPE_FRIEND_STICKER;
            case 4:
                return Config.TYPE_FRIEND_TEXT;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        try {
            return messagesDAO.getData().size();
        } catch (NullPointerException e) {
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }


    private void playVoice(String path) {
        AbstractLog.e("VOICE PATH", path);
        try {
            stopPlayVoice();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    progressBarLoadVoice.setVisibility(View.GONE);
                    txtDurationPlaing.setVisibility(View.VISIBLE);
                    progressBarVoice.setProgress(0);
                    mediaPlayer.start();
                    Log.w("onPrepared", "onPrepared");

                    countDownHandler = new Handler();
                    countDownHandler.postDelayed(updateVoiceTimeRunnable, 1000);
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    stopPlayVoice();
                    outPosition = -1;
                    notifyDataSetChanged();
                    return false;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.w("OnCompletion", "OnCompletion");
                    mediaPlayer.reset();
                    outPosition = -1;
                    notifyDataSetChanged();

                    if (countDownHandler != null)
                        countDownHandler.removeCallbacks(updateVoiceTimeRunnable);
                }
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlayVoice() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void setOutPosition(int outPosition) {
        this.outPosition = outPosition;
    }


    public void setMessagesDAO(MessagesDAO messagesDAO) {
        this.messagesDAO = messagesDAO;
    }

    public class MyVoiceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_voice)
        ImageButton ivMyVoice;
        @BindView(R.id.root_layout)
        LinearLayout rootView;
        @BindView(R.id.txt_count)
        TextView txtVoiceDuration;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.progress_bar_load_voice)
        ProgressBar progressBarLoadVoice;

        public MyVoiceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public LinearLayout getRootView() {
            return rootView;
        }
    }

    public class MyStickerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_sticker)
        ImageView ivMySticker;

        public MyStickerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FriendVoiceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_friend_voice)
        ImageView ivFriendVoice;

        public FriendVoiceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FriendStickerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_friend_sticker)
        ImageView ivFriendSticker;

        public FriendStickerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FriendTextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_friend_msg)
        TextView txtFriendMsg;

        public FriendTextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private long startTime;
    private long finalTime;
    private long showTime;
    private Handler countDownHandler = new Handler();

    private Runnable updateVoiceTimeRunnable = new Runnable() {

        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            finalTime = mediaPlayer.getDuration();
            showTime = (finalTime - startTime) / 1000;

            txtDurationPlaing.setText("0:" + String.format("%02d", showTime));
            progressBarVoice.setProgress(((int) (startTime / 1000)) + 1);

            if (countDownHandler != null)
                countDownHandler.postDelayed(this, 1000);
        }
    };
}
