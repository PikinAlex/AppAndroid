package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Utils.Apis;
import com.example.myapplication.Model.Documento;
import com.example.myapplication.Model.Prospectos;
import com.example.myapplication.R;
import com.example.myapplication.Utils.ProspectosSerivce;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AñadirProspecto extends AppCompatActivity {
    ProspectosSerivce service;
    EditText editText;
    ListView listView;
    Button btnFile;
    int flag = 0;
    Button btnFile3;
    int numeroclick = 1;
    int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    ArrayList<String> documentos;
    ArrayList<Documento> documentoList = new ArrayList<Documento>();
    byte[] byteArray;
    String documentData;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_prospecto);


        try {
            service = Apis.getPropectosSerivice();
            Call<String> call = service.getMax();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String json = response.body();
                    try {
                        JSONObject obj = new JSONObject(json);
                        flag = obj.getInt("value");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("Error:", t.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println(e.toString());
            flag = 1;
        }

        editText = (EditText) findViewById(R.id.txtdocumentos);
        listView = (ListView) findViewById(R.id.listViewId);
        btnFile = (Button) findViewById(R.id.button_add);
        btnFile3 = (Button) findViewById(R.id.button_add3);
        documentos = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(AñadirProspecto.this,
                android.R.layout.simple_list_item_1,
                documentos);
        listView.setAdapter(adapter);
        btnFile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Documento doc = new Documento();
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(AñadirProspecto.this, "Falta nombre documento",
                            Toast.LENGTH_LONG).show();
                } else {
                    doc.setNombre(editText.getText().toString());
                    if (byteArray == null) {
                        Toast.makeText(AñadirProspecto.this, "Falta documento",
                                Toast.LENGTH_LONG).show();
                    } else {
                        doc.setDocumento(documentData);
                        //AGREGAR A LA LISTA
                        RelativeLayout.LayoutParams mParam = new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, (150 * numeroclick));
                        documentos.add(doc.getNombre());
                        adapter.notifyDataSetChanged();
                        listView.setLayoutParams(mParam);
                        numeroclick = numeroclick + 1;

                        doc.setId_prospecto(flag + 1);
                        documentoList.add(doc);
                        byteArray = null;
                        editText.setText("");
                        Toast.makeText(AñadirProspecto.this, "Se Agrego Documento",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,
                                           final int position, long id) {
                new AlertDialog.Builder(AñadirProspecto.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Borrar documento?")
                        .setMessage("Se borrará el documento y no podrá recuperase")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                documentos.remove(position);
                                RelativeLayout.LayoutParams mParam = new RelativeLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT, listView.getHeight() - 150);
                                numeroclick = numeroclick - 1;
                                listView.setLayoutParams(mParam);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });


        final EditText txtnombre = (EditText) findViewById(R.id.txtnombre);
        final EditText txtapellido_p = (EditText) findViewById(R.id.txtapellido_p);
        final EditText txtapellido_m = (EditText) findViewById(R.id.txtapellido_m);
        final EditText txtcalle = (EditText) findViewById(R.id.txtcalle);
        final EditText txtcolonia = (EditText) findViewById(R.id.txtcolonia);
        final EditText txtnumero = (EditText) findViewById(R.id.txtnumero);
        final EditText txtcodigo_postal = (EditText) findViewById(R.id.txtcodigo_postal);
        final EditText txttelefono = (EditText) findViewById(R.id.txttelefono);
        final EditText txtrfc = (EditText) findViewById(R.id.txtrfc);


        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBack();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prospectos p = new Prospectos();
                p.setNombre(txtnombre.getText().toString());
                p.setApellido_p(txtapellido_p.getText().toString());
                p.setApellido_m(txtapellido_m.getText().toString());
                p.setCalle(txtcalle.getText().toString());
                p.setColonia(txtcolonia.getText().toString());
                p.setNumero(txtnumero.getText().toString());
                p.setCodigo_postal(txtcodigo_postal.getText().toString());
                p.setTelefono(txttelefono.getText().toString());
                p.setRfc(txtrfc.getText().toString());
                p.setStatus("Enviado");
                Documento d = new Documento();
                d.setDocumento(documentData);
                d.setNombre(editText.getText().toString());
                addProspecto(p);
            }
        });


    }

    private void selectImage() {
        final CharSequence[] items = {"CÁMARA", "GALERÍA", "CANCELAR"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AñadirProspecto.this);
        builder.setTitle("Agregar imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("CÁMARA")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[i].equals("GALERÍA")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent.createChooser(intent, "Seleccionar imagen"), SELECT_FILE);
                } else if (items[i].equals("CANCELAR")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byteArray = bos.toByteArray();
                documentData = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                try {
                    InputStream stream = getContentResolver().openInputStream(selectedImageUri);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int len = 0;
                    while ((len = stream.read(buffer)) != -1) {
                        bos.write(buffer, 0, len);
                    }
                    byteArray = bos.toByteArray();
                    documentData = Base64.encodeToString(byteArray, Base64.DEFAULT);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void onBack() {
        Intent intent = new Intent(AñadirProspecto.this, MainActivity.class);
        startActivity(intent);

    }

    public void addProspecto(@NotNull Prospectos p) {

        if (!p.getNombre().isEmpty()) {
            if (!p.getApellido_p().isEmpty()) {
                if (!p.getCalle().isEmpty()) {
                    if (!p.getColonia().isEmpty()) {
                        if (!p.getNumero().isEmpty()) {
                            if (!p.getCodigo_postal().isEmpty()) {
                                if (!p.getTelefono().isEmpty()) {
                                    if (!p.getRfc().isEmpty()) {
                                        if (documentoList.size() > 0) {
                                            service = Apis.getPropectosSerivice();
                                            Call<String> call = service.addProspecto(p);
                                            call.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    String json = response.body();
                                                    try {
                                                        JSONObject obj = new JSONObject(json);
                                                        flag = obj.getInt("value");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    if (flag == 0) {
                                                        Toast.makeText(AñadirProspecto.this, "No se agrego, ERROR", Toast.LENGTH_LONG).show();
                                                    } else if (flag == 1) {

                                                        for (int x = 0; x < documentoList.size(); x++) {
                                                            Documento documento;
                                                            documento = documentoList.get(x);
                                                            service = Apis.getPropectosSerivice();
                                                            Call<String> call1 = service.setDocumento(documento);
                                                            call1.enqueue(new Callback<String>() {
                                                                @Override
                                                                public void onResponse(Call<String> call, Response<String> response) {
//
                                                                }

                                                                @Override
                                                                public void onFailure(Call<String> call, Throwable t) {
                                                                }
                                                            });
                                                        }
                                                        onBack();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                    Log.e("Error:", t.getMessage());
                                                }
                                            });
                                        } else {
                                            Toast.makeText(AñadirProspecto.this, "Añada Un documento", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(AñadirProspecto.this, "Falta RFC", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(AñadirProspecto.this, "Falta Telefono", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(AñadirProspecto.this, "Falta CP", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AñadirProspecto.this, "Falta numero de casa", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AñadirProspecto.this, "Falta Colonia", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AñadirProspecto.this, "Falta calle", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(AñadirProspecto.this, "Falta apelido paterno", Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(AñadirProspecto.this, "Falta nombre", Toast.LENGTH_LONG).show();
        }
    }
}
