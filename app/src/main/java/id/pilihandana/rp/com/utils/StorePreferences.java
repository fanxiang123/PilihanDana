package id.pilihandana.rp.com.utils;
import android.content.Context;

public class StorePreferences extends PreferencesBase {

    public static final String STORE_REFERENCE_FILE_NAME = "config";

    private static volatile StorePreferences sInstance;

    public static final String KEY_GUID = "key_guid";

    public static final String KEY_GAID = "key_gaid";

    public static final String KEY_USERINFO = "key_userinfo";

    public static final String KEY_REF = "key_ref";




    private StorePreferences(Context context) {
        super(context, STORE_REFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static StorePreferences getInstance(Context context) {
        if (sInstance == null) {
            synchronized (StorePreferences.class) {
                if (sInstance == null) {
                    sInstance = new StorePreferences(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public String getGaid() {
        return getStringValue(KEY_GAID, "");
    }

    public void setGaid(String gaid) {
        setStringValue(KEY_GAID, gaid);
    }

    public String getUserInfo() {
        return getStringValue(KEY_USERINFO, "");
    }

    public void setUserInfo(String userinfo) {
        setStringValue(KEY_USERINFO, userinfo);
    }


    public String getGuid() {
        return getStringValue(KEY_GUID, "");
    }

    public void setGuid(String guid) {
        setStringValue(KEY_GUID, guid);
    }


    public String getRef() {
        return getStringValue(KEY_REF, "");
    }

    public void setRef(String ref) {
        setStringValue(KEY_REF, ref);
    }



}