package com.ibraheem.android.palestinenews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsClient {
    @GET("search")
    Call<NewsMain> fetchNews(@Query("q") String topic,
                                                          @Query("api-key") String API_KEY,
                                                          @Query("show-fields") String SHOW_FIELDS);
}
