package com.angelmxc.test.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ActivityLista extends Activity {
    public static String TAG = "TestActivity";
    public static final String direccionJson="https://flavioruben.herokuapp.com/data.json";
    public static List<Animal> listaDeAnimales=new ArrayList();

    public ListView listview;
    RequestManager rm=new RequestManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        listview=findViewById(R.id.lista);
        String[] items={"Cargando."};
        ArrayList<String> listItems=new ArrayList<String>(Arrays.asList(items));
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=parent.getItemAtPosition(position);
                Toast.makeText(ActivityLista.this,"vida="+ObtenerVida(o.toString()),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.w(TAG,"onResume");

        rm.getAnimals(new RequestCallback() {
            @Override
            public void onSuccess(List<Animal> listaDeAnimales){
                ActualizarLista(listaDeAnimales);
            }
            @Override
            public void onError(String str_error){
                Toast.makeText(ActivityLista.this,str_error,Toast.LENGTH_SHORT).show();
            }
        },direccionJson);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    protected String ObtenerVida(String nombre_a_buscar) {
        String vida=new String("No encontrado.");
        for (int i = 0; i < this.listaDeAnimales.size(); i++){
            if(listaDeAnimales.get(i).nombre.compareTo(nombre_a_buscar)==0) {
                vida=listaDeAnimales.get(i).vida;
            }
        }
        return vida;
    }

    void ActualizarLista(List<Animal> listaDeAnimales){
        this.listaDeAnimales=listaDeAnimales;
            int length = listaDeAnimales.size();
            List<String> listContents = new ArrayList<String>(length);
            for (int i = 0; i < length; i++){
                listContents.add(listaDeAnimales.get(i).nombre);
            }

            ListView myListView = (ListView) findViewById(R.id.lista);
            myListView.setAdapter(new ArrayAdapter<String>(ActivityLista.this, android.R.layout.simple_list_item_1, listContents));
    }

    /*
    public String getJSON(String direccion, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(direccion);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (SocketTimeoutException exception) {
            Toast.makeText(ActivityLista.this,"Intentar mas tarde.",Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
*/
}


