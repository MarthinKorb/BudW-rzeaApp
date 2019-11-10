package com.team.budwrzea;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team.budwrzea.Model.Partida;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class MarcarJogo extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private EditText etNomeTime, etLocalPartida, etDataPartida, etHorarioPartida;
    private Button btnAgendarPartida;

    //FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_jogo);

        inicializarDatabase();
        inicializarComponentes();
        eventoClick();

    }

    private void eventoClick() {

        etDataPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDataPartida.setText("");
                //startActivity(new Intent(getApplicationContext(), CalendarioActivity.class ));
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"DatePicker");
            }
        });

        etHorarioPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHorarioPartida.setText("");
                //startActivity(new Intent(getApplicationContext(),ClockActivity.class));
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"Time Picker");
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String data = bundle.getString("editDia");
            etDataPartida.setText(data);
        }

        Bundle b = getIntent().getExtras();
        if (b != null){
            String hora = b.getString("editHora"+" : "+"editMinuto");
            etHorarioPartida    .setText(hora);
        }

        btnAgendarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeTime = etNomeTime.getText().toString().trim();
                String local = etLocalPartida.getText().toString().trim();
                String dataPartida = etDataPartida.getText().toString().trim();
                String horarioPartida = etHorarioPartida.getText().toString().trim();

                if (nomeTime.isEmpty() || local.isEmpty() || dataPartida.isEmpty() || horarioPartida.isEmpty()){
                    alert("Todos campos devem ser preenchidos");
                } else{
                    Partida p = new Partida();
                    p.setIdTime(UUID.randomUUID().toString());
                    p.setNomeTime(etNomeTime.getText().toString().trim());

                    p.setDataPartida(etDataPartida.getText().toString().trim());
                    p.setHorarioPartida(etHorarioPartida.getText().toString().trim());
                    p.setTimeStamp(System.currentTimeMillis());
                    reference.child("Partidas").child(p.getIdTime()).setValue(p);
                    alert("Partida agendada com sucesso!");
                    LimparCampos();
                }
            }
        });
    }

    private void LimparCampos() {
        etNomeTime.setText("");
        etLocalPartida.setText("");
        etDataPartida.setText("");
        etHorarioPartida.setText("");
        etNomeTime.requestFocus();
    }

    private void inicializarComponentes() {
        etNomeTime = findViewById(R.id.et_nomeTimeID);
        etLocalPartida = findViewById(R.id.et_localPartidaID);
        etDataPartida = findViewById(R.id.et_dataPartidaID);
        etHorarioPartida = findViewById(R.id.et_horarioPartidaID);
        btnAgendarPartida = findViewById(R.id.btn_SalvarPartidaID);
    }

    private void inicializarDatabase() {
        FirebaseApp.initializeApp(this);
        reference = FirebaseDatabase.getInstance().getReference();
    }

    private void alert(String s) {
        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etHorarioPartida.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        //edtDia.setText(currentDate);
        etDataPartida.setText(dayOfMonth + "/" + (month+1)+ "/" + year);

    }
}
