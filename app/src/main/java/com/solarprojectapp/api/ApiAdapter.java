package com.solarprojectapp.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.solarprojectapp.generated.model.Login;
import com.solarprojectapp.generated.parser.UserProfileDeserializer;
import com.solarprojectapp.utils.LogUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiAdapter {

    public final String TAG = LogUtils.makeLogTag(ApiAdapter.class);

    private ApiAdapter() {
    }


    public static <A> A createRestAdapter(Class<A> AdapterClass, String baseUrl, Context context){


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Login.class,new UserProfileDeserializer());
        Gson gson = gsonBuilder.serializeNulls().create();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(Client.getClientInstance(context))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return restAdapter.create(AdapterClass);


    }


}
