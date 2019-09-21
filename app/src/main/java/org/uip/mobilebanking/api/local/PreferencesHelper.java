package org.uip.mobilebanking.api.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import org.uip.mobilebanking.api.BaseURL;
import org.uip.mobilebanking.api.SelfServiceInterceptor;
import org.uip.mobilebanking.injection.ApplicationContext;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Vishwajeet
 * @since 07/06/16
 */

@Singleton
public class PreferencesHelper {
    private static final String USER_ID = "preferences_user_id";
    private static final String TOKEN = "preferences_token";
    private static final String CLIENT_ID = "preferences_client";
    private static final String OFFICE_NAME = "preferences_office_name";
    private static final String USER_NAME = "preferences_user_name";
    private static final String PASSCODE = "preferences_passcode";
    private static final String OVERVIEW_STATE = "preferences_overview_state";
    private static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    private static final String GCM_TOKEN = "gcm_token";
    private static final String TENANT = "preferences_base_tenant";
    private static final String BASE_URL = "preferences_base_url_key";
    private static final String PROFILE_IMAGE = "preferences_profile_image";
    private static final String MOBILE_NO = "preferences_mobile_no";
    private static final String EXTERNAL_ID = "preferences_external_id";
    private static final String CRB_CHECKED = "preferences_crb_checked";


    private SharedPreferences sharedPreferences;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //prevent deletion of url and tenant
        for (Map.Entry<String, ?> entry: sharedPreferences.getAll().entrySet()) {
            if (!(entry.getKey().equals(BASE_URL) || entry.getKey().equals(TENANT))) {
                editor.remove(entry.getKey());
            }
        }
        editor.apply();
    }

    public int getInt(String preferenceKey, int preferenceDefaultValue) {
        return sharedPreferences.getInt(preferenceKey, preferenceDefaultValue);
    }

    public void putInt(String preferenceKey, int preferenceValue) {
        sharedPreferences.edit().putInt(preferenceKey, preferenceValue).apply();
    }

    public long getLong(String preferenceKey, long preferenceDefaultValue) {
        return sharedPreferences.getLong(preferenceKey, preferenceDefaultValue);
    }

    public void putLong(String preferenceKey, long preferenceValue) {
        sharedPreferences.edit().putLong(preferenceKey, preferenceValue).apply();
    }

    public String getString(String preferenceKey, String preferenceDefaultValue) {
        return sharedPreferences.getString(preferenceKey, preferenceDefaultValue);
    }

    public void putString(String preferenceKey, String preferenceValue) {
        sharedPreferences.edit().putString(preferenceKey, preferenceValue).apply();
    }

    public void putBoolean(String preferenceKey, boolean preferenceValue) {
        sharedPreferences.edit().putBoolean(preferenceKey, preferenceValue).apply();
    }

    public boolean getBoolean(String preferenceKey, boolean preferenceDefaultValue) {
        return sharedPreferences.getBoolean(preferenceKey, preferenceDefaultValue);
    }

    public void saveToken(String token) {
        putString(TOKEN, token);
    }

    public void clearToken() {
        putString(TOKEN, "");
    }

    public String getToken() {
        return getString(TOKEN, "");
    }

    public boolean isAuthenticated() {
        return !TextUtils.isEmpty(getToken());
    }

    public long getUserId() {
        return getLong(USER_ID, -1);
    }

    public void setUserId(long id) {
        putLong(USER_ID, id);
    }

    public String getTenant() {
        return getString(TENANT, SelfServiceInterceptor.DEFAULT_TENANT);
    }

    public void setPasscode(String passcode) {
        putString(PASSCODE, passcode);
    }

    public String getPasscode() {
        return getString(PASSCODE, "");
    }

    public void setCrbChecked(String crb) {
        putString(CRB_CHECKED, crb);
    }

    public String getCrbChecked() {
        return getString(CRB_CHECKED, "");
    }

    public void setClientId(long clientId) {
        putLong(CLIENT_ID, clientId);
    }

    public long getClientId() {
        return getLong(CLIENT_ID, -1);
    }

    public void setOfficeName(String officeName) {
        putString(OFFICE_NAME, officeName);
    }

    public String getUserName() {
        return getString(USER_NAME, "");
    }

    public void setUserName(String userName) {
        putString(USER_NAME, userName);
    }
    public String getMobileNo() {
        return getString(MOBILE_NO, "");
    }

    public void setMobileNo(String mobileNo) {
        putString(MOBILE_NO, mobileNo);
    }
    public String getExternalId() {
        return getString(EXTERNAL_ID, "");
    }

    public void setExternalId(String externalId) {
        putString(EXTERNAL_ID, externalId);
    }

    public String getOfficeName() {
        return getString(OFFICE_NAME, "");
    }

    public void setOverviewState(boolean state) {
        putBoolean(OVERVIEW_STATE, state);
    }

    public boolean overviewState() {
        return getBoolean(OVERVIEW_STATE, true);
    }

    public void saveGcmToken(String token) {
        putString(GCM_TOKEN, token);
    }

    public String getUserProfileImage() {
        return getString(PROFILE_IMAGE, null);
    }

    public void setUserProfileImage(String image) {
        putString(PROFILE_IMAGE, image);
    }

    public String getGcmToken() {
        return getString(GCM_TOKEN, "");
    }

    public void setSentTokenToServer(boolean sentTokenToServer) {
        putBoolean(SENT_TOKEN_TO_SERVER, sentTokenToServer);
    }

    public boolean sentTokenToServerState() {
        return getBoolean(SENT_TOKEN_TO_SERVER, false);
    }

    public void updateConfiguration(String baseUrl, String tenant) {
        sharedPreferences.edit()
                .putString(BASE_URL, baseUrl)
                .putString(TENANT, tenant)
                .apply();
    }

    public String getBaseUrl() {
        return getString(BASE_URL, new BaseURL().getDefaultBaseUrl());
    }
}