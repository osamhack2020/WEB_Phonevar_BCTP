package kr.osam.phonevar.util;

import android.content.Context;

import kr.osam.core.sharedpreference.BaseSharedPreferencesHelper;

public class TokenSharedPreference extends BaseSharedPreferencesHelper {
    public static final String TAG = "TokenSharedPreference";
    private static volatile TokenSharedPreference instance = null;
    public final String TOKEN = "TOKEN";
    public final String ID = "ID";
    private final String TOKEN_SHARED_PREFEREMCE = "kr.osam.phonever.token";


    public TokenSharedPreference() {
        sharedPreferences = null;
    }

    public static TokenSharedPreference getInstance() {
        if (instance == null) {
            synchronized (TokenSharedPreference.class) {
                instance = new TokenSharedPreference();
            }
        }
        return instance;
    }

    public void init(Context context) {
        super.init(context, context.getSharedPreferences(TOKEN_SHARED_PREFEREMCE, Context.MODE_PRIVATE));
    }

    public void saveToken(String accessToken) {
        putString(TOKEN, accessToken);
    }

    public String getToken() {
        return getString(TOKEN, "");
    }

    public void saveID(int id) {
        putInt(ID, id);
    }

    public int getID() {
        return getInt(ID, -1);
    }
}
