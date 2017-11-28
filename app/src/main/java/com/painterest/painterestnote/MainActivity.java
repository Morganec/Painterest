package com.painterest.painterestnote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    DrawingView dv ;
    LinearLayout linearLayout;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SeekBar sk=(SeekBar) findViewById(R.id.seekBar);
        size= sk.getProgress();
         linearLayout = (LinearLayout) findViewById(R.id.line1);
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

        final Button btnTakePict = (Button) findViewById(R.id.btnTakePict);
        btnTakePict.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
                    takePictureIntent.getExtras();


                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageView imageView = ( ImageView) findViewById(R.id.image);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            BitmapDrawable imageDraw = new BitmapDrawable(imageBitmap);
            dv.setBackground(imageDraw);

        }else if(requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){
            Bitmap bm=null;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BitmapDrawable imageDraw = new BitmapDrawable(bm);
            dv.setBackground(imageDraw);

        }



    }



    @Override
    public void colorChanged(String key, int color) {
        // TODO Auto-generated method stub
        dv.getmPaint().setColor(color);
    }


}
