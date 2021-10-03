package com.snaptrap.libs;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import androidx.loader.content.CursorLoader;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.snaptrap.MainApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Settings extends ReactContextBaseJavaModule {

    public Settings(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "SnapSettings";
    }

    static Gson gson = new Gson();
    static String settingsLocation = String.format("%s/SnapTrap", Environment.getExternalStorageDirectory());
    static File jsonSettings = new File(settingsLocation + "/settings.json");
    static File homeDir = new File(settingsLocation);

    static public Boolean verifySettingsFile() {

        if (jsonSettings.exists()) {
            return true;
        }

        if (!homeDir.exists()) {
            homeDir.mkdir();
            homeDir.setReadable(true);
            homeDir.setWritable(true);
        }

        try {
            Map<String, Object> settings = new HashMap<>();
            settings.put("saveSnaps", true);
            settings.put("disableScreenshot", true);
            settings.put("saveLocation", settingsLocation);

            Writer writer = new FileWriter(jsonSettings);
            gson.toJson(settings, writer);
            writer.close();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }

        return true;
    }

    public static String getSavingPath() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonSettings));
            JsonObject json = gson.fromJson(bufferedReader, JsonObject.class);
            return json.get("saveLocation").getAsString();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return settingsLocation;
        }
    };

    public static boolean setSavingPath(String path) {
        try {
            Map<String, Object> settings = new HashMap<>();
            settings = gson.fromJson(new FileReader(jsonSettings), settings.getClass());
            settings.remove("saveLocation");
            settings.put("saveLocation", new URI(path).getPath());

            Writer writer = new FileWriter(jsonSettings);
            gson.toJson(settings, writer);
            writer.close();
            return true;
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    };

    public static boolean getSaveSnaps() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonSettings));
            JsonObject json = gson.fromJson(bufferedReader, JsonObject.class);
            return json.get("saveSnaps").getAsBoolean();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    public static boolean setSaveSnaps(boolean bool) {
        try {
            Map<String, Object> settings = new HashMap<>();
            settings = gson.fromJson(new FileReader(jsonSettings), settings.getClass());
            settings.remove("saveSnaps");
            settings.put("saveSnaps", bool);

            Writer writer = new FileWriter(jsonSettings);
            gson.toJson(settings, writer);
            writer.close();
            return true;
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    public static boolean getDisableScreenshot() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonSettings));
            JsonObject json = gson.fromJson(bufferedReader, JsonObject.class);
            return json.get("disableScreenshot").getAsBoolean();
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    public static boolean setDisableScreenshot(boolean bool) {
        try {
            Map<String, Object> settings = new HashMap<>();
            settings = gson.fromJson(new FileReader(jsonSettings), settings.getClass());
            settings.remove("disableScreenshot");
            settings.put("disableScreenshot", bool);

            Writer writer = new FileWriter(jsonSettings);
            gson.toJson(settings, writer);
            writer.close();
            return true;
        } catch (Exception e) {
            Logging.log("ERROR " + e.getMessage());
            return false;
        }
    }

    @ReactMethod
    public static void exportVerifySettingsFile(Promise promise) {
        promise.resolve(verifySettingsFile());
    }

    @ReactMethod
    public static void exportGetSavingPath(Promise promise) {
        promise.resolve(getSavingPath());
    }

    @ReactMethod
    public static void exportSetSavingPath(String path, Promise promise) {
        promise.resolve(setSavingPath(path));
    }

    @ReactMethod
    public static void exportSetSaveSnaps(Boolean bool, Promise promise) {
        promise.resolve(setSaveSnaps(bool));
    }

    @ReactMethod
    public static void exportGetSaveSnaps(Promise promise) {
        promise.resolve(getSaveSnaps());
    }

    @ReactMethod
    public static void exportSetDisableScreenshot(Boolean bool, Promise promise) {
        promise.resolve(setDisableScreenshot(bool));
    }

    @ReactMethod
    public static void exportGetDisableScreenshot(Promise promise) {
        promise.resolve(getDisableScreenshot());
    }


}
