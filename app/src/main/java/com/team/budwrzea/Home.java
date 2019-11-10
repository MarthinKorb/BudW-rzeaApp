package com.team.budwrzea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button btnAgenda, btnMarcarJogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inicializarComponentes();
        eventoClick();
    }

    private void eventoClick() {

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AgendaJogos.class));
            }
        });

        btnMarcarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MarcarJogo.class));
            }
        });

    }

    private void inicializarComponentes() {
        btnAgenda = findViewById(R.id.btn_agendaHomeID);
        btnMarcarJogo = findViewById(R.id.btn_MarcarJogoHomeID);
    }
}
