package com.team.budwrzea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private Button btnEntrar, btnCadastrar;
    private EditText etEmail, etSenha;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        inicializarComponentes();
        eventoClick();

    }

    private void inicializarComponentes() {
        etEmail = findViewById(R.id.et_emailLoginID);
        etSenha = findViewById(R.id.et_senhaLoginID);
        btnEntrar = findViewById(R.id.btn_EntrarID);
        btnCadastrar = findViewById(R.id.btn_CadastrarseID);
    }

    private void eventoClick() {
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String senha = etSenha.getText().toString().trim();

                if (email.isEmpty() || senha.isEmpty()){
                    alert("Preencha todos os campos!");
                }else{
                    carregando();
                    Logar(email,senha);
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CadastroJogador.class));
            }
        });
    }

    private void alert(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void carregando() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Fazendo Login");
        dialog.setMessage("\nAguarde um instante...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    private void Logar(final String email, final String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();

                            if (email.toLowerCase().equals("amarthin12@gmail.com")) {
                                if (senha.equals("123456")) {
                                    dialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), Home.class));
                                    alert("Bem Vindo " + user.getEmail());
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), Home.class));
                                    alert("Bem Vindo " + user.getEmail());
                                    finish();
                                }
                            } else {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                                alert("Bem Vindo " + user.getEmail());
                                finish();
                            }

                        }else{
                            if (!task.isSuccessful()) {
                                dialog.dismiss();
                                // alert("Erro ao fazer o login! Verifique seu email e/ou senha!");
                                alert(task.getException().getMessage());
                                etEmail.setText("");
                                etSenha.setText("");
                                etEmail.requestFocus();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
