package com.pomohouse.message.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pomohouse.message.R;
import com.pomohouse.message.config.Config;
import com.pomohouse.message.controller.AvatarMap;
import com.pomohouse.message.interfaceclass.OnItemClickListener;
import com.pomohouse.message.model.ContactDAO;
import com.pomohouse.message.model.ContactData;
import com.pomohouse.message.tools.Tools;
import com.pomohouse.message.view.CircleTransform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SITTIPONG on 30/8/2559.
 */
public class AllContactAdapter extends RecyclerView.Adapter<AllContactAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private ContactDAO contactDAO;

    public AllContactAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_list_contact, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ContactData contactModel = contactDAO.getContactData().get(position);

        Tools.setTypeface(context, holder.txtName);
        holder.txtName.setText(Tools.subStringName(contactModel.getName()));

        Tools.setTypeface(context, holder.txtTime);
        holder.txtTime.setText(getDate(contactModel.getLastTimeStamp()));

        if (!TextUtils.isEmpty(contactModel.getAvatar())) {
            if (contactModel.getAvatarType() == 0 && contactModel.getAvatar() != null && !contactModel.getAvatar().isEmpty()) {
                holder.ivContactAvatarIcon.setVisibility(View.VISIBLE);
                holder.boxAvatarUrl.setVisibility(View.GONE);
                holder.ivContactAvatarIcon.setImageResource(new AvatarMap().getAvatarIcon(contactModel.getAvatar()));
            } else {
                holder.boxAvatarUrl.setVisibility(View.VISIBLE);
                holder.ivContactAvatarIcon.setVisibility(View.GONE);
                Glide.with(context).load(contactModel.getAvatar()).error(R.drawable.placeholder_avatar).transform(new CircleTransform(context)).into(holder.ivContactAvatar);
            }
        } else {
            holder.ivContactAvatar.setVisibility(View.VISIBLE);
            holder.boxAvatarUrl.setVisibility(View.GONE);
            holder.ivContactAvatarIcon.setImageResource(R.drawable.placeholder_avatar);
        }

        if (contactModel.getContactType().equals(Config.TypeRelation.FAMILY)) {
            holder.ivContactType.setImageResource(R.drawable.contact_type_family);
        } else {
            holder.ivContactType.setImageResource(R.drawable.contact_type_bff);
        }

        if (contactModel.isHaveNewMsg != null) {
            if (contactModel.isHaveNewMsg.equals("F")) {
                holder.ivNewMessage.setVisibility(View.INVISIBLE);
            } else {
                holder.ivNewMessage.setVisibility(View.VISIBLE);
            }
        } else {
            holder.ivNewMessage.setVisibility(View.INVISIBLE);
        }

        if (onItemClickListener != null) {
            setOnClick(holder.itemView, contactModel);
        }
    }

    private void setOnClick(View itemView, final ContactData m) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return contactDAO.getContactData().size();
        } catch (NullPointerException e) {
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setContactDao(ContactDAO c) {
        this.contactDAO = c;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.ivContactAvatar)
        AppCompatImageView ivContactAvatar;
        @BindView(R.id.ivContactAvatarIcon)
        AppCompatImageView ivContactAvatarIcon;
        @BindView(R.id.ivContactType)
        ImageView ivContactType;
        @BindView(R.id.iv_new_msg)
        ImageView ivNewMessage;
        @BindView(R.id.boxAvatarUrl)
        RelativeLayout boxAvatarUrl;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private String getDate(String ourDate) {
        if (TextUtils.isEmpty(ourDate))
            return "";
        try {
            ourDate = ourDate.replaceAll("T", " ").split(Pattern.quote("."))[0];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);
        } catch (Exception e) {
            ourDate = "";
        }
        return ourDate;
    }
}
