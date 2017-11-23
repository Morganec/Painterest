package com.painterest.painterestnote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements ColorPickerDialog.OnColorChangedListener {
    private static final int RESULT_LOAD_IMG = 0;
    DrawingView dv ;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SeekBar sk=(SeekBar) findViewById(R.id.seekBar);
        size= sk.getProgress();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.line1);
        dv = new DrawingView(this);

        linearLayout.addView(dv);





        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                size = i;
                dv.setPinceauSize(size);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final Button btnColorPicker = (Button) findViewById(R.id.btnColorPicker);
        btnColorPicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                new ColorPickerDialog(MainActivity.this,MainActivity.this,"picker",Color.BLACK,Color.WHITE).show();
            }
        });


        final Button btnGomme = (Button) findViewById(R.id.btnGomme);
        btnGomme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dv.setPinceauColor(Color.WHITE);
                dv.setPinceauSize(size);
            }
        });

        final Button btnPinceau = (Button) findViewById(R.id.btnPinceau);
        btnPinceau.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dv.setPinceauColor(Color.BLACK);
                dv.setPinceauSize(size);
            }
        });

        final Button btnEraseAll = (Button) findViewById(R.id.btnEraseAll);
        btnEraseAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               dv.getmCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            }
        });


        final Button btnChoosePict = (Button) findViewById(R.id.btnChoosePict);
       btnChoosePict.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);




            }
        });

    }
    @Override
    public void colorChanged(String key, int color) {
        // TODO Auto-generated method stub
        dv.getmPaint().setColor(color);
    }


}
