package com.example.medicinereminderapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.text.InputFilter;
import android.text.Spanned;

import org.w3c.dom.Text;


public class AddEditMedicineActivity extends AppCompatActivity {

    EditText edtMedicineName, edtDosage, edtNotes;
    TextView txtStartDate, txtEndDate;
    LinearLayout layoutTimes;
    Button btnAddTime, btnSave, btnCancel, btnDelete;
    CheckBox chkMon, chkTue, chkWed, chkThu, chkFri, chkSat, chkSun;
    DatabaseHelper dbhelper;
    int medicineId = -1;
    Medicine medicine;

    //Dosage input filter
    private final InputFilter dosageInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++){
                char c = source.charAt(i);

                if(!(Character.isLetterOrDigit(c) || c == ' ' || c == '.' || c == '/' || c == '%' || c == '+' || c == '_' || c == '-')){
                    return "";
                }
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_medicine);

        dbhelper = new DatabaseHelper(this);
        edtMedicineName = findViewById(R.id.edtMedicineName);
        edtDosage.setFilters(new InputFilter[]{dosageInputFilter});
        edtNotes = findViewById(R.id.edtNotes);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        layoutTimes = findViewById(R.id.layoutTimes);
        btnAddTime = findViewById(R.id.btnAddTime);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        chkMon = findViewById(R.id.chkMon);
        chkTue = findViewById(R.id.chkTue);
        chkWed = findViewById(R.id.chkWed);
        chkThu = findViewById(R.id.chkThu);
        chkFri = findViewById(R.id.chkFri);
        chkSat = findViewById(R.id.chkSat);
        chkSun = findViewById(R.id.chkSun);

        //If editing
        medicineId = getIntent().getIntExtra("medicineId", -1);
        if (medicineId != -1){
            medicine = dbHelper.getMedicineById(medicineId);
            if (medicine != null){
                loadMedicineForEdit();
                btnDelete.setVisibility(Button.VISIBLE);
            }
        }

        txtStartDate.setOnClickListener(v -> showDatePicker(txtStartDate));
        txtEndDate.setOnClickListener(v -> showDatePicker(txtEndDate));
        btnAddTime.setOnClickListener(v -> showTimePicker());
        btnSave.setOnClickListener(v -> saveMedicine());
        btnCancel.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            dbhelper.deleteMedicine(medicineId);
            Toast.makeText(this, "Medicine deleted successfully !", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadMedicineForEdit(){
        edtMedicineName.setText(medicine.getName());
        edtDosage.setText(medicine.getDosage());
        edtNotes.setText(medicine.getNotes());
        txtStartDate.setText(medicine.getStartDate());
        txtEndDate.setText(medicine.getEndDate());

        for (String d : medicine.getDays()){
            switch (d){
                case "Mon":chkMon.setChecked(true); break;
                case "Tue":chkTue.setChecked(true); break;
                case "Wed":chkWed.setChecked(true); break;
                case "Thu":chkThu.setChecked(true); break;
                case "Fri":chkFri.setChecked(true); break;
                case "Sat":chkSat.setChecked(true); break;
                case "Sun":chkSun.setChecked(true); break;
            }
        }
        for (String t : medicine.getTimes()) addTimeTextView(t);
    }

    private void addTimeTextView(String time){
        TextView t = new TextView(this);
        t.setText(time);
        t.setTextSize(16f);
        t.setPadding(8,8,8,8,);
        layoutTimes.addView(t);
    }

    private void showDatePicker(TextView txt){
        Calender c = Calender.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> txt.setText(day + "/" + (month + 1) + "/" + year),
                c.get(Calender.YEAR), c.get(Calender.Month), c.get(Calender.DAY_OF_MONTH)).show();
    }





}