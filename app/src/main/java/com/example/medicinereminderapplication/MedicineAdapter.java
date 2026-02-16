package com.example.medicinereminderapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;
import android.text.TextUtils;


public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private List<Medicine> medicineList;
    private Context context;
    private OnItemClickListner listener;

    public interface onItemClickListener { //Handling click events
        void onItemClick(Medicine medicine);
    }

    public MedicineAdapter(List<Medicine> medicineList, Context context, OnItemClickListner listener){
        this.medicineList = medicineList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
        return new ViewHolder(view);
    }
}
