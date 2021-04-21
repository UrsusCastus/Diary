package com.example.diary.data;

import android.content.Context;

import com.example.diary.Event;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataReceiver {

    private final String jsonFileName = "to_do_list.json";
    private Context mContext;
    private List<Event> mEventList = new ArrayList<Event>();

    private DataReceiver(Context context) {
        mContext = context;
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

    private void writeDataToJsonFileOnInternalStorage(Context context) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(context
                    .openFileOutput(jsonFileName, Context.MODE_PRIVATE)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addEvent(Event event) {
        mEventList.add(event);
    }
}
