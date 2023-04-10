package com.example.etaxpointapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter {

 private Context mContext;
 private GradeAdapter mGradeAdapter;

 public void setConfig(RecyclerView recyclerView, Context context, List<Meetings> list, List<String> keys) {
  mContext = context;
  mGradeAdapter = new GradeAdapter(list,keys);
  recyclerView.setLayoutManager( new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
  recyclerView.setAdapter(mGradeAdapter);
 }


 class GradeList extends RecyclerView.ViewHolder {
  TextView  date, totime,fromtime,title;

  public GradeList(View view) {
   super(view);

   date=itemView.findViewById(R.id.date_list);
   totime=itemView.findViewById(R.id.totime_list);
   fromtime=itemView.findViewById(R.id.fromtime_list);
   title=itemView.findViewById(R.id.title_list);


  }

  public void bind(Meetings meetings, String key) {
   title.setText(meetings.getTitle_m());
  date.setText(meetings.getDate_m());
   totime.setText(meetings.getTodate_m());
  fromtime.setText(meetings.getFromdate_m());

  }


 }

 class GradeAdapter extends RecyclerView.Adapter<GradeList> {
  private List<Meetings> list;
  private List<String> mKeys;

  public GradeAdapter(List<Meetings> list, List<String> mKeys) {
   this.list = list;
   this.mKeys = mKeys;


  }

  @NonNull
  @Override
  public GradeList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   View view=LayoutInflater.from(mContext).inflate(R.layout.activity_item,parent,false);
   return new GradeList(view);
  }

  @Override
  public void onBindViewHolder(@NonNull GradeList holder, int position) {
   holder.bind(list.get(position), mKeys.get(position));
  }

  @Override
  public int getItemCount() {
   return list.size();
  }
 }

}
