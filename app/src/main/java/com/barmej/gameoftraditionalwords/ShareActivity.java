package com.barmej.gameoftraditionalwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import static android.provider.MediaStore.Images.Media.insertImage;

public class ShareActivity extends AppCompatActivity {

    private int mShareImage;
    public EditText mEditTextShareTitle;
    private ImageView imageViewPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mEditTextShareTitle = findViewById(R.id.edit_text_share_title);
        imageViewPicture = findViewById(R.id.image_view_question);
        mShareImage = getIntent().getIntExtra(constants.SHARE_IMAGE_EXTRA,0);

        Drawable imageDrawable = ContextCompat.getDrawable(this,mShareImage);
        imageViewPicture.setImageDrawable(imageDrawable);

        SharedPreferences sharedPreferences = getSharedPreferences(constants.APP_PREF, MODE_PRIVATE);
        String questionTitle = sharedPreferences.getString(constants.SHARE_TITLE,"");
        mEditTextShareTitle.setText(questionTitle);

    }

    public void onShareImageClicked(View view) {
        Bitmap mBitmap =BitmapFactory.decodeResource(getResources(),mShareImage);
        String questionTitle = mEditTextShareTitle.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = insertImage(this.getContentResolver(), mBitmap, constants.IMAGE, null);
        Uri imageUri =  Uri.parse(path);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, questionTitle );
        shareIntent.setType("image/jpeg");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, constants.SHARE_IMAGE));


        SharedPreferences sharedPreferences = getSharedPreferences(constants.APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(constants.SHARE_TITLE, questionTitle);
        editor.apply();


    }




}
