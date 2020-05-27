package id.pilihandana.rp.com.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

public abstract class PreferencesBase {

    protected Context mContext;

    protected SharedPreferences mSharedPreferences;
    private String key;
    private String value;

    protected PreferencesBase(Context context, String name, int mode) {
        mContext = context.getApplicationContext();
        mSharedPreferences = mContext.getSharedPreferences(name, mode);
    }

    public Editor edit() {
        return mSharedPreferences.edit();
    }

    public int getIntValue(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public int getIntValue(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public void setIntValue(String key, int value) {
        if (mSharedPreferences != null) {
            Editor mEditor = mSharedPreferences.edit();
            mEditor.putInt(key, value);
            mEditor.commit();
        }
    }

    public String getStringValue(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public String getStringValue(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public void setStringValue(String key, String value) {
        this.key = key;
        this.value = value;
        if (mSharedPreferences != null) {
            Editor mEditor = mSharedPreferences.edit();
            mEditor.putString(key, value);
            mEditor.commit();
        }
    }

    public long getLongValue(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public long getLongValue(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    public void setLongValue(String key, long value) {
        if (mSharedPreferences != null) {
            Editor mEditor = mSharedPreferences.edit();
            mEditor.putLong(key, value);
            mEditor.commit();
        }
    }

    public boolean getBooleanValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBooleanValue(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public void setBooleanValue(String key, boolean value) {
        if (mSharedPreferences != null) {
            Editor mEditor = mSharedPreferences.edit();
            mEditor.putBoolean(key, value);
            mEditor.commit();
        }
    }

    public void remove(String key) {
        if (mSharedPreferences != null) {
            Editor mEditor = mSharedPreferences.edit();
            mEditor.remove(key);
            mEditor.commit();
        }
    }

    public void clearSharedPreferences() {
        if (mSharedPreferences != null) {
            Editor mEditor = mSharedPreferences.edit();
            mEditor.clear();
            mEditor.commit();
        }
    }

    public Map<String, ?> getAll() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getAll();
        }
        return null;
    }
}
