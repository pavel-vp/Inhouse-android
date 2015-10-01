package com.mobileme.photolocator.gui;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileme.photolocator.R;
import com.mobileme.photolocator.api.HSPhoto;
import com.mobileme.photolocator.dao.HSSettings;
import com.mobileme.photolocator.dao.HSRestHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 23.09.2015.
 */
public class PhotoListAdapter extends ArrayAdapter<HSPhoto> {

    List<HSPhoto> listPhoto;
    final Context context;
    int layoutResID;

    static SimpleDateFormat sdf = new SimpleDateFormat("kk:mm dd.MM.yyyy");

    public PhotoListAdapter(Context context, int resource, List<HSPhoto> objects) {
        super(context, resource,  objects);
        listPhoto = objects;
        this.context = context;
        layoutResID = resource;
    }

    @Override
    public int getCount() {
        return listPhoto.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhotoHolder holder = null;
        View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResID, parent, false);
            holder = new PhotoHolder(row);
        }
        else
        {
            holder = (PhotoHolder)row.getTag();
        }

        HSPhoto itemdata = listPhoto.get(position);
        holder.setPhoto(itemdata, HSRestHelper.getInstance(context));

        return row;

    }

    static class PhotoHolder{

        ImageView ivMini;
        TextView tvName;
        TextView tvCreatedate;
        TextView tvStatus;
        TextView tvStatusMsg;
        Button btnDelete;
        HSPhoto photo;

        public PhotoHolder(View v) {
            super();
            this.tvName = (TextView)v.findViewById(R.id.tvName);
            this.tvCreatedate=(TextView)v.findViewById(R.id.tvCreatedate);
            this.tvStatus=(TextView)v.findViewById(R.id.tvStatus);
            this.tvStatusMsg=(TextView)v.findViewById(R.id.tvStatusMsg);
            this.btnDelete=(Button)v.findViewById(R.id.btnDelete);
            this.ivMini = (ImageView)v.findViewById(R.id.ivMini);
            v.setTag(this);
        }

        public void setPhoto(HSPhoto photo, final HSRestHelper service) {

            this.photo = photo;
            this.tvName.setText(photo.getName());
            this.tvCreatedate.setText(sdf.format(new Date(photo.getCreatedate())));
            this.tvStatusMsg.setText(null);
            if (photo.getImagemini() != null) {
                this.ivMini.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(photo.getImagemini())));
            } else {
                this.ivMini.setImageBitmap(null);
            }
            switch (photo.getStatus()) {
                case HSSettings.STATUS_NEW:
                    this.tvStatus.setText("Новая");
                    break;
                case HSSettings.STATUS_SENDING:
                    this.tvStatus.setText("Отправляем...");
                    break;
                case HSSettings.STATUS_SENDOK:
                    this.tvStatus.setText("Отправлено");
                    break;
                case HSSettings.STATUS_SENDERROR:
                    this.tvStatus.setText("Ошибка при отправке");
                    this.tvStatusMsg.setText(photo.getStatusMsg());
                    break;
            }
            this.btnDelete.setTag(photo);
            this.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HSPhoto p = (HSPhoto) (v.getTag());

                    service.getSettings().getDao().getHsPhotoDBHelper().deletePhoto(p);
                    Toast.makeText(v.getContext(), "Удалили", Toast.LENGTH_SHORT).show();
                    service.sendDisplayUpdate();
                }
            });

        }

    }

}
