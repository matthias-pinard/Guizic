package com.example.matthias.guizic.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.matthias.guizic.Database.Parcours;
import com.example.matthias.guizic.Database.SecretZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonReaderUtils {
    Context mContext;
    public JsonReaderUtils(Context context) {
        mContext = context;
    }

    public String readJsonFile() {
        BufferedReader reader = null;
        String file = "";
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream ims = assetManager.open("parcours.json");
            reader = new BufferedReader(new InputStreamReader(ims));
            String line;

            while((line = reader.readLine()) != null ){
                file += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public List<SecretZone> parseJsonFileZone() {
        String json = readJsonFile();
        List<SecretZone> lZone = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("parcours");

            for(int j = 0; j < jsonArray.length(); j++) {
                JSONObject parcour = jsonArray.getJSONObject(j);
                JSONArray arrayZones = parcour.getJSONArray("parcour");
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject zone = jsonArray.getJSONObject(i);
                    double latitude = zone.getDouble("latitude");
                    double longitude = zone.getDouble("longitude");
                    double sensibilite = zone.getDouble("sensibilite");
                    String realName = zone.getString("realName");
                    String secretName = zone.getString("secretName");
                    SecretZone secretZone = new SecretZone(longitude, latitude, sensibilite, realName, secretName);
                    lZone.add(secretZone);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lZone;
    }

    public List<Parcours> parseJsonFileParcours() {
        String json = readJsonFile();
        List<Parcours> lParcours = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("parcours");
            String name = jsonObject.getString("name");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject parcours = jsonArray.getJSONObject(i);

               // lParcours.add(parcours);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lParcours;
    }
}
