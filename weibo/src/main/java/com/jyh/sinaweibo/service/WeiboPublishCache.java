package com.jyh.sinaweibo.service;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.jyh.sinaweibo.util.StreamUtils.close;

/**
 * Created by cheng on 2017/1/5.
 */
public class WeiboPublishCache {
    private final static String TAG = WeiboPublishCache.class.getName();

    private WeiboPublishCache() {

    }

    static String getImageCachePath(Context context, String id) {
        return String.format("%s/WeiboPictures/%s", context.getCacheDir().getAbsolutePath(), id);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static String getFileCachePath(Context context, String id) {
        String dir = context.getFilesDir().getAbsolutePath() + "/WeiboQueue";
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (id != null) {
            return String.format("%s/%s.weibo", dir, id);
        }
        return dir;
    }

    static String getIdByFile(File file) {
        if (file == null)
            return null;
        String name = file.getName();
        int index = name.indexOf(".tweet");
        if (index == -1)
            return name;
        return name.substring(0, index);
    }

    static void removeImages(Context context, String id) {
        String dir = getImageCachePath(context, id);
        File file = new File(dir);
        if (file.exists() && file.isDirectory()) {
            deleteDir(file);
        }
    }

    public static boolean have(Context context, String id) {
        File data = new File(getFileCachePath(context, id));
        return data.exists();
    }

    public static List<WeiboPublishModel> list(Context context) {
        File fileDir = new File(getFileCachePath(context, null));
        if (fileDir.exists() && fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (files != null && fileDir.length() > 0) {
                List<WeiboPublishModel> models = new ArrayList<>();
                for (File file : files) {
                    String id = getIdByFile(file);
                    WeiboPublishModel model = get(context, id);
                    if (model != null)
                        models.add(model);
                }
                return models;
            }
        }
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean save(Context context, String id, WeiboPublishModel model) {
        final String path = getFileCachePath(context, id);
        log("save", path);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File file = new File(path);
            if (file.exists())
                file.delete();
            file.createNewFile();
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(model);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(oos, fos);
        }
    }

    public static WeiboPublishModel get(Context context, String id) {
        if (!have(context, id))
            return null;

        final String path = getFileCachePath(context, id);
        log("get", path);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            return (WeiboPublishModel) ois.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (InvalidClassException e) {
            e.printStackTrace();
            remove(context, id);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(ois, fis);
        }
        return null;
    }

    public static boolean remove(Context context, String id) {
        // To clear the images cache
        removeImages(context, id);

        File data = new File(getFileCachePath(context, id));
        log("remove", data.getAbsolutePath());
        return !data.exists() || data.delete();
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String c : children) {
                deleteDir(new File(dir, c));
            }
        }
        log("delete", dir.getAbsolutePath());
        dir.delete();
    }

    private static void log(String action, String msg) {
        Log.e(TAG, String.format("%s:%s", action, msg));
    }
}