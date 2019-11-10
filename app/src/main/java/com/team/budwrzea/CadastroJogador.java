package com.team.budwrzea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroJogador extends AppCompatActivity {

    private EditText etEmailJog, etSenhaJog, etConfSenha;
    private Button btnCadastrarJog;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private Uri mUriSelecionada;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jogador);

        inicializaComponentes();
        eventoClicks();
        inicializarDatabase();

    }

    private void inicializarDatabase() {
        FirebaseApp.initializeApp(CadastroJogador.this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void eventoClicks() {
        btnCadastrarJog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmailJog.getText().toString().trim();
                String senha = etSenhaJog.getText().toString().trim();
                String confSenha = etConfSenha.getText().toString().trim();

                if (email == null || email.isEmpty() || senha == null || senha.isEmpty()
                        || confSenha==null || confSenha.isEmpty()){
                    alert("Todos os campos devem ser preenchidos!");
                }else {
                    if (senha.equals(confSenha)) {
                        carregando();
                        criarUser(email, senha);
                    }else{
                        alert("As senhas devem ser iguais!");
                        etSenhaJog.setText("");
                        etConfSenha.setText("");
                        etSenhaJog.requestFocus();
                    }
                }
            }
        });
    }

    private void criarUser(final String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();
                            //salvarUsuarioNoFirebase();
                            dialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();
                            alert("Bem vindo "+ auth.getCurrentUser().getEmail());
                        }else{
                            dialog.dismiss();
                            alert(task.getException().toString());
                            etEmailJog.setText("");
                            etSenhaJog.setText("");
                            etConfSenha.setText("");
                            etEmailJog.requestFocus();
                        }
                    }
                });
    }

    private void inicializaComponentes() {
        etEmailJog = findViewById(R.id.etNomeCadastroID);
        etSenhaJog = findViewById(R.id.etSenhaCadID);
        etConfSenha = findViewById(R.id.etConfSenhaCadID);
        btnCadastrarJog = findViewById(R.id.btn_CadastrarJogID);
    }

    private void carregando() {
        dialog = new ProgressDialog(CadastroJogador.this);
        dialog.setTitle("Cadastrando...");
        dialog.setMessage("\nAguarde um instante...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    private void alert(String msg){
        Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
