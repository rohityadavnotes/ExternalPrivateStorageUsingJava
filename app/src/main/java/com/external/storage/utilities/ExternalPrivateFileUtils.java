package com.external.storage.utilities;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;

public class ExternalPrivateFileUtils {

    public static final String TAG = ExternalPrivateFileUtils.class.getSimpleName();

    private ExternalPrivateFileUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Create directory
     *
     * @param context application context
     * @param directoryName
     * @return /storage/emulated/0/Android/com.external.storage/files/AppName
     */
    public static File createDirectory(@NonNull Context context, String directoryName) {

        File directory = context.getExternalFilesDir(directoryName);

        if (directory != null)
        {
            if (!directory.exists())
            {
                if (!directory.mkdirs())
                {
                    Log.e(TAG, "Oops! Create Directory Failed....");
                }
                else
                {
                    Log.e(TAG, "Directory Create Successful....");
                }
            }
            else
            {
                Log.e(TAG, "Directory Already Exit....");
            }
        }
        return directory;
    }

    /**
     * Create file
     *
     * @param directoryWhereCreateFile storage directory where create file
     * @param fileExtension file extension without dot
     * @param fileName file name without extension
     * @return /storage/emulated/0/Android/com.external.storage/files/AppName/externalFile.txt
     * @throws IOException
     */
    public static File createFile(File directoryWhereCreateFile, String fileExtension, String fileName) {
        File file        = new File(directoryWhereCreateFile, fileName+"."+fileExtension);

        if (!file.exists())
        {
            try {
                if (!file.createNewFile())
                {
                    Log.e(TAG, "Oops! Create File Failed....");
                    return null;
                }
                else
                {
                    Log.e(TAG, "File Create Successful....");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.e(TAG, "File Already Exit....");
        }

        return file;
    }

    /**
     * Create file
     *
     * @param fileExtension file extension without dot
     * @param fileName file name without extension
     * @return /storage/emulated/0/Android/com.external.storage/cache/externalFile.txt
     * @throws IOException
     */
    public static File createFileInsideCacheDirectory(@NonNull Context context, String fileExtension, String fileName) {
        File file        = new File(context.getExternalCacheDir(), fileName+"."+fileExtension);

        if (!file.exists())
        {
            try {
                if (!file.createNewFile())
                {
                    Log.e(TAG, "Oops! Create File Failed....");
                    return null;
                }
                else
                {
                    Log.e(TAG, "File Create Successful....");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.e(TAG, "File Already Exit....");
        }

        return file;
    }
}
