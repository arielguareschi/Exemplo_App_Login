package br.edu.unisep.devmob.exemploapplogin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.unisep.devmob.exemploapplogin.model.User;
import br.edu.unisep.devmob.exemploapplogin.repository.RepositoryUser;

public class UserRegister extends AppCompatActivity {

    EditText etName;
    EditText etLogin;
    EditText etPassword;
    EditText etEmail;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        etName = findViewById(R.id.etName);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            RepositoryUser rpuser = new RepositoryUser(UserRegister.this);
            try {
                User admin = new User();
                admin.setName(etName.getText().toString());
                admin.setLogin(etLogin.getText().toString());
                admin.setPassword(etPassword.getText().toString());
                admin.setEmail(etEmail.getText().toString());
                rpuser.inserir(admin);
                Toast.makeText(UserRegister.this, "Usuário salvo com sucesso!", Toast.LENGTH_SHORT).show();
            } finally {
                rpuser.fechar();
            }
        });

    }
}
