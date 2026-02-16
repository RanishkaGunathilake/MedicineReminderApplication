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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
        return new RecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        Medicine med = medicineList.get(position);
        holder.txtName.setText(med.getName());
        holder.txtDosage.setText(med.getDosage());
        holder.txtDays.setText(TextUtils.join(", ", med.getDays() != null ? med.getDays() : new ArrayList()));
        holder.txtTimes.setText(TextUtils.join(", ", med.getTimes() != null ? med.getTimes() : new ArrayList()));
        holder.txtStartDate.setText("From : " + (med.getStartDate() != null ? med.getStartDate() : "N/A"));
        holder.txtEndDate.setText("From : " + (med.getEndDate() != null ? med.getEndDate() : "N/A"));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(med));
    }

    @Override
    public int getItemCount() { return medicineList.size();}

    public void updateList(List<Medicine> newList){
        medicineList.clear();
        medicineList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtName, txtDosage, txtStartDate, txtEndDate, txtDays, txtTimes;

        public ViewHolder(View itemView){
            super(itemView);
            txtName = itemView.findViewById(R.id.txtMedicineName);
            txtDosage = itemView.findViewById(R.id.txtDosage);
            txtStartDate = itemView.findViewById(R.id.txtStartDate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            txtDays = itemView.findViewById(R.id.txtDays);
            txtTimes = itemView.findViewById(R.id.txtMedicineName);
        }
    }
}

//Completed