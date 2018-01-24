package com.amirtokopedia.newsapitokopedia.api;

import com.amirtokopedia.newsapitokopedia.util.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amir Malik on 5/2/17.
 */

public class APIService {
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public Retrofit BaseURLAPI()
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.INSTANCE.getAPI_URL()).client(okHttpClient.newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public APIInterface getInterfaceService(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.INSTANCE.getAPI_URL()).client(okHttpClient.newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        APIInterface mInterfaceService = retrofit.create(APIInterface.class);
        return mInterfaceService;
    }


}
