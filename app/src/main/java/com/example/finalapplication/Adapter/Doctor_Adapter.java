package com.example.finalapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapplication.Doctor.Doctor_Update;
import com.example.finalapplication.Module.showDoctor;
import com.example.finalapplication.R;

import java.util.ArrayList;
public class Doctor_Adapter extends RecyclerView.Adapter<Doctor_Adapter.ViewHolder> {

    Context context;
    ArrayList<showDoctor> topicArrayList;

    private ItemClickListener mClickListener;

    public Doctor_Adapter(Context context, ArrayList<showDoctor> topicArrayList, ItemClickListener onClick ) {
        this.context = context;

        this.topicArrayList = topicArrayList;
        this.mClickListener = onClick;


    }


    @NonNull
    @Override
    public Doctor_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_doc_item,parent,false);
        return new Doctor_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        showDoctor n =topicArrayList.get(position);
        holder.show_name.setText(n.getTitle());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(holder.getAdapterPosition(),topicArrayList.get(position).getId());
            }
        });
        //  holder.show_img.setImageURI(uri);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Doctor_Update.class);
                context.startActivity(intent);


                //   EditClickListener.onItemClick(holder.getAdapterPosition(),topicArrayList.get(position).getId());


            }
        });

    }


    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView show_name;
        ImageView edit;
        ImageView delete;

        CardView show_card;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.show_name = itemView.findViewById(R.id.show_title);
            this.edit=itemView.findViewById(R.id.edit_topic);
            this.delete=itemView.findViewById(R.id.delete_topic);
            this.show_card=itemView.findViewById(R.id.show_card);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }

    }
    public interface ItemClickListener {
        void onItemClick(int position, String id);

    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    showDoctor getItem(int id) {

        return topicArrayList.get(id);
    }


}