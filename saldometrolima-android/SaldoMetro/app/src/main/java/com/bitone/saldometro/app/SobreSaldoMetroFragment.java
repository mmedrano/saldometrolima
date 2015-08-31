package com.bitone.saldometro.app;

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
    TextView tvCreditoAndina;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sobre_saldo_metro, container, false);
        tvNombreEmpresa = (TextView) rootView.findViewById(R.id.tvNombreEmpresa);
        tvEmailEmpresa = (TextView) rootView.findViewById(R.id.tvEmailEmpresa);
        tvCreditoAndina = (TextView) rootView.findViewById(R.id.tvCreditoAndina);

        tvNombreEmpresa.setOnClickListener(this);
        tvEmailEmpresa.setOnClickListener(this);
        tvCreditoAndina.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvNombreEmpresa:
                irAPaginaEmpresa();
                break;
            case R.id.tvEmailEmpresa:
                irAEnviarEmail();
                break;
            case R.id.tvCreditoAndina:
                irAPaginaCreditoAndina();
                break;
            default: break;
        }
    }

    private void irAPaginaEmpresa(){
        String urlEmpresa = getString(R.string.url_bitone);
        Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(urlEmpresa));
        startActivity(intentUrl);
    }

    private void irAPaginaCreditoAndina(){
        String urlAndina = getString(R.string.url_andina);
        Intent intentUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAndina));
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
