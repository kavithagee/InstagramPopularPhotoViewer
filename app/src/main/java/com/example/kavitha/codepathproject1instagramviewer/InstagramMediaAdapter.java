package com.example.kavitha.codepathproject1instagramviewer;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kavitha on 9/17/15.
 */
public class InstagramMediaAdapter extends ArrayAdapter {
    public InstagramMediaAdapter(Context context, List<InstagramMedia> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final InstagramMedia photo = (InstagramMedia) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        RoundedImageView ivUserPhoto = (RoundedImageView) convertView.findViewById(R.id.ivUserPhoto);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvMoreComment = (TextView) convertView.findViewById(R.id.tvMoreComment);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);
        tvUsername.setText(Html.fromHtml("<b><font color=\"#3f729b\">" + photo.userName + "</font></b> "));
        tvCaption.setText(Html.fromHtml("<b><font color=\"#3f729b\">" + photo.userName + "</font></b> " +photo.caption));
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.mediaUrl).into(ivPhoto);

        ivUserPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.userPhotoUrl).into(ivUserPhoto);

        //comments circus
        int commentCount = photo.comments.size();
        if (commentCount > 0) {
            if (commentCount >= 3) {
                tvMoreComment.setText("View all " + commentCount + " comments");
            } else {
                tvMoreComment.setVisibility(View.GONE);
            }
            tvComment2.setText(Html.fromHtml("<b><font color=\"#3f729b\">" + photo.comments.get(commentCount-1).fromUsername + "</font></b> " + photo.comments.get(commentCount-1).text));
            if (commentCount >= 2) {
                tvComment1.setText(Html.fromHtml("<b><font color=\"#3f729b\">" + photo.comments.get(commentCount-2).fromUsername + "</font></b> " + photo.comments.get(commentCount-1).text));
            } else {
                tvComment1.setVisibility(View.GONE);
            }
        } else {
            tvMoreComment.setVisibility(View.GONE);
            tvComment1.setVisibility(View.GONE);
            tvComment2.setVisibility(View.GONE);
        }

        tvMoreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MediaActivity activity = (MediaActivity) getContext();
            FragmentManager fm = activity.getFragmentManager();
            CommentDialog commentDialog = new CommentDialog();
            Bundle args = new Bundle();
            args.putParcelable("photo", photo);
            commentDialog.setArguments(args);
            commentDialog.show(fm, "comment_dialog");
            }
        });
        return convertView;
    }
}
