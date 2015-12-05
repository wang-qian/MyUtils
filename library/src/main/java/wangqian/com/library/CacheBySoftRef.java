package wangqian.com.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 *  图片缓存
 * WQ on 2015/11/19 10:21
 * wendell.std@gmail.com
 */
public class CacheBySoftRef {
    private Map<String,SoftReference<Bitmap>> imageCache = new HashMap<>();
    public void addBitmapToCache(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
        imageCache.put(path,softBitmap);
    }
    public Bitmap getBitmapByPath(String path){
        SoftReference<Bitmap> softBitmap = imageCache.get(path);
        if (softBitmap==null){
            return  null;
        }
        Bitmap bitmap = softBitmap.get();
        return  bitmap;
    }





}
