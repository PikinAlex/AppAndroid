package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.Prospectos;

import java.util.List;
import java.util.Map;

public class PropectosAdapter extends ArrayAdapter<Prospectos> {

    private Context context;
    private  List<Prospectos>prospectos;

    public PropectosAdapter(@NonNull Context context, int resource, @NonNull List<Prospectos> objects) {
        super(context, resource, objects);
        this.context=context;
        this.prospectos=objects;
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=layoutInflater.inflate(R.layout.content_main,parent,false);

        TextView txtid=(TextView)rowView.findViewById(R.id.id_prospecto);
        TextView txtnombre=(TextView)rowView.findViewById(R.id.nombre);;
        TextView txtapellido_p=(TextView)rowView.findViewById(R.id.apellido_p);
        TextView txtapellido_m=(TextView)rowView.findViewById(R.id.apellido_m);
        TextView txtstatus=(TextView)rowView.findViewById(R.id.statusProspecto);


        txtid.setText(String.format("ID:%d",prospectos.get(position).getId_prospecto()));
        txtnombre.setText(String.format("NOMBRE:%s",prospectos.get(position).getNombre()));
        txtapellido_p.setText(String.format("APELLIDO PATERNO: %s",prospectos.get(position).getApellido_p()));
        txtapellido_m.setText(String.format("APELLIDO MATERNO: %s",prospectos.get(position).getApellido_m()));
        txtstatus.setText(String.format("STATUS: %s",prospectos.get(position).getStatus()));
        if(prospectos.get(position).getStatus().equals("Enviado")){
            txtstatus.setTextColor(Color.parseColor("#FF9800")); //FF9800 4CAF50 F60909
        }else if(prospectos.get(position).getStatus().equals("Aprobado")){
            txtstatus.setTextColor(Color.parseColor("#4CAF50"));
        }
        else if(prospectos.get(position).getStatus().equals("Rechazado")){
            txtstatus.setTextColor(Color.parseColor("#F60909"));
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProspectoActivity.class);
                intent.putExtra("ID",String.valueOf(prospectos.get(position).getId_prospecto()));
                intent.putExtra("NOMBRE",prospectos.get(position).getNombre());
                intent.putExtra("APELLIDO PATERNO",prospectos.get(position).getApellido_p());
                intent.putExtra("APELLIDO MATERNO",prospectos.get(position).getApellido_m());
                intent.putExtra("CALLE",prospectos.get(position).getCalle());
                intent.putExtra("COLONIA",prospectos.get(position).getColonia());
                intent.putExtra("NUMERO",prospectos.get(position).getNumero());
                intent.putExtra("CODIGO POSTAL",prospectos.get(position).getCodigo_postal());
                intent.putExtra("TELEFONO",prospectos.get(position).getTelefono());
                intent.putExtra("RFC",prospectos.get(position).getRfc());
                intent.putExtra("STATUS",prospectos.get(position).getStatus());
                intent.putExtra("OBSERVACIONES", prospectos.get(position).getObservaciones());

                context.startActivity(intent);
            }
        });
        return rowView;
    }

}
