package br.edu.unisep.devmob.exemploapplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    private TextView tvName;
    private Button btnClient;
    private Button btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Bundle extras = getIntent().getExtras();

        tvName = findViewById(R.id.tvName);
        btnClient = findViewById(R.id.btnClient);
        btnUser = findViewById(R.id.btnUser);

        if (extras != null) {
            String login = extras.getString("name");
            tvName.setText(getString(R.string.welcome).concat(login));
        }

        btnUser.setOnClickListener(view -> {
            Intent intentUserRegister = new Intent(MenuPrincipal.this, UserList.class);
            startActivity(intentUserRegister);
        });

        btnClient.setOnClickListener(view -> {
            Intent intentClientRegister = new Intent(MenuPrincipal.this, ClientRegister.class);
            startActivity(intentClientRegister);
        });

    }
}