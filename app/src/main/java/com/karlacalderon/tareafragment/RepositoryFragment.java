package com.karlacalderon.tareafragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.nio.BufferUnderflowException;

import models.Repo;
public class RepositoryFragment extends Fragment {

        private TextView repoName, repoDescription;
    private Repo repo;

    private RepositoryFragment(Repo repo){
        this.repo = repo;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.repo_item, container, false);
        repoName =view.findViewById(R.id.repoName);
        repoDescription = view.findViewById(R.id.repoDescription);


        repoName.setText(this.repo.getName());
        repoDescription.setText(this.repo.getDescription());

        return view;

    }
}
