package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.karlacalderon.tareafragment.R;

import java.util.List;

import models.Repo;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repo> repositories;
    private Context context;

    public RepoAdapter(List<Repo> repositories, Context context) {
        this.repositories = repositories;
        this.context = context;
    }

    @Nullable
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false);
        return new ViewHolder(view);


    }
    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder,int position){
        Repo repo = repositories.get(position);
        holder.setAlbumData(repo);
    }

    @Override
    public int getItemCount () {

        return repositories.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView tvName, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.repoName);
            tvDescription = itemView.findViewById(R.id.repoDescription);
        }

        public void setAlbumData(Repo repo) {

            tvName.setText(repo.getName());
            tvDescription.setText(repo.getDescription());
        }
    }
}
