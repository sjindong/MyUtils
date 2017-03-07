package com.sjd_utils.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sjd on 2017/2/17.
 *
 * 图片操作  （包括bitmap，drawable之类）
 */

public class Util_Pic {
    /**
     * 获取给出路径的图片
     *
     * @param picPath 图片路径
     * @return 路径不存在则为空
     */
    public static Drawable getPicFromPath(String picPath) {
        Drawable drawable = null;
        if (!(picPath == null || picPath == "") && Util.fileIsExists(picPath)) {//判断路径的时候 已经将图片赋值到bitmap里面了
            drawable = new BitmapDrawable(BitmapFactory.decodeFile(picPath));
        }
        return drawable;
    }


    /**
     * 截取圆形图片， 透明背景
     * 注意要生成透明背景的圆形，图片一定要png类型的，不能是jpg类型
     *
     * @param bitmap 原始图片
     * @return
     */
    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            int length = 0;
            float roundPx = 0.0f;
            // 以较短的边为标准
            if (bitmap.getWidth() > bitmap.getHeight()) {
                length = bitmap.getHeight();
            } else {
                length = bitmap.getWidth();
            }
            roundPx = length / 2.0f;
            Bitmap circleBitmap = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(circleBitmap);

            final Paint paint = new Paint();
//            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            final Rect rect = new Rect(0, 0, length, length);
            final RectF rectF = new RectF(new Rect(0, 0, length, length));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, length, length);
            canvas.drawBitmap(bitmap, src, rect, paint);
            return circleBitmap;
        } catch (Exception e) {
            return bitmap;
        }
    }

    //获取缩小图片
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 400, 400);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取路径
     *
     * @param form 　保存图片格式
     * @param Name 　保存文件夹名字
     * @return
     */
    public static String getPhotopath(String form, String Name) {
        DateFormat df = new DateFormat();
        String name = df.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + form;
        String fileName = "/sdcard/" + Name + "/" + name;
        File file = new File("/sdcard/" + Name + "/");
        if (!file.exists()) {//文件夹不存在
            file.mkdirs();//创建文件夹
        }
        return fileName;
    }

    /**
     * 保存bitmap数据到图片
     *
     * @param path
     * @param bm
     */
    public static void saveBitmap(String path, Bitmap bm) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 将Bitmap保存为PNG图片文件
     *
     * @param bm
     * @param fileName 带小写的格式名称
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

}
