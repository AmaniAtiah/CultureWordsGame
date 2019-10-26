package com.barmej.gameoftraditionalwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
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
        showNewImage();

    }

    private void showNewImage() {
        Random random = new Random();
        int randomImageIndex = random.nextInt(IMAGE.length);
        mCurrentImage = IMAGE[randomImageIndex];
        mCurrentAnswer = ANSWERS[randomImageIndex];
        mCurrentAnswerDescription = ANSWERS_DESCRIPTION[randomImageIndex];
        Drawable placeDrawable = ContextCompat.getDrawable(this,mCurrentImage);
        imageViewQuestion.setImageDrawable(placeDrawable);
    }

    public void onChangeImageClicked(View view) {
        showNewImage();
    }

    public void onAnswerClicked(View view) {
        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
        intent.putExtra("question_answer",mCurrentAnswer + ": "+ mCurrentAnswerDescription);
        startActivity(intent);



    }
}
