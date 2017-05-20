package com.example.vikram.loadmorerecycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vikram.loadmorerecycler.Model.Datum;

import java.util.List;


public class NewDataAdapter extends RecyclerView.Adapter<NewDataAdapter.MyViewHolder> {
    String TAG = "NewDataAdapter";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Context context;
    private List<Datum> contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public NewDataAdapter(RecyclerView recyclerView, List<Datum> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.d(TAG, "onScrolled: 00 " + isLoading + " " + totalItemCount + " " + (lastVisibleItem + visibleThreshold));
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        Log.d(TAG, "onScrolled: onload");
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void insertprogressdialog() {
        contacts.add(null);
        notifyItemInserted(contacts.size() - 1);
    }

    public void removeLoader() {

        contacts.remove(contacts.size() - 1);
        notifyItemRemoved(contacts.size());
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class UserViewHolder extends MyViewHolder {
        public TextView id, name, phone, email, dob, doj;

        public UserViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            email = (TextView) view.findViewById(R.id.email);
            dob = (TextView) view.findViewById(R.id.dob);
            doj = (TextView) view.findViewById(R.id.doj);
        }
    }

    private class LoadingViewHolder extends MyViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_row, parent, false);
            return new UserViewHolder(view);
        }  if (viewType == VIEW_TYPE_LOADING) {


            View view = LayoutInflater.from(context).inflate(R.layout.progressbar_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            Datum contact = contacts.get(position);
            UserViewHolder h1 = (UserViewHolder) holder;
            h1.id.setText("" + contact.getId());
            h1.name.setText("" + contact.getName());
            h1.phone.setText("" + contact.getPhone());
            h1.email.setText("" + contact.getEmail());
            h1.dob.setText("" + contact.getDob());
            h1.doj.setText("" + contact.getDoj());
        } else if (holder instanceof LoadingViewHolder) {

            Log.e(TAG, "onBindViewHolder: "+VIEW_TYPE_LOADING );

            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }else{
            Log.e(TAG, "onBindViewHolder: HOW I REACHED HERE" );
        }
    }
    public void insertdata(List<Datum> list2) {
   /*     List<Datum> c = contacts;
        contacts = list2;
        Log.e(TAG, "insertdata: "+c.size()+" "+list2.size() );
        notifyItemRangeInserted(c.size(),list2.size());*/


        contacts.addAll(list2);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public void setLoaded() {
        isLoading = false;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

}