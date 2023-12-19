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

public class EditarRepoActivity extends AppCompatActivity {

    private EditText edtNuevoNombre, edtNuevaDescripcion;
    private Button btnEditar;
    private GitHubApiService gitHubApiService;
    private String nombreRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_repo);

        edtNuevoNombre = findViewById(R.id.edtNuevoNombre);
        edtNuevaDescripcion = findViewById(R.id.edtNuevaDescripcion);
        btnEditar = findViewById(R.id.btnEditar);

        gitHubApiService = RetrofitClient.getGitHubApiService();

        // Obtener el nombre del repositorio a editar de la actividad anterior
        nombreRepo = getIntent().getStringExtra("nombre_repo");

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarRepositorio();
            }
        });
    }

    private void editarRepositorio() {
        String nuevoNombre = edtNuevoNombre.getText().toString();
        String nuevaDescripcion = edtNuevaDescripcion.getText().toString();

        if (!nuevoNombre.isEmpty() && !nuevaDescripcion.isEmpty()) {
            RepoRequest repoRequest = new RepoRequest(nuevoNombre, nuevaDescripcion);

            Call<Repo> call = gitHubApiService.editRepo("Bearer tu_token_aqui", "dueño", nombreRepo, repoRequest);

            call.enqueue(new Callback<Repo>() {
                @Override
                public void onResponse(Call<Repo> call, Response<Repo> response) {
                    if (response.isSuccessful()) {
                        Repo repoEditado = response.body();
                        // Realizar acciones después de la edición
                        Toast.makeText(EditarRepoActivity.this, "Repositorio editado con éxito", Toast.LENGTH_SHORT).show();
                        finish();  // Cerrar la actividad después de la edición
                    } else {
                        // Manejar respuesta no exitosa
                        Toast.makeText(EditarRepoActivity.this, "Error al editar el repositorio", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Repo> call, Throwable t) {
                    // Manejar fallo en la llamada
                    Toast.makeText(EditarRepoActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}

