package br.edu.unisep.devmob.exemploapplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
            tvName.setText("Bem Vindo(a): " + login);
        }

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUserRegister = new Intent(MenuPrincipal.this, UserRegister.class);
                startActivity(intentUserRegister);
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentClientRegister = new Intent(MenuPrincipal.this, ClientRegister.class);
                startActivity(intentClientRegister);
            }
        });

    }
}