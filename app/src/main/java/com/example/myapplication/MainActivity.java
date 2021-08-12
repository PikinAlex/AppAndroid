package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Model.Prospectos;
import com.example.myapplication.Utils.Apis;
import com.example.myapplication.Utils.ProspectosSerivce;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ProspectosSerivce prospectosSerivce;
    List<Prospectos>listProspectos=new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView=(ListView)findViewById(R.id.listView);

        listProspectos();

        FloatingActionButton fab = findViewById(R.id.fabe);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AñadirProspecto.class);
                intent.putExtra("ID","");
                intent.putExtra("NOMBRE","");
                intent.putExtra("APELLIDO PATERNO","");
                intent.putExtra("APELLIDO MATERNO","");
                intent.putExtra("CALLE","");
                intent.putExtra("COLONIA","");
                intent.putExtra("NUMERO","");
                intent.putExtra("CODIGO POSTAL","");
                intent.putExtra("TELEFONO","");
                intent.putExtra("RFC","");
                intent.putExtra("STATUS","");
                intent.putExtra("OBSERVACIONES", "");
                startActivity(intent);
            }
        });

    }

    public void listProspectos(){
        prospectosSerivce= Apis.getPropectosSerivice();
        Call<List<Prospectos>>call=prospectosSerivce.getProspectos();
        call.enqueue(new Callback<List<Prospectos>>() {
            @Override
            public void onResponse(Call<List<Prospectos>> call, Response<List<Prospectos>> response) {
                if(response.isSuccessful()) {
                    listProspectos = response.body();
                    listView.setAdapter(new PropectosAdapter(MainActivity.this,R.layout.content_main,listProspectos));
                }
            }

            @Override
            public void onFailure(Call<List<Prospectos>> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }


}