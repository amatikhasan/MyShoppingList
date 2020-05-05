package au.edu.utas.ssaadat.assignment2.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;
    final String prefName = "MySharedPref";
    final String keyImage = "image";


    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean setImage(String image) {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyImage, image);
        editor.apply();
        return true;
    }

    public boolean clear() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getImage() {
        SharedPreferences sp = mCtx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sp.getString(keyImage, null);
    }

}
