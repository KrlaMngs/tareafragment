package RetrofitGit;

import java.util.List;

import models.Repo;
import retrofit2.Call;
import retrofit2.http.*;

public interface GitHubApiService {

    @POST("user/repos")
    Call<Repo> createRepo(@Header("Authorization") String token, @Body RepoRequest repoRequest);

    @PATCH("repos/{owner}/{repo}")
    Call<Repo> editRepo(
            @Header("Authorization") String token,
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Body RepoRequest repoRequest
    );

    @DELETE("repos/{owner}/{repo}")
    Call<Void> deleteRepo(
            @Header("Authorization") String token,
            @Path("owner") String owner,
            @Path("repo") String repo
    );

    @GET("user/repos")
    Call<List<Repo>> getRepos(@Header("Authorization") String token);
}
