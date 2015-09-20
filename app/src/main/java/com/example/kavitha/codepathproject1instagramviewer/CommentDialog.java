package com.example.kavitha.codepathproject1instagramviewer;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kavitha on 9/19/15.
 */
public class CommentDialog extends DialogFragment {
    public ArrayList<MediaActivity.CommentObj> allComments;
    private InstagramCommentsAdapter aComments;
    public CommentDialog() {
        ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.comment_dialog, container);

        InstagramMedia photo = getArguments().getParcelable("photo");
        allComments = photo.comments;
        aComments = new InstagramCommentsAdapter(getActivity(), allComments);
        ListView lvComments = (ListView) view.findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);
        getDialog().setTitle(Html.fromHtml("<font color=\"#3f729b\">COMMENTS</font>"));

        aComments.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }
}
