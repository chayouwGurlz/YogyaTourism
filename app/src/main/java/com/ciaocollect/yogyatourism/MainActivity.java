package com.ciaocollect.yogyatourism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private TourismAdapterList tourismAdapterList;
    private RecyclerView rvTourismList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTourismList = findViewById(R.id.rv_list_tourism);
        rvTourismList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTourismList.addItemDecoration(new DividerItemDecoration(rvTourismList.getContext(), linearLayoutManager.getOrientation()));
        rvTourismList.setLayoutManager(linearLayoutManager);
        tourismAdapterList = new TourismAdapterList(this);
        tourismAdapterList.notifyDataSetChanged();
        SetListTourism();
    }

    public void SetListTourism(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Tourism> listTourism = new ArrayList<>();
        String listUrl = "http://erporate.com/bootcamp/jsonBootcamp.php";
        client.get(listUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("data");
                    for(int i=0; i<list.length(); i++){
                        JSONObject tourist = list.getJSONObject(i);
                        Tourism tourism = new Tourism(tourist);
                        listTourism.add(tourism);
                    }
                    tourismAdapterList.setListTourism(listTourism);
                    rvTourismList.setAdapter(tourismAdapterList);
                } catch (Exception e){
                    listTourism.add(null);
                    Log.d("onSuccess catch", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listTourism.add(null);
                Log.d("onFailure catch", error.getMessage());
            }
        });
    }
}
