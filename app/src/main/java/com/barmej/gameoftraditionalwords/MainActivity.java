package com.barmej.gameoftraditionalwords;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BUNDLE_CURRENT_IMAGE = "BUNDLE_CURRENT_IMAGE";
    private static final String BUNDLE_CURRENT_ANSWER = "BUNDLE_CURRENT_ANSWER";
    private static final String BUNDLE_CURRENT_ANSWER_DESCRIPTION = "BUNDLE_CURRENT_ANSWER_DESCEIPTION";
    public ImageView imageViewPicture;
    public ImageButton changeLang;


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

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE);
        String appLang = sharedPreferences.getString(Constants.APP_LANG, "");
        if (!appLang.equals(""))
            LocaleHelper.setLocale(this, appLang);

        setContentView(R.layout.activity_main);

        imageViewPicture = findViewById(R.id.image_view_picture);
        ANSWERS = getResources().getStringArray(R.array.answers);
        ANSWERS_DESCRIPTION = getResources().getStringArray(R.array.answer_description);
        showNewImage();

        changeLang = findViewById(R.id.button_change_language);

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }

        });
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
                        reload();
                    }
                }).create();
            alertDialog.show();
    }

    private void saveLanguage(String lang) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.APP_LANG,lang);
        editor.apply();
    }

    public void reload() {
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        }

        private void showNewImage() {
        Random random = new Random();
        int randomImageIndex = random.nextInt(IMAGE.length);
        mCurrentImage = IMAGE[randomImageIndex];
        mCurrentAnswer = ANSWERS[randomImageIndex];
        mCurrentAnswerDescription = ANSWERS_DESCRIPTION[randomImageIndex];
        showImage();
    }

    public void showImage() {
        Drawable imageDrawable = ContextCompat.getDrawable(this, mCurrentImage);
        imageViewPicture.setImageDrawable(imageDrawable);

    }

    public void onChangeImageClicked(View view) {
        showNewImage();
    }

    public void onAnswerClicked(View view) {
        Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
        intent.putExtra(Constants.QUESTION_ANSWER,mCurrentAnswer + ": "+ mCurrentAnswerDescription);
        startActivity(intent);
    }

    public void onShareImageClicked(View view) {
        Intent intent = new Intent(MainActivity.this,ShareActivity.class);
        intent.putExtra(Constants.SHARE_IMAGE_EXTRA,mCurrentImage);
        startActivity(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
                mCurrentImage = savedInstanceState.getInt(BUNDLE_CURRENT_IMAGE);
                mCurrentAnswer = savedInstanceState.getString(BUNDLE_CURRENT_ANSWER);
                mCurrentAnswerDescription = savedInstanceState.getString(BUNDLE_CURRENT_ANSWER_DESCRIPTION);
                showImage();
        }
        Log.i(TAG,"onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_CURRENT_IMAGE, mCurrentImage);
        outState.putString(BUNDLE_CURRENT_ANSWER, mCurrentAnswer);
        outState.putString(BUNDLE_CURRENT_ANSWER_DESCRIPTION, mCurrentAnswerDescription);
        Log.i(TAG,"onSaveInstanceState");
    }
}
