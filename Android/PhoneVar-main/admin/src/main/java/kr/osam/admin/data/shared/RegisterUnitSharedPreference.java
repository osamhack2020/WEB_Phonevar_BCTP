package kr.osam.admin.data.shared;

import android.content.Context;

import kr.osam.core.sharedpreference.BaseSharedPreferencesHelper;

public class RegisterUnitSharedPreference extends BaseSharedPreferencesHelper {
    public static final String TAG = "TokenSharedPreference";
    private static volatile RegisterUnitSharedPreference instance = null;
    public final String UNIT_ID = "ID";
    public final String UNIT_NAME = "UNIT";
    private final String TOKEN_SHARED_PREFEREMCE = "kr.osam.admin.unit";


    public RegisterUnitSharedPreference() {
        sharedPreferences = null;
    }

    public static RegisterUnitSharedPreference getInstance() {
        if (instance == null) {
            synchronized (RegisterUnitSharedPreference.class) {
                instance = new RegisterUnitSharedPreference();
            }
        }
        return instance;
    }

    public void init(Context context) {
        super.init(context, context.getSharedPreferences(TOKEN_SHARED_PREFEREMCE, Context.MODE_PRIVATE));
    }

    public void saveUnitName(String unitName) {
        putString(UNIT_NAME, unitName);
    }

    public String getUnitName() {
        return getString(UNIT_NAME, "");
    }

    public void saveUnitID(String unitID) {
        putString(UNIT_ID, unitID);
    }

    public String getUnitID() {
        return getString(UNIT_ID, "");
    }

    public void removeAll(){
        remove(UNIT_NAME);
        remove(UNIT_ID);
    }
}
