package com.karlacalderon.tareafragment;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.AlbumDatabaseHelper;
import models.Repo;

public class RepoForm extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText;
    private Button btnSave;

    private AlbumDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_create_form);

        nameEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        btnSave = findViewById(R.id.saveButton);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRepo();

            }
        });

        databaseHelper = new AlbumDatabaseHelper(this);
    }

    private  void saveRepo(){
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();


        if (name.isEmpty()|| description.isEmpty()){
            Toast.makeText(this,"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();
        }
        else {
            Repo repo = new Repo(0, name,description);
            databaseHelper.insertRepo(repo);
            Toast.makeText(this,"Repositorio guardado exitosamente",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
