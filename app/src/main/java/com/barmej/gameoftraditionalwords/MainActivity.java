package com.barmej.gameoftraditionalwords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE =123 ;
    public ImageView imageViewPicture;
    private ImageButton changeLang;

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
        imageViewPicture = findViewById(R.id.image_view_picture);
        ANSWERS = getResources().getStringArray(R.array.answers);
        ANSWERS_DESCRIPTION = getResources().getStringArray(R.array.answer_description);
        shareImage();

        changeLang = findViewById(R.id.button_change_language);

    }
        public void onChangeClicked (View view){
            showLanguageDialog();
    }

    private void showLanguageDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                        }
                        saveLanguage(language);
                        LocaleHelper.setLocale(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    }
                }).create();
        alertDialog.show();
    }

    private void saveLanguage(String lang) {
        SharedPreferences sharedPreferences = getSharedPreferences(constants.APP_PREF ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(constants.APP_LANG,lang);
        editor.apply();

    }

    private void shareImage() {
        Random random = new Random();
        int randomImageIndex = random.nextInt(IMAGE.length);
        mCurrentImage = IMAGE[randomImageIndex];
        mCurrentAnswer = ANSWERS[randomImageIndex];
        mCurrentAnswerDescription = ANSWERS_DESCRIPTION[randomImageIndex];
        Drawable placeDrawable = ContextCompat.getDrawable(this,mCurrentImage);
        imageViewPicture.setImageDrawable(placeDrawable);
    }

    public void onChangeImageClicked(View view) {
        shareImage();
    }

    public void onAnswerClicked(View view) {
        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
        intent.putExtra(constants.QUESTION_ANSWER,mCurrentAnswer + ": "+ mCurrentAnswerDescription);
        startActivity(intent);

    }

    public void onShareImageClicked(View view) {
        Intent intent = new Intent(MainActivity.this,ShareActivity.class);
        intent.putExtra(constants.SHARE_IMAGE_EXTRA,mCurrentImage);
        startActivity(intent);

    }
}
