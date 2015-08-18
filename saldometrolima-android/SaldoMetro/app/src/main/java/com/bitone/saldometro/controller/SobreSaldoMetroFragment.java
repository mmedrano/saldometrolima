package com.bitone.saldometro.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SobreSaldoMetroFragment extends Fragment implements View.OnClickListener {
    TextView tvNombreEmpresa;
    TextView tvEmailEmpresa;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sobre_saldo_metro, container, false);
        tvNombreEmpresa = (TextView) rootView.findViewById(R.id.tvNombreEmpresa);
        tvEmailEmpresa = (TextView) rootView.findViewById(R.id.tvEmailEmpresa);

        tvNombreEmpresa.setOnClickListener(this);
        tvEmailEmpresa.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvNombreEmpresa){
            irAPaginaEmpresa();
        }else if(v.getId() == R.id.tvEmailEmpresa){
            irAEnviarEmail();
        }
    }

    private void irAPaginaEmpresa(){
        String urlEmpresa = getString(R.string.url_bitone);
        Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(urlEmpresa));
        startActivity(intentUrl);
    }

    private void irAEnviarEmail(){
        String[] emailPara = new String[1];
        String emailEmpresa = getString(R.string.email_bitone);
        emailPara[0] = emailEmpresa;

        String emailAsunto = getString(R.string.email_asunto);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailPara);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailAsunto);

        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }
}
