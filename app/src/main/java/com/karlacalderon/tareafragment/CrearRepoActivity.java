package com.karlacalderon.tareafragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import RetrofitGit.GitHubApiService;
import RetrofitGit.RepoRequest;
import RetrofitGit.RetrofitClient;
import models.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearRepoActivity extends AppCompatActivity {

    private EditText edtNombre, edtDescripcion;
    private Button btnCrear;
    private GitHubApiService gitHubApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_repo);

        edtNombre = findViewById(R.id.edtNombre);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        btnCrear = findViewById(R.id.btnCrear);

        gitHubApiService = RetrofitClient.getGitHubApiService();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearRepositorio();
            }
        });
    }

    private void crearRepositorio() {
        // Obtener el token de autenticación (deberías tener tu propia lógica para esto)
        String authToken = "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"; // Reemplazar con tu lógica para obtener el token

        String nombre = edtNombre.getText().toString();
        String descripcion = edtDescripcion.getText().toString();

        if (!nombre.isEmpty() && !descripcion.isEmpty()) {
            RepoRequest repoRequest = new RepoRequest(nombre, descripcion);

            // Utilizar el token en la llamada a la API
            Call<Repo> call = gitHubApiService.createRepo("Bearer " + authToken, repoRequest);

            call.enqueue(new Callback<Repo>() {
                @Override
                public void onResponse(Call<Repo> call, Response<Repo> response) {
                    if (response.isSuccessful()) {
                        Repo nuevoRepo = response.body();
                        // Realizar acciones después de la creación
                        Toast.makeText(CrearRepoActivity.this, "Repositorio creado con éxito", Toast.LENGTH_SHORT).show();
                        finish();  // Cerrar la actividad después de la creación
                    } else {
                        // Manejar respuesta no exitosa
                        Toast.makeText(CrearRepoActivity.this, "Error al crear el repositorio", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Repo> call, Throwable t) {
                    // Manejar fallo en la llamada
                    Toast.makeText(CrearRepoActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
