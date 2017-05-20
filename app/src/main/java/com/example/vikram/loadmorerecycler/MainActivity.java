package com.example.vikram.loadmorerecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vikram.loadmorerecycler.Model.Datum;
import com.example.vikram.loadmorerecycler.Model.Example;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity1";
    private RecyclerView recycler;
    List<Datum> list = new ArrayList<>();
    NewDataAdapter adapter;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putLong("id", 0);
        editor.putLong("time", 0);
        editor.apply();

        list.add(null);

        recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewDataAdapter(recycler,list,MainActivity.this);//Adding adapter to recyclerview
        recycler.setAdapter(adapter);

        callscript();

        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: pavlo re ");
                if(list.size()> 4){

                    Log.i(TAG, "onLoadMore: item size is >=4");
//                    list.add(null);
//                    adapter.notifyItemInserted(list.size() - 1);

                    adapter.insertprogressdialog();

                    callscript();
                }
//                list.add(null);
//                adapter.notifyItemInserted(list.size() - 1);
//                callscript();
//
//                list.remove(list.size() - 1);
//                adapter.notifyItemRemoved(list.size());
//                //add null , so the adapter will check view_type and show progress bar at bottom
////                list.add(null);
////                adapter.notifyItemInserted(list.size() - 1);
////
////                list.remove(list.size() - 1);
////                adapter.notifyItemRemoved(list.size());
////                //add items one by one
////                //writing logic to add more data
////                //mAdapter.notifyItemInserted(studentList.size());
////
//                adapter.setLoaded();
//                //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();



            }
        });

    }

    private void callscript() {
        Log.e(TAG, "callscript: hah" );
        final ApInterface taskService = ServiceGenerator.createService(ApInterface.class);
        Call<Example> call = taskService.calbk(sharedpreferences.getLong("id", 0), sharedpreferences.getLong("time", 0));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.d(TAG,"success_onresponse "+ "lol " + response.body().getResponse().getMain().getData().size());


//                if(list.size()>3){
//                    list.remove(list.size() - 1);
//                    adapter.notifyItemRemoved(list.size());

                adapter.removeLoader();

                int temp_status = response.body().getResponse().getStatus();
                Log.d(TAG, "onResponse: status_of_response"+temp_status);
                if(temp_status != 0) {
                    list = new ArrayList<>();
                    list = response.body().getResponse().getMain().getData();
                    Log.d(TAG, "onResponse: " + list.size());
                    Log.d(TAG, "onResponse: " + response.body().getResponse().getMain().getTimestamp());
                    editor.putLong("id", response.body().getResponse().getMain().getFinalId());
                    editor.putLong("time", response.body().getResponse().getMain().getTimestamp());
                    editor.apply();
                    adapter.insertdata(list);
                    adapter.setLoaded();
                }
                else
                {
                    Log.d(TAG, "onResponse: status_of_response"+temp_status);
                    adapter.insertprogressdialog();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("success_onfailure", "" + t.getMessage());
            }
        });
    }
}
