package br.edu.unisep.devmob.exemploapplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import br.edu.unisep.devmob.exemploapplogin.model.User;
import br.edu.unisep.devmob.exemploapplogin.repository.RepositoryUser;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvError;
    private EditText etUser;
    private EditText etPassword;
    private CheckBox chkLembrarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        chkLembrarLogin = findViewById(R.id.chkLembrarLogin);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        chkLembrarLogin.setChecked(sp.getBoolean("chk_lembrar", false));
        if (chkLembrarLogin.isChecked()) {
            etUser.setText(sp.getString("txt_login", ""));
            etUser.setEnabled(false);
        }

        chkLembrarLogin.setOnCheckedChangeListener((buttonView, isChecked) -> etUser.setEnabled(!isChecked));
        // ver se existe o admin criado senao ja cria no banco
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@admin.com.br");
        admin.setName("admin");
        RepositoryUser rpuser = new RepositoryUser(MainActivity.this);
        try{
            if (!rpuser.checkLogin(admin)){
                rpuser.inserir(admin);
            }
        } finally {
            rpuser.fechar();
        }


        btnLogin.setOnClickListener(view -> {
            User u = new User();
            u.setLogin(etUser.getText().toString());
            u.setPassword(etPassword.getText().toString());
            RepositoryUser rpuser1 = new RepositoryUser(MainActivity.this);
            try{
                if (rpuser1.checkLogin(u)) {
                    Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                    intent.putExtra("name", etUser.getText().toString());
                    startActivity(intent);
                } else {
                  tvError.setText(R.string.user_invalid);
                }
            } finally {
                rpuser1.fechar();
            }
        });
    }

    private void salvarPreferencias() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sp.edit();
        if (chkLembrarLogin.isChecked()) {
            editor.putString("txt_login", etUser.getText().toString());
        } else {
            editor.putString("txt_login", "");
        }
        editor.putBoolean("chk_lembrar", chkLembrarLogin.isChecked());
        editor.apply();
    }

    @Override
    protected void onStop() {
        salvarPreferencias();
        super.onStop();
    }
}