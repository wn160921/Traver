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
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traver.shujubiao.yonghu;
import com.example.traver.util.DatabaseUtil;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.traver.FashuoshuoActivity.calculateInSampleSize;


public class ZhuceActivity extends Activity {
    Button zhuce;
    EditText yonghuming;
    EditText nicen;
    EditText mima;
    EditText youxiang;
    CircleImageView tou_xiang;
    Handler handler;
    Bitmap bitmap;
    byte[] b;
    ByteArrayOutputStream baos;
    private Uri imageUri;
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=4;
    final String[] arrayS=new String[]{"拍照","相册","取消"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ZhuceActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);}
        inithander();
        initKonjian();

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
    public void initKonjian(){
        zhuce=(Button)findViewById(R.id.zhuce);
        yonghuming=(EditText)findViewById(R.id.yonghuming);
        nicen=(EditText)findViewById(R.id.necen);
        mima=(EditText)findViewById(R.id.mima);
        youxiang=(EditText)findViewById(R.id.youxiang);
        tou_xiang=(CircleImageView)findViewById(R.id.touxiang);
        tou_xiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ZhuceActivity.this);
                builder.setItems(arrayS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                paishe();
                                break;
                            case 1:
                                openAlbum();
                            default:

                        }
                    }
                }).setCancelable(true).create();
                builder.show();
            }
        });

        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yonghuming.getText().length()<4){
                    Toast.makeText(getApplicationContext(),"用户名过短",Toast.LENGTH_SHORT).show();
                }else if(mima.getText().length()<5){
                    Toast.makeText(getApplicationContext(),"密码过短",Toast.LENGTH_SHORT).show();
                }else if(b==null){
                    Toast.makeText(getApplicationContext(),"头像！！！",Toast.LENGTH_SHORT).show();
                }else if(yonghuming.getText().length()<5){
                    Toast.makeText(getApplicationContext(),"请输入邮箱",Toast.LENGTH_SHORT).show();
                }else {
                    String s="zhuce|"+yonghuming.getText().toString()+"|"+mima.getText().toString()+"|"+
                            youxiang.getText().toString()+"|"+nicen.getText().toString();
                    Log.d("zhuce",s);
                    DatabaseUtil.zhuceD(handler,s,b);

                }
            }
        });
    }
    public void inithander(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case 0:
                        Toast.makeText(getApplication(),"注册失败", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Intent intent=new Intent();
                        setResult(1,intent);
                        DataSupport.deleteAll(yonghu.class);
                        yonghu yonghu=new yonghu();
                        yonghu.setYonghuming(yonghuming.getText().toString());
                        yonghu.setJifen(100);
                        yonghu.setTouxiang(yonghuming.getText().toString()+"/touxiang.jpg");
                        yonghu.setNecen(nicen.getText().toString());
                        yonghu.save();
                        finish();
                }
            }
        };
    }
    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }
    public void paishe(){
        File outputImage1=new File(getExternalCacheDir(),"output_imageT.jpg");
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
            imageUri= Uri.fromFile(outputImage1);
        }
        Intent intent1=new Intent("android.media.action.IMAGE_CAPTURE");
        intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent1,TAKE_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    try {
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inJustDecodeBounds=true;
                        Rect rect=new Rect();
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        options.inSampleSize=calculateInSampleSize(options,300,300);
                        options.inJustDecodeBounds=false;
                        bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri),rect,options);
                        baos=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                        b=null;
                        b=baos.toByteArray();
                        tou_xiang.setImageBitmap(bitmap);
                        Log.d("photo","jieshu");
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
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
        displayImage(imagePath);
    }
    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
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
    private void displayImage(String imagePath){
        if(imagePath!=null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Rect rect = new Rect();
            BitmapFactory.decodeFile(imagePath, options);
            options.inSampleSize = calculateInSampleSize(options, 100, 100);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            b=null;
            b = baos.toByteArray();
            tou_xiang.setImageBitmap(bitmap);
        }
    }
}
