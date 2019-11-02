package com.barmej.gameoftraditionalwords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE =123 ;
    private ImageView imageViewQuestion;

    private int IMAGE [] = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10,
            R.drawable.icon_11,
            R.drawable.icon_12,
            R.drawable.icon_13
    };

    private String[] ANSWERS;
    private String[] ANSWERS_DESCRIPTION;

    private String mCurrentAnswer, mCurrentAnswerDescription;
    private int mCurrentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewQuestion = findViewById(R.id.image_view_question);
        ANSWERS = getResources().getStringArray(R.array.answers);
        ANSWERS_DESCRIPTION = getResources().getStringArray(R.array.answer_description);
        shareImage();

    }


    private void shareImage() {
        Random random = new Random();
        int randomImageIndex = random.nextInt(IMAGE.length);
        mCurrentImage = IMAGE[randomImageIndex];
        mCurrentAnswer = ANSWERS[randomImageIndex];
        mCurrentAnswerDescription = ANSWERS_DESCRIPTION[randomImageIndex];
        Drawable placeDrawable = ContextCompat.getDrawable(this,mCurrentImage);
        imageViewQuestion.setImageDrawable(placeDrawable);
    }

    public void onChangeImageClicked(View view) {
        shareImage();
    }

    public void onAnswerClicked(View view) {
        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
        intent.putExtra("question_answer",mCurrentAnswer + ": "+ mCurrentAnswerDescription);
        startActivity(intent);

    }

    public void onShareImageClicked(View view) {
        Intent intent = new Intent(MainActivity.this,ShareActivity.class);
        intent.putExtra("image_url",mCurrentImage);
        startActivity(intent);

    }
}
