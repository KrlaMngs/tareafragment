package com.karlacalderon.tareafragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import RetrofitGit.GitHubApiService;
import adapters.RepoAdapter;
import models.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RepoAdapter repoAdapter;
    private List<Repo> repositories;

    private FloatingActionButton fabRepoForm;

    private GitHubApiService gitHubApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar el botón flotante para abrir la actividad CrearRepoActivity
        fabRepoForm = findViewById(R.id.fabNewRepo);
        fabRepoForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityCrearRepo();
            }
        });

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubApiService = retrofit.create(GitHubApiService.class);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar repositorios
        loadRepositories();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar repositorios al volver a la actividad
        loadRepositories();
    }

    private void abrirActivityCrearRepo() {
        Intent intent = new Intent(this, CrearRepoActivity.class);
        startActivity(intent);
    }

    private void loadRepositories() {
        // Obtener el token de autenticación (deberías tener tu propia lógica para esto)
        String authToken = "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";

        // Utilizar Retrofit para obtener la lista de repositorios desde GitHub
        Call<List<Repo>> call = gitHubApiService.getRepos("Bearer " + authToken);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.isSuccessful()) {
                    repositories = response.body();
                    // Actualizar el RecyclerView con la nueva lista de repositorios
                    updateRecyclerView();
                } else {
                    // Manejar respuesta no exitosa
                    Toast.makeText(MainActivity.this, "Error al obtener la lista de repositorios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                // Manejar fallo en la llamada
                Toast.makeText(MainActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView() {
        // Configurar el adaptador y el RecyclerView
        repoAdapter = new RepoAdapter(repositories, this);
        recyclerView.setAdapter(repoAdapter);
    }
}
