package br.edu.unisep.devmob.exemploapplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class ClientRegister extends AppCompatActivity {

    EditText etName;
    EditText etAdress;
    EditText etCity;
    EditText etPhone;
    EditText etCellPhone;
    SwitchMaterial swActive;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        etName = findViewById(R.id.etName);
        etAdress = findViewById(R.id.etAdress);
        etCity = findViewById(R.id.etCity);
        etPhone = findViewById(R.id.etPhone);
        etCellPhone = findViewById(R.id.etCellPhone);
        swActive = findViewById(R.id.swActive);

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}