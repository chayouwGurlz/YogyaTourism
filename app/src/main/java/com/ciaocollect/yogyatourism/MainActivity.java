package com.ciaocollect.yogyatourism;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTourismList.addItemDecoration(new DividerItemDecoration(rvTourismList.getContext(), linearLayoutManager.getOrientation()));
        rvTourismList.setLayoutManager(linearLayoutManager);
        rvTourismList.setHasFixedSize(true);
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
                    ItemClickSupport.addTo(rvTourismList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            Intent tourismIntent = new Intent(MainActivity.this, DetailActivity.class);
                            tourismIntent.putExtra(DetailActivity.EXTRA_TOURISM, listTourism.get(position));
                            startActivity(tourismIntent);
                        }
                    });
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
