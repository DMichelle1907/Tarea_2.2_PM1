package com.example.tarea22_pm1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tarea22_pm1.Settings.Rest_api;

import org.json.JSONObject;

public class Activity_list extends AppCompatActivity {

    Rest_api rest_api=new Rest_api();
    EditText txt_search;
    Button  btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

         txt_search=findViewById(R.id.txt_search);
         btn_search=findViewById(R.id.btnBuscar);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_search.getText().toString().length()!=0){
                    search(txt_search.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Campo vacio, por favor ingrese un ID", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void search(String id){
        Rest_api rest_api=new Rest_api();
        String url;
        url=rest_api.url+"/"+id;

        RequestQueue queue=Volley.newRequestQueue(this);//queue=cola

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // JSON data retrieved successfully
                        try{
                            Log.e("w",""+response.length());
                            String[] datas=new String[1];

                            for (int i=0; i<1; i++) {
                                String id=response.getString("id");
                                String title=response.getString("title");
                                String body=response.getString("body");
                                String data=id+" - "+title+" - "+body;
                                datas[i]=data;
                            }

                            ListView listView=findViewById(R.id.list_buscar);
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(Activity_list.this, R.layout.list_item_layout, datas);//adapter=adaptador
                            listView.setAdapter(adapter);

                        } catch (Exception e) {
                            Log.e("e",""+e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error occurred while making the request
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}