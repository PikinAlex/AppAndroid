package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.Documento;

import java.util.List;

public class DocumentoAdapter extends ArrayAdapter<Documento> {
    private Context context;
    private List<Documento> documentos;

    public DocumentoAdapter(@NonNull Context context, int resource, @NonNull List<Documento> objects) {
        super(context, resource, objects);
        this.context=context;
        this.documentos=objects;
    }
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=layoutInflater.inflate(R.layout.content_documento,parent,false);

        try{
            final String base= documentos.get(position).getDocumento();
            byte[] byteArrray = Base64.decode(base,0);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArrray, 0 , byteArrray.length);


            ImageView imageView = (ImageView)rowView.findViewById(R.id.documento);
            TextView txtid=(TextView)rowView.findViewById(R.id.id_prospecto);
            TextView txtnombre=(TextView)rowView.findViewById(R.id.Nombre);
            imageView.setImageBitmap(bmp);


            txtid.setText(String.format("ID:%d",documentos.get(position).getId_prospecto()));
            txtnombre.setText(String.format("NOMBRE:%s",documentos.get(position).getNombre()));
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return rowView;
    }
}
