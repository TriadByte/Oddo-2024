package com.crimesnap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<String> imagePaths;


    public ImageAdapter(Context context, List<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Load image into ImageView using Picasso or any other image loading library
        Picasso.get().load(imagePaths.get(position)).into(holder.imageView);

        // Set click listener for cancel button to remove the image
        holder.btnCancel.setOnClickListener(v -> {
            imagePaths.remove(position);
            notifyDataSetChanged();
        });


        return convertView;
    }

//    public void deleteImage(int position) {
//        imagePaths.remove(position);
//        notifyDataSetChanged();
//    }

    private static class ViewHolder {
        ImageView imageView;
        ImageView btnCancel;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.image_view);
            btnCancel = view.findViewById(R.id.btn_cancel);
        }
    }
}
