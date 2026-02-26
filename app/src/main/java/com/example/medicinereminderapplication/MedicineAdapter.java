package com.example.medicinereminderapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.ArrayList;
import android.text.TextUtils;


public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private List<Medicine> medicineList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener { //Handling click events
        void onItemClick(Medicine medicine);
    }

    public MedicineAdapter(List<Medicine> medicineList, Context context, OnItemClickListener listener){
        this.medicineList = medicineList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){//Setting medicine details into TextViews
        Medicine med = medicineList.get(position);
        holder.txtName.setText(med.getName());
        //holder.txtDosage.setText(med.getDosage());
        String dosage = med.getDosage();
        if (dosage == null || dosage.isEmpty()){
            holder.txtDosage.setText("Dosage : N/A");
        } else {
            holder.txtDosage.setText("Dosage : " + dosage);
        }

        //holder.txtDays.setText(TextUtils.join(", ", med.getDays() != null ? med.getDays() : new ArrayList()));
        String daysText = "";
        if (med.getDays() != null && !med.getDays().isEmpty()){
            daysText = TextUtils.join(", ", med.getDays());
        } else {
            daysText = "N/A";
        }
        holder.txtDays.setText("Reminder Days : " + daysText);

        //holder.txtTimes.setText(TextUtils.join(", ", med.getTimes() != null ? med.getTimes() : new ArrayList()));
        String timesText = "";
        if (med.getTimes() != null && !med.getTimes().isEmpty()){
            timesText = TextUtils.join(", ", med.getTimes());
        } else {
            timesText = "N/A";
        }
        holder.txtTimes.setText("Reminder Times : " + timesText);

        holder.txtStartDate.setText("From : " + (med.getStartDate() != null ? med.getStartDate() : "N/A"));

        String endDate = med.getEndDate();
        if(endDate == null || endDate.isEmpty() || endDate.equalsIgnoreCase("Optional") || endDate.equalsIgnoreCase("End Date")){
            holder.txtEndDate.setText("To : N/A");
        } else {
            holder.txtEndDate.setText("To : " + endDate);
        }

        String notes = med.getNotes();
        if (notes == null || notes.isEmpty()){
            holder.txtNotes.setText("Notes : N/A");
        } else {
            holder.txtNotes.setText("Notes : " + notes);
        }

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
        TextView txtName, txtDosage, txtStartDate, txtEndDate, txtDays, txtTimes, txtNotes;

        public ViewHolder(View itemView){//To hold references
            super(itemView);
            txtName = itemView.findViewById(R.id.txtMedicineName);
            txtDosage = itemView.findViewById(R.id.txtDosage);
            txtStartDate = itemView.findViewById(R.id.txtStartDate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            txtDays = itemView.findViewById(R.id.txtDays);
            txtTimes = itemView.findViewById(R.id.txtTimes);
            txtNotes = itemView.findViewById(R.id.txtNotes);
        }
    }
}

//100% Completed