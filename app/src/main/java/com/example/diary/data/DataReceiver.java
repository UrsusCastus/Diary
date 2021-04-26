package com.example.diary.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.diary.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataReceiver {

    private static final String LOG_EVENT_CREATE_ACTIVITY = "LogDataReceiver";

    private final String jsonFileName = "to_do_list.json";

    private static DataReceiver sDataReceiverInstance;

    private Context mContext;
    private List<Event> mEventList = new ArrayList<Event>();

    private DataReceiver() {

    }

    private DataReceiver(Context context) {
        mContext = context;
    }

    public static DataReceiver getInstanceDataReceiver() {
        if (sDataReceiverInstance == null) {
            sDataReceiverInstance = new DataReceiver();
        }
        return sDataReceiverInstance;
    }

    private void getData(Context context) {

    }

    private String getDataFromJsonFileToAssets(Context context) {
        String jsonData = null;
        try {
            InputStream inputStream = context.getAssets().open(jsonFileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonData = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public JSONObject createJsonObject(Event event) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", event.getTitle());
            jsonObject.put("start_date", event.getTimeStart());
            jsonObject.put("finish_date", event.getTimeEnd());
            jsonObject.put("description", event.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return jsonObject;
    }

    public void writeDataToJsonFileOnInternalStorage(Context context, JSONObject jsonObject) {
        Log.d(LOG_EVENT_CREATE_ACTIVITY, "writeDataToJsonFileOnInternalStorage run");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(context
                    .openFileOutput(jsonFileName, Context.MODE_PRIVATE)));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.write('\n');
            Log.d(LOG_EVENT_CREATE_ACTIVITY, jsonObject.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addEvent(Event event) {
        mEventList.add(event);
    }
}
