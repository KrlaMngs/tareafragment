package RetrofitGit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.github.com/";
    private static GitHubApiService gitHubApiService;

    public static GitHubApiService getGitHubApiService() {
        if (gitHubApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            gitHubApiService = retrofit.create(GitHubApiService.class);
        }
        return gitHubApiService;
    }
}

