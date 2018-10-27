package com.angelmxc.test.test;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestManager {
    public static RequestCallback rq;
    public static String json;

    public void getAnimals(RequestCallback rq,String direccion){
        RequestManager.rq=rq;
        new RequestManager.HttpTask().execute(direccion);
    }

    private class HttpTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            Response responses = null;
            try {
                responses = client.newCall(request).execute();
                json=responses.body().string();
                if(json!=null) {
                    return !json.isEmpty();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean  result) {
            List<Animal> listaDeAnimales=new ArrayList();
            if(result){
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    int length = jsonArray.length();
                    List<String> listContents = new ArrayList<String>(length);
                    for (int i = 0; i < length; i++){
                        Animal animal=new Animal();
                        JSONObject jsonObj=new JSONObject(jsonArray.getString(i));
                        Object name=jsonObj.get("name");
                        animal.nombre=name.toString();
                        Object vida=jsonObj.get("life");
                        animal.vida=vida.toString();
                        listaDeAnimales.add(animal);
                    }
                    rq.onSuccess(listaDeAnimales);
                } catch (JSONException e) {
                    e.printStackTrace();
                    rq.onError("Error parseando el json");
                }
            }else{
                rq.onError("Error al obtener json");
            }
        }
    }
}
