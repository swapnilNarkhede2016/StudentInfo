package com.accion.assigment.studentinfo;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import com.accion.assigment.studentinfo.common.RestApi;
import com.squareup.picasso.Picasso;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

/**
 * Created by swapnil on 9/4/16.
 */
public class App extends Application {
    private static RestApi sApi;
    private static Picasso sPicasso;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }

    /**
     * Global picasso instance, all image loading should happen through this
     * instance.
     *
     * @return
     */
    public static synchronized Picasso getPicasso() {
        if (sPicasso == null) {
            sPicasso = buildPicasso();
        }

        return sPicasso;
    }

    /**
     * Configures the Picasso instance to use custom downloader instance as we'll
     * need to provide access_token and device_id to the request to download the
     * image
     */
    private static Picasso buildPicasso() {
        Picasso.Builder builder = new Picasso.Builder(getContext());
        return builder.build();
    }

    private static Context getContext() {
        return sContext;
    }

    public static RestApi getApi() {
        if (sApi == null) {
            sApi = getAdapter().create(RestApi.class);
        }

        return sApi;
    }

    private static RestAdapter getAdapter() {
        OkHttpClient okHttpClient = new OkHttpClient();
        OkClient client = new OkClient(okHttpClient);
        return new RestAdapter.Builder().setClient(client).setEndpoint(BuildConfig.BASE_URL)
                .setConverter(getJsonConvertor())
                .setLogLevel(getLogLevel())
                .build();
    }

    private static RestAdapter.LogLevel getLogLevel() {
        return BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE;
    }

    private static Converter getJsonConvertor() {
        Gson gson = new GsonBuilder().create();
        return new GsonConverter(gson);
    }
}
