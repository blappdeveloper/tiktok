package com.leeladher.shortvideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.leeladher.shortvideo.Adapter.videoadapter;
import com.leeladher.shortvideo.databinding.ActivityFollowingBinding;
import com.leeladher.shortvideo.models.videoModel;

import java.util.ArrayList;
public class FollowingActivity extends AppCompatActivity {

    ActivityFollowingBinding binding;
    videoadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFollowingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportActionBar().hide();

        FirebaseRecyclerOptions<videoModel> options =
                new FirebaseRecyclerOptions.Builder<videoModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("video"), videoModel.class)
                        .build();

        adapter = new videoadapter(options);
        binding.viewPager.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    public void forYouBtn(View view) {
        Intent intent = new Intent(FollowingActivity.this,DashBoardActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSwipeLeft(this);
        finish();
    }
}