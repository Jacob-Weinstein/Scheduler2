package co.jacobweinstein.scheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    private static final String TAG = "AddTask";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView date;
    MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        myDBHandler = new MyDBHandler(this, null, null, 1);

        date = (TextView) findViewById(R.id.datePicker);

        Calendar c = Calendar.getInstance();
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int year = c.get(Calendar.YEAR);

        date.setText((month + 1) + "/" + day + "/" + year);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog d = new DatePickerDialog(AddTask.this, android.R.style.Theme_Material_Dialog, dateSetListener, year, month, day);
                //d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                date.setText(month + "/" + dayOfMonth + "/" + year);
                //Log.d(TAG, month + "/" + dayOfMonth + "/" + year);
            }
        };

        Spinner spinner = findViewById(R.id.select_class_edit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select a class");
    }
    public void submitAssignment(View view){
        EditText name = (EditText) findViewById(R.id.ass_title);
        Spinner className = (Spinner) findViewById(R.id.select_class_edit);
        String nameVal = name.getText().toString().replaceAll("\\s","");
        EditText desc = (EditText) findViewById(R.id.desc);
        if (!nameVal.equals("") && !date.getText().toString().equals("") && !className.getSelectedItem().toString().equals("")) {
            Assignment newAss = new Assignment(name.getText().toString(), date.getText().toString(), className.getSelectedItem().toString(), desc.getText().toString());

            myDBHandler.addAssignment(newAss);
            Log.i(TAG, "added " + newAss.toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }
}
