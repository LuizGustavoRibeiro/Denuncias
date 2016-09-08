package com.example.luizgustavo.denuncias;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    private ListView lstDenuncias;
    private AdapterDenuncia adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstDenuncias = (ListView) findViewById(R.id.lstDenuncias);
        adapter = new AdapterDenuncia(this, R.layout.denuncia,new ArrayList<JSONObject>());
        lstDenuncias.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncTask<Void, Void, JSONArray>(){

            @Override
            protected JSONArray doInBackground(Void... voids) {
                HttpRequests request = new HttpRequests();
                String resposta = "";
                JSONArray denuncias = null;
                try {
                    resposta = request.denuncias();
                    denuncias = new JSONArray(resposta);
                    for (int i = 0; i < denuncias.length(); i++) {
                        Log.i("TAG", denuncias.getJSONObject(i).getString("id"));
                    }

                }catch (MalformedURLException exception) {
                    Log.e("TAG", exception.getMessage());
                }catch (JSONException exc) {
                    Log.e("TAG", exc.getMessage());
                }

                Log.i("TAG", resposta);
                return denuncias;
            }

            @Override
            protected void onPostExecute(JSONArray denuncias) {
                adapter.clear();
                for (int i = 0; i < denuncias.length(); i++) {
                    try {
                        adapter.add(denuncias.getJSONObject(i));
                    } catch (JSONException ex) {}
                }

            }
        }.execute();
    }
}
