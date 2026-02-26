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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddEditMedicineActivity extends AppCompatActivity {

    EditText edtMedicineName, edtDosage, edtNotes;
    TextView txtStartDate, txtEndDate;
    LinearLayout layoutTimes;
    Button btnAddTime, btnSave, btnCancel, btnDelete;
    CheckBox chkMon, chkTue, chkWed, chkThu, chkFri, chkSat, chkSun;
    DatabaseHelper dbHelper;
    int medicineId = -1;
    Medicine medicine;

    private Calendar startDateCalendar = null;

    //Dosage input filter
    private final InputFilter dosageInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++){
                char c = source.charAt(i);

                if(!(Character.isLetterOrDigit(c) || c == ' ' || c == '.' || c == '/' || c == '%' || c == '+' || c == '_' || c == '-')){
                    return "";//Blocking the keyboard
                }
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_medicine);

        dbHelper = new DatabaseHelper(this);
        edtMedicineName = findViewById(R.id.edtMedicineName);
        edtDosage = findViewById(R.id.edtDosage);
        edtDosage.setFilters(new InputFilter[]{dosageInputFilter});
        edtNotes = findViewById(R.id.edtNotes);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);

        txtEndDate.setEnabled(false); //Disabling until start date is selected

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

        txtStartDate.setOnClickListener(v -> showDatePicker(txtStartDate, true));
        txtEndDate.setOnClickListener(v -> {
            if (startDateCalendar == null){
                Toast.makeText(this, "Please select the date first", Toast.LENGTH_SHORT).show();
                return;
            }
            showDatePicker(txtEndDate, false);
        });

        btnAddTime.setOnClickListener(v -> showTimePicker());
        btnSave.setOnClickListener(v -> saveMedicine());
        btnCancel.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteMedicine(medicineId);
            Toast.makeText(this, "Medicine deleted successfully !", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadMedicineForEdit(){//Loading existing data
        edtMedicineName.setText(medicine.getName());
        edtDosage.setText(medicine.getDosage());
        edtNotes.setText(medicine.getNotes());
        txtStartDate.setText(medicine.getStartDate());
        txtEndDate.setText(medicine.getEndDate());

        //Setting start date calendar
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            startDateCalendar = Calendar.getInstance();
            startDateCalendar.setTime(sdf.parse(medicine.getStartDate()));
        } catch (ParseException ignored){}


        for (String d : medicine.getDays()){//Marking selected date
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
        for (String t : medicine.getTimes()) addTimeTextView(t);//Adding saved reminder times

        txtEndDate.setEnabled(true);
    }

    private void addTimeTextView(String time){
        TextView t = new TextView(this);
        t.setText(time);
        t.setTextSize(16f);
        t.setPadding(8,8,8,8);
        layoutTimes.addView(t);
    }

    private void showDatePicker(TextView txt, boolean isStartDate) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, month, day) -> {
            txt.setText(day + "/" + (month + 1) + "/" + year);
            if (isStartDate) {
                startDateCalendar = Calendar.getInstance();
                startDateCalendar.set(year, month, day);

                txtEndDate.setEnabled(true);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        if (!isStartDate && startDateCalendar != null) {
            dpd.getDatePicker().setMinDate(startDateCalendar.getTimeInMillis());
        }
        dpd.show();
    }

    private void showTimePicker(){
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this, (view, hour, minute) -> addTimeTextView(String.format("%02d:%02d", hour, minute)),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }

    private List<String> getSelectedDays(){
        List<String> days = new ArrayList<>();
        if (chkMon.isChecked()) days.add("Mon");
        if (chkTue.isChecked()) days.add("Tue");
        if (chkWed.isChecked()) days.add("Wed");
        if (chkThu.isChecked()) days.add("Thu");
        if (chkFri.isChecked()) days.add("Fri");
        if (chkSat.isChecked()) days.add("Sat");
        if (chkSun.isChecked()) days.add("Sun");
        return days;
    }

    private List<String> getReminderTimes() {
        List<String> times = new ArrayList<>();
        for (int i = 0; i < layoutTimes.getChildCount(); i++) {
            TextView t = (TextView) layoutTimes.getChildAt(i);
            times.add(t.getText().toString());
        }
        return times;
    }

    private void saveMedicine(){
        String name = edtMedicineName.getText().toString().trim();
        String dosage = edtDosage.getText().toString().trim();
        String notes = edtNotes.getText().toString().trim();
        String StartDate = txtStartDate.getText().toString().trim();
        String EndDate = txtEndDate.getText().toString().trim();
        if (EndDate.equals(getString(R.string.end_date_text))){
            EndDate = "";
        }

        String username = getIntent().getStringExtra("username"); //Get username from intent

        if (name.isEmpty() || StartDate.equals(getString(R.string.start_date_text))){
            Toast.makeText(this, "Please enter medicine name and start date", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validating end date after start date
        if(!EndDate.isEmpty() && !EndDate.equals(getString(R.string.end_date_text))){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try{
                Date startDate = sdf.parse(StartDate);
                Date endDate = sdf.parse(EndDate);
                if(endDate.before(startDate)) {
                    Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch(ParseException e){
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Medicine med;
        if (medicineId == -1){
            //Adding new medicine
            med = new Medicine(name, dosage, StartDate, EndDate, getSelectedDays(), getReminderTimes(), notes, username);
            dbHelper.insertMedicine(med);
        }
        else{
            //Editing medicine details
            med = new Medicine(medicineId, name, dosage, StartDate, EndDate, getSelectedDays(), getReminderTimes(), notes, username);
            dbHelper.updateMedicine(med);
        }

        Toast.makeText(this, "Medicine saved!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
//100% Completed