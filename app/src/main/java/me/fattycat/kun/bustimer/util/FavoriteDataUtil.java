package me.fattycat.kun.bustimer.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.fattycat.kun.bustimer.model.LineInfoSerializable;

/**
 * Author: Kelvinkun
 * Date: 16/7/21
 */

public class FavoriteDataUtil {
    private static String FILE_NAME = "BusFavorite";

    public static void saveFavoriteLine(Context context, LineInfoSerializable lineInfo) {
        List<LineInfoSerializable> originalLineInfoList = loadAllFavoriteLine(context);
        if (originalLineInfoList == null) {
            originalLineInfoList = new ArrayList<>();
        }
        for (LineInfoSerializable savedLineInfoItem : originalLineInfoList) {
            if (TextUtils.equals(savedLineInfoItem.getRunPathId(), lineInfo.getRunPathId())
                    && TextUtils.equals(savedLineInfoItem.getFlag(), lineInfo.getFlag())) {
                return;
            }
        }
        originalLineInfoList.add(lineInfo);
        saveAllFavoriteLine(context, originalLineInfoList);
    }

    private static void saveAllFavoriteLine(Context context, List<LineInfoSerializable> toSaveLineList) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(toSaveLineList);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<LineInfoSerializable> loadAllFavoriteLine(Context context) {
        FileInputStream fis;
        ObjectInputStream ois;
        List<LineInfoSerializable> lineInfoList = null;

        try {
            fis = context.openFileInput(FILE_NAME);
            ois = new ObjectInputStream(fis);
            lineInfoList = (List<LineInfoSerializable>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineInfoList;
    }

    public static void deleteFavoriteLine(Context context, LineInfoSerializable lineToDelete) {
        List<LineInfoSerializable> originalLineInfoList = loadAllFavoriteLine(context);
        if (originalLineInfoList == null) {
            return;
        }
        Iterator<LineInfoSerializable> iterator = originalLineInfoList.iterator();
        while (iterator.hasNext()) {
            LineInfoSerializable savedLineInfo = iterator.next();
            if (TextUtils.equals(savedLineInfo.getRunPathId(), lineToDelete.getRunPathId())
                    && TextUtils.equals(savedLineInfo.getFlag(), lineToDelete.getFlag())) {
                iterator.remove();
                break;
            }
        }
        saveAllFavoriteLine(context, originalLineInfoList);
    }
}
