package com.leeladher.shortvideo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.leeladher.shortvideo.R;
import com.leeladher.shortvideo.commentpanel;
import com.leeladher.shortvideo.models.videoModel;

import retrofit2.http.Url;

public class videoadapter extends FirebaseRecyclerAdapter<videoModel,videoadapter.myviewholder>{
    DatabaseReference likeReference;

    Boolean testClick = false;
    public videoadapter(@NonNull FirebaseRecyclerOptions<videoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull videoModel model) {
         holder.setdata(model);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userId=firebaseUser.getUid();
        String postKey=getRef(position).getKey();
        holder.getLikeButtonStatus(postKey,userId);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,postKey);
                intent.setType("text/plain");
                v.getContext().startActivity(Intent.createChooser(intent,"send to"));
            }
        });
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testClick = true;
                likeReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (testClick==true){
                            if (snapshot.child(postKey).hasChild(userId)) {
                                likeReference.child(postKey).child(userId).removeValue();
                                testClick=false;
                            }else {
                                likeReference.child(postKey).child(userId).setValue(true);
                                testClick=false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, commentpanel.class);
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
              Intent intent =   new Intent(v.getContext(),commentpanel.class);
                intent.putExtra("postKey",postKey);
                v.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_layout,parent,false);
        likeReference = FirebaseDatabase.getInstance().getReference("likes");
        return new myviewholder(view);

    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        VideoView videoView;
        TextView title,likeText;
        ProgressBar pbar;
        ImageView ringTon , likeBtn , comment, profileImage, share;
        DatabaseReference likeReference;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            videoView=(VideoView)itemView.findViewById(R.id.media_container);
            title=(TextView)itemView.findViewById(R.id.textView2);
            //desc=(TextView)itemView.findViewById(R.id.textView7);
            pbar=(ProgressBar)itemView.findViewById(R.id.progressBar);
            ringTon=itemView.findViewById(R.id.ringtone2);
            likeBtn=itemView.findViewById(R.id.like_Btn);
            likeText=itemView.findViewById(R.id.like_text);
            comment=itemView.findViewById(R.id.comment);
            profileImage=itemView.findViewById(R.id.profileImage);
            share=itemView.findViewById(R.id.share);
        }

        void setdata(videoModel obj)
        {
            videoView.setVideoPath(obj.getVurl());
            title.setText(obj.getVtitle());
//            desc.setText(obj.getVdesc());



            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    pbar.setVisibility(View.GONE);
                    mediaPlayer.start();
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            Glide.with(itemView.getContext()).load(R.drawable.giphy).into(ringTon);

        }

        public void getLikeButtonStatus(final String postKey, final String userId) {
            likeReference= FirebaseDatabase.getInstance().getReference("likes");
            likeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(postKey).hasChild(userId)) {
                        int likeCount=(int)snapshot.child(postKey).getChildrenCount();
                        likeText.setText(likeCount+" Like");
                        likeBtn.setImageResource(R.drawable.ic_heart__1_);
                    }else {
                        int likeCount=(int)snapshot.child(postKey).getChildrenCount();
                        likeText.setText(likeCount+" Like");
                        likeBtn.setImageResource(R.drawable.ic_heart);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
