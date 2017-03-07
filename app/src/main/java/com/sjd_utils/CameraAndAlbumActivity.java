package com.sjd_utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import static com.sjd_utils.Utils.Util.fileIsExists;

/**
 * Created by sjd on 2017/2/14.
 */

public class CameraAndAlbumActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cmeraandalbumactivity);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void startCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 0);
    }

    public void startAlbum(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0://拍照
                switch (resultCode) {
                    case RESULT_OK://拍照成功
                        if (data != null) {
                            Bundle extras = data.getExtras();
                            Bitmap bmp = (Bitmap) extras.get("data");
                            imageView.setImageBitmap(bmp);
                        }
                        break;
                    case RESULT_CANCELED://取消保存
                        break;
                }
                break;
            case 1://相册选择
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            Uri selectedImage = data.getData();
                            String[] filePathColumns = {MediaStore.Images.Media.DATA};
                            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                            c.moveToFirst();
                            int columnIndex = c.getColumnIndex(filePathColumns[0]);
                            String path = c.getString(columnIndex);
                            imageView.setImageBitmap(getPicFromPath(path));//需要读取内存卡权限
                            c.close();
                        }
                        break;
                    case RESULT_CANCELED:
                        break;
                    default:
                }
                break;
        }
    }

    public static Bitmap getPicFromPath(String picPath) {
        Bitmap bitmap = null;
        if (!(picPath == null || picPath == "") && fileIsExists(picPath)) {//判断路径的时候 已经将图片赋值到bitmap里面了
            bitmap = BitmapFactory.decodeFile(picPath);
        }
        return bitmap;
    }
}
