package com.barmej.gameoftraditionalwords;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class AnswerActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BUNDLE_CURRENT_INDEX = "BUNDLE_CURRENT_INDEX";
    private TextView mTextViewAnswer;

    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mTextViewAnswer = findViewById(R.id.text_view_answer);

       answer = getIntent().getStringExtra(constants.QUESTION_ANSWER);
        if(answer != null)
            mTextViewAnswer.setText(answer);
    }

    public void  onReturnClicked(View view) {
        finish();
    }


}
