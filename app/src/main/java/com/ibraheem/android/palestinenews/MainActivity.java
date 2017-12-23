package com.ibraheem.android.palestinenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static List<NewsArticle> newsArticles;
    @Bind(R.id.news_list)
    RecyclerView newsList;
    @Bind(R.id.empty_view)
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        newsArticles = new ArrayList<>();
        newsList.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, newsArticles);
        newsList.setAdapter(newsAdapter);
        fetchNewsArticles("Palestine");
    }

    private void reDrawRecyclerView() {
        if (newsArticles.isEmpty()) {
            newsList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            newsList.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        newsList.getAdapter().notifyDataSetChanged();
    }

    private void fetchNewsArticles(String topic) {
        NewsClient client = NewsServiceGenerator.createService(NewsClient.class);
        String API_KEY = "4d5e77df-caa0-4fdd-9e96-eae89d4f8276";
        String SHOW_FIELDS = "thumbnail";
        Call<NewsMain> call = client.fetchNews(
                topic, API_KEY, SHOW_FIELDS);
        call.enqueue(new Callback<NewsMain>() {
            @Override
            public void onResponse(Call<NewsMain> call, Response<NewsMain> mainResponse) {
                if (mainResponse.isSuccessful()) {
                    NewsResponse res = mainResponse.body().getResponse();
                    List<NewsResult> apiResults = res.getResults();
                    for (NewsResult apiResult : apiResults) {
                        String thumbnail = "";
                        if (apiResult.getFields() != null) {
                            thumbnail = apiResult.getFields().getThumbnail();
                        }

                        newsArticles.add(new NewsArticle(apiResult.getWebTitle(),
                                apiResult.getSectionName(), thumbnail,
                                apiResult.getWebUrl()));
                    }
                    reDrawRecyclerView();
                } else {
                    int statusCode = mainResponse.code();
                    ResponseBody errorBody = mainResponse.errorBody();
                    Log.e("Network Error: ", errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<NewsMain> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
