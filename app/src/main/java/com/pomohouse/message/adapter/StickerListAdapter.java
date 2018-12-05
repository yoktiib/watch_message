package com.pomohouse.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.pomohouse.message.R;
import com.pomohouse.message.controller.StickerMap;
import com.pomohouse.message.interfaceclass.OnItemClickListener;
import com.pomohouse.message.interfaceclass.OnItemTouchListener;
import com.pomohouse.message.model.ChatRoomData;
import com.pomohouse.message.model.StickerDAO;
import com.pomohouse.message.model.StickerData;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class StickerListAdapter extends RecyclerView.Adapter<StickerListAdapter.MyViewHolder> {

    private StickerDAO stickerDAO;
    private Context context;
    private OnItemTouchListener onItemTouchListener;

    public StickerListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_sticker, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StickerData data = stickerDAO.getStickerData().get(position);

        holder.ivSticker.setImageResource(data.getStickerImg());

        if (onItemTouchListener != null) {
            setOnTouch(holder.itemView, data);
        }
    }

    private void setOnTouch(View itemView, final StickerData data) {
        itemView.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
//                    Log.e("onSingleTapUp", "onSingleTapUp");
                    onItemTouchListener.onSingleTap(data);
                    return true;
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
//                    Log.e("onDoubleTap", "onDoubleTap");
                    onItemTouchListener.onDoubleTap(data);
                    return true;
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
//        if (listSticker.size() == 0)
//            return 0;
//        return listSticker.size();
        return stickerDAO.getStickerData().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_sticker)
        ImageView ivSticker;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setStickerData(StickerDAO dao) {
        this.stickerDAO = dao;
    }

    public void setOnItemClickListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }
}
