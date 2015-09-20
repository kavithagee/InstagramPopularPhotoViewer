package com.example.kavitha.codepathproject1instagramviewer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kavitha on 9/13/15.
 */
public class InstagramMedia implements Parcelable {
    public String userName;
    public String userPhotoUrl;
    public String caption;
    public String mediaUrl;
    public String type;
    public int mediaHeight;
    public int likesCount;
    public int createdTime;
    public ArrayList<MediaActivity.CommentObj> comments;

    public InstagramMedia() {
        this.comments = new ArrayList<MediaActivity.CommentObj>();
    }


    @Override
    public String toString() {
        return "InstagramMedia{" +
                "userName='" + userName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", caption='" + caption + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", type='" + type + '\'' +
                ", mediaHeight=" + mediaHeight +
                ", likesCount=" + likesCount +
                ", createdTime=" + createdTime +
                ", comments=" + comments +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userPhotoUrl);
        dest.writeString(this.caption);
        dest.writeString(this.mediaUrl);
        dest.writeString(this.type);
        dest.writeInt(this.mediaHeight);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.createdTime);
        dest.writeList(this.comments);
    }

    protected InstagramMedia(Parcel in) {
        this.userName = in.readString();
        this.userPhotoUrl = in.readString();
        this.caption = in.readString();
        this.mediaUrl = in.readString();
        this.type = in.readString();
        this.mediaHeight = in.readInt();
        this.likesCount = in.readInt();
        this.createdTime = in.readInt();
        this.comments = new ArrayList<MediaActivity.CommentObj>();
        in.readList(this.comments, List.class.getClassLoader());
    }

    public static final Creator<InstagramMedia> CREATOR = new Creator<InstagramMedia>() {
        public InstagramMedia createFromParcel(Parcel source) {
            return new InstagramMedia(source);
        }

        public InstagramMedia[] newArray(int size) {
            return new InstagramMedia[size];
        }
    };
}
