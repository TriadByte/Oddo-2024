package com.crimesnap.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyPreferences {

    /* FILE : MyPreferences.java */

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_LINK = "link";

    private static final String ACTIVITY_LOG = "activity_log";

    private static final String KEY_WEBHOOK_URL = "webhook_url";
    private static final String KEY_USERNAME = "username";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static String getWebhookUrl() {
        return KEY_WEBHOOK_URL;
    }

    public static void saveWebhookUrl(String url) {
        editor.putString(KEY_WEBHOOK_URL, url);
        editor.apply();
    }

    public static void printKey(String key) {
        Log.d("MyPreferences", key + ": " + sharedPreferences.getString(key, ""));
    }

    public static void saveLink(String link) {
        editor.putString(KEY_LINK, link);
        editor.apply();
    }

    public static String getLink() {
        return sharedPreferences.getString(KEY_LINK, "");
    }

    public static void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public static String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public static void saveActivityLog(String log) {
        editor.putString(ACTIVITY_LOG, log);
        editor.apply();
    }


    public static String getActivityLog() {
        return sharedPreferences.getString(ACTIVITY_LOG, "");
    }

    public static void clearActivityLog() {
        editor.putString(ACTIVITY_LOG, "");
        editor.apply();
    }

    public static void addActivityLogInJsonArray(JSONObject logData){
        String activityLog = getActivityLog();

        if(activityLog.isEmpty()){
            activityLog = "{\"activity\":[]}";
        }

        try {
            JSONObject jsonObject = new JSONObject(activityLog);

            JSONArray jsonArray = jsonObject.getJSONArray("activity");

            jsonArray.put(logData);

            jsonObject.put("activity", jsonArray);

            saveActivityLog(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}