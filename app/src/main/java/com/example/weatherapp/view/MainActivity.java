package com.example.weatherapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.WeatherAdapter;
import com.example.weatherapp.apputils.WeatherApp;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.dialog.ProgressDialog;
import com.example.weatherapp.model.ForeCastList;
import com.example.weatherapp.retrofit.ApiClient;
import com.example.weatherapp.retrofit.ApiInterface;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.android.material.snackbar.Snackbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ApiInterface apiService = ApiClient.getInstance().getApi();
    private ActivityMainBinding mBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if(WeatherApp.hasNetwork()){
            getWeatherData();
        }else{
            showErrorMessage("Not connected to internet.");
        }
        mBinding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Toast.makeText(MainActivity.this, "Panel Slide", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    //Toast.makeText(MainActivity.this, "Collapsed", Toast.LENGTH_SHORT).show();
                    mBinding.tvIndication.setBackgroundResource(R.drawable.ic_up_arrow);
                }

                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    //Toast.makeText(MainActivity.this, "Expanded", Toast.LENGTH_SHORT).show();
                    mBinding.tvIndication.setBackgroundResource(R.drawable.ic_down_arrow);
                }
            }
        });
    }

    private void showErrorMessage(String msg){
        Snackbar snackbar = Snackbar
                .make(mBinding.slidingLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("RELOAD", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getWeatherData();
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void getWeatherData(){
        Log.d(TAG, "getWeatherData: ");
        ProgressDialog.getInstance().show(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.init();
        mainViewModel.getWeatherRepository().observe(this, foreCastResponse -> {
            ProgressDialog.getInstance().dismiss();
            if(foreCastResponse!=null){
                List<ForeCastList> forecastList = foreCastResponse.getList();
                setForeCastList(forecastList);
            }else{
                showErrorMessage("Something went wrong.");
                //Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setForeCastList(List<ForeCastList> list) {
        mBinding.tvDateTime.setText(DateTimeUtils.formatWithPattern(list.get(0).getDtTxt(), "E, MMMM dd"));
        mBinding.tvTemp.setText("" + list.get(0).getMain().getTemp());
        WeatherAdapter weatherAdapter = new WeatherAdapter(list);
        mBinding.forecastRecyclerView.addItemDecoration(new DividerItemDecoration(mBinding.forecastRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mBinding.forecastRecyclerView.setHasFixedSize(true);
        mBinding.forecastRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.forecastRecyclerView.setAdapter(weatherAdapter);
    }
}
