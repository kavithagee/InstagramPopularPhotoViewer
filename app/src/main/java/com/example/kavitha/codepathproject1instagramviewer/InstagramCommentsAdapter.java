package com.example.kavitha.codepathproject1instagramviewer;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

/**
 * Created by kavitha on 9/19/15.
 */
public class InstagramCommentsAdapter extends ArrayAdapter {
    public InstagramCommentsAdapter(Context context, List<MediaActivity.CommentObj> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MediaActivity.CommentObj commentObj = (MediaActivity.CommentObj) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }

        RoundedImageView ivCommentUserPhoto = (RoundedImageView) convertView.findViewById(R.id.ivCommentUserPhoto);
        TextView tvCommentUsername = (TextView) convertView.findViewById(R.id.tvCommentText);
        TextView tvCommentTimestamp = (TextView) convertView.findViewById(R.id.tvCommentTimestamp);

        ivCommentUserPhoto.setImageResource(0);
        Picasso.with(getContext()).load(commentObj.userPhotoUrl).into(ivCommentUserPhoto);

        tvCommentUsername.setText(Html.fromHtml("<b><font color=\"#3f729b\">" + commentObj.fromUsername + "</font></b> " + commentObj.text));
        Date now = new Date();
        tvCommentTimestamp.setText(""+ DateUtils.getRelativeTimeSpanString(commentObj.createdTime * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        return convertView;
    }
}
