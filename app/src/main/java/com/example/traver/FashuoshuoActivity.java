package com.example.traver;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.traver.util.DatabaseUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FashuoshuoActivity extends Activity {
    Bitmap bitmap1;
    ByteArrayOutputStream baos=new ByteArrayOutputStream();
    //Button takephoto1;
    //Button xiangce;
    Button tijao;
    Spinner spinner;
    public static final int TAKE_PHOTO1=1;
    public static final int TAKE_PHOTO2=2;
    public static final int TAKE_PHOTO3=3;
    public static final int CHOOSE_PHOTO1=4;
    public static final int CHOOSE_PHOTO2=5;
    public static final int CHOOSE_PHOTO3=6;
    private ImageView picture;
    private ImageView picture2;
    private ImageView picture3;
    private EditText editText;
    private Uri imageUri;
    final String[] arrayS=new String[]{"拍照","相册","取消"};
    byte[] p1;
    byte[] p2;
    byte[] p3;
    String leibie="分享";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashuoshuo);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FashuoshuoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);}
        //takephoto1=(Button)findViewById(R.id.takephoto);
        //xiangce=(Button)findViewById(R.id.xiangce);
        tijao=(Button)findViewById(R.id.bianjiwanchen);
        spinner=(Spinner)findViewById(R.id.spinner);
        editText=(EditText)findViewById(R.id.edit);
        picture=(ImageView)findViewById(R.id.ima1);
        picture2=(ImageView)findViewById(R.id.ima2);
        picture3=(ImageView)findViewById(R.id.ima3);
        setOnclick();
        /*
        takephoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(getApplicationContext(),"com.example.traver.fileprovider",outputImage);
                }else {
                    imageUri=Uri.fromFile(outputImage);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });


        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(FashuoshuoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
            }
        });
        */
    }
    public void setOnclick(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] fenleis=getResources().getStringArray(R.array.fenlei);
                leibie=fenleis[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tijao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseUtil.tijiaoshuoshuo(editText.getText().toString(),p1,p2,p3,leibie);
                DatabaseUtil.setJifen(5);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click","image1");
                AlertDialog.Builder builder = new AlertDialog.Builder(FashuoshuoActivity.this);
                builder.setItems(arrayS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                paishe(1);
                                break;
                            case 1:
                                openAlbum(1);
                            default:

                        }
                    }
                }).setCancelable(true).create();
                builder.show();
            }
        });
        picture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(FashuoshuoActivity.this);
                builder2.setItems(arrayS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                paishe(2);
                                break;
                            case 1:
                                openAlbum(2);
                            default:

                        }
                    }
                }).setCancelable(true).create();
                builder2.show();
            }
        });
        picture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder3 = new AlertDialog.Builder(FashuoshuoActivity.this);
                builder3.setItems(arrayS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                paishe(3);
                                break;
                            case 1:
                                openAlbum(3);
                            default:

                        }
                    }
                }).setCancelable(true).create();
                builder3.show();
            }
        });
    }

    private void openAlbum(int picid){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        switch (picid){
            case 1:
                startActivityForResult(intent,CHOOSE_PHOTO1);
                break;
            case 2:
                startActivityForResult(intent,CHOOSE_PHOTO2);
                break;
            case 3:
                startActivityForResult(intent,CHOOSE_PHOTO3);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantReults){
        switch (requestCode){
            case 1:
                if(grantReults.length>0&&grantReults[0]==PackageManager.PERMISSION_GRANTED){
                    //openAlbum();
                }else {
                    Toast.makeText(this,"无权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO1:
                if(resultCode==RESULT_OK){
                    try {
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inJustDecodeBounds=true;
                        Rect rect=new Rect();
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        options.inSampleSize=calculateInSampleSize(options,400,225);
                        options.inJustDecodeBounds=false;
                        bitmap1= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        bitmap1.compress(Bitmap.CompressFormat.JPEG,100,baos);
                        p1=baos.toByteArray();
                        picture.setImageBitmap(bitmap1);
                        picture2.setVisibility(View.VISIBLE);
                        Log.d("photo","jieshu");
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_PHOTO2:
                if(resultCode==RESULT_OK){
                    try {
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inJustDecodeBounds=true;
                        Rect rect=new Rect();
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        options.inSampleSize=calculateInSampleSize(options,400,225);
                        options.inJustDecodeBounds=false;
                        bitmap1= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        bitmap1.compress(Bitmap.CompressFormat.JPEG,100,baos);
                        p2=baos.toByteArray();
                        picture2.setImageBitmap(bitmap1);
                        picture3.setVisibility(View.VISIBLE);
                        Log.d("photo","jieshu");
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_PHOTO3:
                if(resultCode==RESULT_OK){
                    try {
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inJustDecodeBounds=true;
                        Rect rect=new Rect();
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        options.inSampleSize=calculateInSampleSize(options,400,225);
                        options.inJustDecodeBounds=false;
                        bitmap1= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        bitmap1.compress(Bitmap.CompressFormat.JPEG,100,baos);
                        p3=baos.toByteArray();
                        picture3.setImageBitmap(bitmap1);
                        Log.d("photo","jieshu");
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO1:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data,1);
                    }else {
                        handleImageBeforeKitKat(data,1);
                    }
                }
                break;
            case CHOOSE_PHOTO2:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data,2);
                    }else {
                        handleImageBeforeKitKat(data,2);
                    }
                }
                break;
            case CHOOSE_PHOTO3:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data,3);
                    }else {
                        handleImageBeforeKitKat(data,3);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data,int picid){
        String imagePath=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documentss".equals(uri.getAuthority())){
                Uri contenturi= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(uri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath=uri.getPath();
        }
        displayImage(imagePath,picid);
    }
    private void handleImageBeforeKitKat(Intent data,int picid){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath,picid);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath,int picid){
        if(imagePath!=null){
            switch (picid){
                case 1:
                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inJustDecodeBounds=true;
                    Rect rect=new Rect();
                    BitmapFactory.decodeFile(imagePath,options);
                    options.inSampleSize=calculateInSampleSize(options,400,225);
                    options.inJustDecodeBounds=false;
                    bitmap1=BitmapFactory.decodeFile(imagePath,options);
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    p1=baos.toByteArray();
                    baos.reset();
                    picture.setImageBitmap(bitmap1);
                    picture2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    BitmapFactory.Options options2=new BitmapFactory.Options();
                    options2.inJustDecodeBounds=true;
                    BitmapFactory.decodeFile(imagePath,options2);
                    options2.inSampleSize=calculateInSampleSize(options2,400,225);
                    options2.inJustDecodeBounds=false;
                    bitmap1=BitmapFactory.decodeFile(imagePath,options2);
                    baos=new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    p2=baos.toByteArray();
                    picture2.setImageBitmap(bitmap1);
                    picture3.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    BitmapFactory.Options options3=new BitmapFactory.Options();
                    options3.inJustDecodeBounds=true;
                    BitmapFactory.decodeFile(imagePath,options3);
                    options3.inSampleSize=calculateInSampleSize(options3,400,225);
                    options3.inJustDecodeBounds=false;
                    bitmap1=BitmapFactory.decodeFile(imagePath,options3);
                    baos=new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    p3=baos.toByteArray();
                    picture3.setImageBitmap(bitmap1);
                    break;
                default:
            }
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }
    public void paishe(int id){
        switch (id){
            case 1:
                File outputImage1=new File(getExternalCacheDir(),"output_image1.jpg");
                try{
                    if(outputImage1.exists()){
                        outputImage1.delete();
                    }
                    outputImage1.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(getApplicationContext(),"com.example.traver.fileprovider",outputImage1);
                }else {
                    imageUri=Uri.fromFile(outputImage1);
                }
                Intent intent1=new Intent("android.media.action.IMAGE_CAPTURE");
                intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent1,TAKE_PHOTO1);
                break;
            case 2:
                File outputImage2=new File(getExternalCacheDir(),"output_image2.jpg");
                try{
                    if(outputImage2.exists()){
                        outputImage2.delete();
                    }
                    outputImage2.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(getApplicationContext(),"com.example.traver.fileprovider",outputImage2);
                }else {
                    imageUri=Uri.fromFile(outputImage2);
                }
                Intent intent2=new Intent("android.media.action.IMAGE_CAPTURE");
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent2,TAKE_PHOTO2);
                break;
            case 3:
                File outputImage3=new File(getExternalCacheDir(),"output_image3.jpg");
                try{
                    if(outputImage3.exists()){
                        outputImage3.delete();
                    }
                    outputImage3.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(getApplicationContext(),"com.example.traver.fileprovider",outputImage3);
                }else {
                    imageUri=Uri.fromFile(outputImage3);
                }
                Intent intent3=new Intent("android.media.action.IMAGE_CAPTURE");
                intent3.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent3,TAKE_PHOTO3);
                break;
            default:
        }
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
