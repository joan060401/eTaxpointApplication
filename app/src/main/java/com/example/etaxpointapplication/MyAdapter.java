package com.example.etaxpointapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter {
 private OnItemClickListener mListener;

 private Context mContext;
 private GradeAdapter mGradeAdapter;
 private OnItemClickListener mlistener;


 public void setConfig(RecyclerView recyclerView, Context context, List<Meetings> list, List<String> keys) {
  mContext = context;
  mGradeAdapter = new GradeAdapter(list,keys);
  recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
  recyclerView.setAdapter(mGradeAdapter);
 }

 @NonNull
 @Override
 public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  return null;
 }

 @Override
 public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

 }

 @Override
 public int getItemCount() {
  return 0;
 }

 public interface OnItemClickListener {
  void onItemClick(int position);
 }
 public void setOnItemClickListener(final OnItemClickListener listener) {
  mlistener =  listener;
 }

 class GradeList extends RecyclerView.ViewHolder implements View.OnClickListener {
  TextView date, totime, fromtime, title;
  CardView layout;

  public GradeList(View view) {
   super(view);

   date = itemView.findViewById(R.id.date_list);
   totime = itemView.findViewById(R.id.totime_list);
   fromtime = itemView.findViewById(R.id.fromtime_list);
   title = itemView.findViewById(R.id.title_list);
   layout=itemView.findViewById(R.id.layoutcard);
   itemView.setOnClickListener(this);


  }

  public void bind(Meetings meetings, String key) {
   title.setText(meetings.getTitle_m());
   date.setText(meetings.getDate_m());
   totime.setText(meetings.getTodate_m());
   fromtime.setText(meetings.getFromdate_m());

  }

  @Override
  public void onClick(View v) {
   int position = getAdapterPosition();
   if (position != RecyclerView.NO_POSITION) {
    // Call the onItemClick method of the listener interface
    mlistener.onItemClick(position);

   }

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
   final Meetings data_position = list.get(position);
   holder.bind(list.get(position), mKeys.get(position));
   holder.title.setText(list.get(position).getTitle_m());
   holder.date.setText(list.get(position).getDate_m());
   holder.totime.setText(list.get(position).getTodate_m());
   holder.fromtime.setText(list.get(position).getFromdate_m());

   holder.layout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    /* Intent intent = new Intent(v.getContext(),viewingSched.class);
     intent.putExtra("titlev",data_position.getTitle_m());
     intent.putExtra("locationv",data_position.getLocation_m());
     intent.putExtra("descriptionv",data_position.getDes_m());
     v.getContext().startActivity(intent); */
     Dialog dialog = new Dialog(v.getContext());
     dialog.setContentView(R.layout.activity_viewing_sched);
     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
     EditText titlev = dialog.findViewById(R.id.titleV);
     EditText locationv = dialog.findViewById(R.id.locationV);
     EditText descriptionv = dialog.findViewById(R.id.descriptionV);
     ImageButton closev =dialog.findViewById(R.id.closeV);
     ImageButton deletev =dialog.findViewById(R.id.deleteV);
     ImageButton edit =dialog.findViewById(R.id.editV);
     Button save = dialog.findViewById(R.id.saveV);
     TextView id= dialog.findViewById(R.id.IDmeetingV);
     DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Meetings");
     dialog.show();

     titlev.setText(data_position.getTitle_m());
     locationv.setText(data_position.getLocation_m());
     descriptionv.setText(data_position.getDes_m());
     id.setText(data_position.getId_m());


     save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      String title =titlev.getText().toString();
      String location =locationv.getText().toString();
      String description =descriptionv.getText().toString();
       save.setVisibility(View.INVISIBLE);
       save.setVisibility(View.VISIBLE);
       titlev.setEnabled(false);
       descriptionv.setEnabled(false);
       locationv.setEnabled(false);


      }
     });


     closev.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       dialog.hide();
      }
     });
     deletev.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


       // Delete the data for the corresponding item from the Firebase Realtime Database
       FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       String userUid = user.getUid();
       mDatabase.child(userUid).child(list.get(position).getId_m()).removeValue();

       // Remove the item from the RecyclerView dataset
       list.remove(position);

       // Notify the adapter that the item has been removed from the dataset
       mGradeAdapter.notifyItemRemoved(position);
      }
     });
     edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       save.setVisibility(View.VISIBLE);
       titlev.setEnabled(true);
       descriptionv.setEnabled(true);
       locationv.setEnabled(true);
      }
     });
    }
   });
  }
  @Override
  public int getItemCount() {
   return list.size();
  }

 }
 private void updatemeeting(String title,String location,String description){

  DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Meetings");
  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  String userUid = user.getUid();

  Meetings meetings = new Meetings(title,location,description);
  DbRef.child(userUid).setValue(meetings);




 }


}