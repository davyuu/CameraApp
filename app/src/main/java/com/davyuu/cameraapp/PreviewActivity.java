package com.davyuu.cameraapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;

import java.io.File;

import static com.devbrackets.android.exomedia.core.video.scale.ScaleType.CENTER_CROP;

public class PreviewActivity extends AppCompatActivity {

    private static final String FILE_PATH_KEY = "PreviewActivity.filePath";
    public static final int REQUEST_CODE_PREVIEW = 1;

    String filePath;
    EMVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        videoView = (EMVideoView) findViewById(R.id.preview_surface_view);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        if(intent.hasExtra(FILE_PATH_KEY)){
            filePath = intent.getStringExtra(FILE_PATH_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        videoView.setScaleType(CENTER_CROP);
        videoView.setVideoPath(filePath);
        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                videoView.start();
            }
        });
        videoView.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                videoView.restart();
            }
        });
    }

    @Override
    protected void onDestroy() {
        new File(filePath).delete();
        super.onDestroy();
    }

    public static void startActivity(Activity activity, String filePath){
        Intent intent = new Intent(activity, PreviewActivity.class);
        intent.putExtra(FILE_PATH_KEY, filePath);
        activity.startActivityForResult(intent, REQUEST_CODE_PREVIEW);
    }
}
