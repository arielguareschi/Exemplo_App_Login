package br.edu.unisep.devmob.exemploapplogin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

    private User user;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        etName = findViewById(R.id.etName);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);

        user = new User();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Long id = extras.getLong("userId");
            RepositoryUser repositoryUser = new RepositoryUser(UserRegister.this);
            try {
                user = repositoryUser.buscarUnico(id);

                if (user != null) {
                    etName.setText(user.getName());
                    etLogin.setText(user.getLogin());
                    etPassword.setText(user.getPassword());
                    etEmail.setText(user.getEmail());
                }

            } finally {
                repositoryUser.fechar();
            }
        }

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            RepositoryUser rpuser = new RepositoryUser(UserRegister.this);
            try {
                user.setName(etName.getText().toString());
                user.setLogin(etLogin.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setEmail(etEmail.getText().toString());

                if (user.getId() != null) {
                    rpuser.editar(user);
                } else {
                    rpuser.inserir(user);
                }

                Toast.makeText(UserRegister.this, "Usuário salvo com sucesso!", Toast.LENGTH_SHORT).show();
            } finally {
                rpuser.fechar();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_user_register, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
