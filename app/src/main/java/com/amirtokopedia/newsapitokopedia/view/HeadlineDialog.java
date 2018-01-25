package com.amirtokopedia.newsapitokopedia.view;


import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.amirtokopedia.newsapitokopedia.R;
import com.amirtokopedia.newsapitokopedia.adapter.CountryAdapter;
import com.amirtokopedia.newsapitokopedia.model.local.CountryModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import kotlin.text.Charsets;

/**
 * Created by Amir on 1/24/2018.
 */

public class HeadlineDialog extends Dialog implements CountryAdapter.onItemClick {

    CountryAdapter countryAdapter;
    public HeadlineDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.custom_dialog_headline);
        dataCountry();

    }

    private void dataCountry(){
        String jsonCountry = "";
        try {
            InputStream menuCountry = getContext().getAssets().open("country.json");
            int sizeCountry = menuCountry.available();
            byte[] bufferCountry = new byte[sizeCountry];
            menuCountry.read(bufferCountry);
            menuCountry.close();

            jsonCountry = new String(bufferCountry, Charsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        CountryModel dataCountry = new Gson().fromJson(jsonCountry, CountryModel.class);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.headline_change);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        countryAdapter = new CountryAdapter(getContext(), dataCountry);
        countryAdapter.setItemListener(this);
        recyclerView.setAdapter(countryAdapter);


    }

    @Override
    public void setCurrentCountry(@NotNull View view, @NotNull String name) {

    }

    @Override
    public void onItemSelectedCountry(@NotNull CountryModel.dataCountry item, @NotNull View tempView) {

    }
}
