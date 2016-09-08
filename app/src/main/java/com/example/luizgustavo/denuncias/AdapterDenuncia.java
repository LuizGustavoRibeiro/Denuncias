package com.example.luizgustavo.denuncias;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luiz Gustavo on 11/08/2016.
 */
public class AdapterDenuncia extends ArrayAdapter<JSONObject> {

    public AdapterDenuncia(Context context, int resource, ArrayList<JSONObject> items) {

        super(context, resource, items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.denuncia, null);

        JSONObject denuncia = getItem(position);

        if (denuncia != null) {

            ImageView imgThumbnail = (ImageView) convertView.findViewById(R.id.imgDenuncia);
            TextView txvData       = (TextView) convertView.findViewById(R.id.txtDataDenuncia);
            TextView txvEndereco   = (TextView) convertView.findViewById(R.id.txtEnderecoDenuncia);
            TextView txvDescricao  = (TextView) convertView.findViewById(R.id.txtDescricaoDenuncia);

            try {
                String url = denuncia.getJSONArray("anexos").getString(0);

                Glide.with(getContext())
                        .load(url)
                        .into(imgThumbnail);

                txvData.setText(denuncia.getString("data"));
                txvEndereco.setText(denuncia.getString("endereco"));
                txvDescricao.setText(denuncia.getString("descricao"));
            }catch (JSONException exc) {

            }
        }

        return convertView;

    }
}
