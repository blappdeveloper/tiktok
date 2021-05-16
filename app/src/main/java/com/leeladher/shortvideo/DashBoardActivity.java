package com.leeladher.shortvideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.leeladher.shortvideo.Adapter.videoadapter;
import com.leeladher.shortvideo.databinding.ActivityDashboardBinding;
import com.leeladher.shortvideo.models.videoModel;

public class DashBoardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    videoadapter adapter;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
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


    public void folowingBtn(View view) {

        Intent intent = new Intent(DashBoardActivity.this, FollowingActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSwipeRight(this);
        finish();
    }

    public void addBtn(View view) {
        checkPermission();

        Intent intent = new Intent(DashBoardActivity.this, UploadVideoActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideUp(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "[WARN] permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public void discoverPage(View view) {
        Intent intent = new Intent(DashBoardActivity.this,DiscoverActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideUp(this);
    }

    public void accountPage(View view) {
        Intent intent = new Intent(DashBoardActivity.this,AccountActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideUp(this);
    }

    public void massagePage(View view) {
        Intent intent = new Intent(DashBoardActivity.this,MessageActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Animatoo.animateSlideUp(this);
    }
}