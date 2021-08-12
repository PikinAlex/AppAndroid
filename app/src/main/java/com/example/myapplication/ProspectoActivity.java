package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Prospectos;
import com.example.myapplication.Utils.Apis;
import com.example.myapplication.Utils.ProspectosSerivce;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProspectoActivity extends AppCompatActivity {

    ProspectosSerivce service;
    EditText editText;
    TextView textView;
    Button button;
    int flag=0;
    String id, nombre, apellido_p, apellido_m, calle, colonia, numero, codigo_postal, telefono, rfc, observaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prospectos_layout);
        editText=(EditText)findViewById(R.id.txtdocumentos);
        button=(Button)findViewById(R.id.button_add);
        final Spinner spinner= findViewById(R.id.status);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.valuesEditarProspecto, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);


        final TextView txtid=(TextView)findViewById(R.id.txtid);
        final TextView txtnombre=(TextView)findViewById(R.id.txtnombre);
        final TextView txtapellido_p=(TextView)findViewById(R.id.txtapellido_p);
        final TextView txtapellido_m=(TextView)findViewById(R.id.txtapellido_m);
        final TextView txtcalle=(TextView)findViewById(R.id.txtcalle);
        final TextView txtcolonia=(TextView)findViewById(R.id.txtcolonia);
        final TextView txtnumero=(TextView)findViewById(R.id.txtnumero);
        final TextView txtcodigopostal=(TextView)findViewById(R.id.txtcodigo_postal);
        final TextView txttelefono=(TextView)findViewById(R.id.txttelefono);
        final TextView txtrfc=(TextView)findViewById(R.id.txtrfc);
        final EditText txtobservaciones=(EditText)findViewById(R.id.txtobservaciones);

        Bundle bundle=getIntent().getExtras();
        id = bundle.getString("ID");
        nombre=bundle.getString("NOMBRE");
        apellido_p=bundle.getString("APELLIDO PATERNO");
        apellido_m=bundle.getString("APELLIDO MATERNO");
        calle=bundle.getString("CALLE");
        colonia=bundle.getString("COLONIA");
        numero=bundle.getString("NUMERO");
        codigo_postal=bundle.getString("CODIGO POSTAL");
        telefono=bundle.getString("TELEFONO");
        rfc=bundle.getString("RFC");
        observaciones=bundle.getString("OBSERVACIONES");


        Button btnSave=(Button)findViewById(R.id.btnGuardar);
        Button btnVolver=(Button)findViewById(R.id.btnVolver);
        //Button btnEliminar=(Button)findViewById(R.id.btnEliminar);

        txtid.setText(id);
        txtnombre.setText(nombre);
        txtapellido_p.setText(apellido_p);
        txtapellido_m.setText(apellido_m);
        txtcalle.setText(calle);
        txtcolonia.setText(colonia);
        txtnumero.setText(numero);
        txtcodigopostal.setText(codigo_postal);
        txttelefono.setText(telefono);
        txtrfc.setText(rfc);
        txtobservaciones.setText(observaciones);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prospectos p=new Prospectos();
                p.setNombre(txtnombre.getText().toString());
                p.setApellido_p(txtapellido_p.getText().toString());
                p.setApellido_m(txtapellido_m.getText().toString());
                p.setCalle(txtcalle.getText().toString());
                p.setColonia(txtcolonia.getText().toString());
                p.setNumero(txtnumero.getText().toString());
                p.setCodigo_postal(txtcodigopostal.getText().toString());
                p.setTelefono(txttelefono.getText().toString());
                p.setRfc(txtrfc.getText().toString());
                p.setStatus(spinner.getSelectedItem().toString());
                p.setObservaciones(txtobservaciones.getText().toString());
                updateProspecto(p, Integer.valueOf(id));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ProspectoActivity.this, VerDocumento.class);
                intent.putExtra("ID",id );
                intent.putExtra("NOMBRE",nombre);
                intent.putExtra("APELLIDO PATERNO",apellido_p);
                intent.putExtra("APELLIDO MATERNO",apellido_m);
                intent.putExtra("CALLE",calle);
                intent.putExtra("COLONIA",colonia);
                intent.putExtra("NUMERO",numero);
                intent.putExtra("CODIGO POSTAL",codigo_postal);
                intent.putExtra("TELEFONO",telefono);
                intent.putExtra("RFC",rfc);
                intent.putExtra("OBSERVACIONES", observaciones);
                startActivity(intent);

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ALGO");
                new AlertDialog.Builder(ProspectoActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Salir?")
                        .setMessage("Se borrarán los datos no guardados si regresa a la pantalla anterior")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent =new Intent(ProspectoActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

    }
    public void updateProspecto(Prospectos p, int id){
        if(p.getStatus().equals("Rechazado")){
            if(!p.getObservaciones().isEmpty()){
                service= Apis.getPropectosSerivice();
                Call<Prospectos>call=service.updateProspecto(p,id);

                call.enqueue(new Callback<Prospectos>() {
                    @Override
                    public void onResponse(Call<Prospectos> call, Response<Prospectos> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ProspectoActivity.this,"Se Actualizó conéxito",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Prospectos> call, Throwable t) {
                        Log.e("Error:",t.getMessage());
                    }
                });
                Intent intent =new Intent(ProspectoActivity.this,MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(ProspectoActivity.this, "Falta Añadir Observaciones", Toast.LENGTH_LONG).show();
            }
        }else {
            service= Apis.getPropectosSerivice();
            Call<Prospectos>call=service.updateProspecto(p,id);

            call.enqueue(new Callback<Prospectos>() {
                @Override
                public void onResponse(Call<Prospectos> call, Response<Prospectos> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(ProspectoActivity.this,"Se Actualizó conéxito",Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Prospectos> call, Throwable t) {
                    Log.e("Error:",t.getMessage());
                }
            });
            Intent intent =new Intent(ProspectoActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ProspectoActivity.this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("¿Salir?")
                .setMessage("Se borrarán los datos si regresa a la pantalla anterior")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(ProspectoActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
