package co.jacobweinstein.scheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class EditAssignment extends AppCompatActivity {
    MyDBHandler myDBHandler;
    Assignment open;
    EditText myTitle;
    TextView myDate;
    Spinner myClass;
    EditText myDesc;
    DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        myDBHandler = new MyDBHandler(this, null, null, 1);

        myTitle = (EditText) findViewById(R.id.ass_title_edit);
        myDate = (TextView) findViewById(R.id.datePicker_edit);
        myClass = (Spinner) findViewById(R.id.select_class_edit);
        myDesc = (EditText) findViewById(R.id.desc2_edit);

        Intent intent = getIntent();
        open = myDBHandler.getAssignment(
                intent.getStringExtra(AssignmentAdapter.ASSIGNMENT_TITLE),
                intent.getStringExtra(AssignmentAdapter.ASSIGNMENT_CLASSNAME)
        );

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.classes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myClass.setAdapter(adapter);
        int position = 0;
        for (int i = 0;i<adapter.getCount();i++){
            if (adapter.getItem(i).equals(open.getClassName())){
                position = i;
            }
        }


        Calendar c = Calendar.getInstance();
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int year = c.get(Calendar.YEAR);

        myTitle.setText(open.getTitle());
        myDate.setText(open.getDate());
        myDesc.setText(open.getDesc());
        myClass.setSelection(position);

        myDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog d = new DatePickerDialog(EditAssignment.this, android.R.style.Theme_Material_Dialog, dateSetListener, year, month, day);
                d.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                myDate.setText(month + "/" + dayOfMonth + "/" + year);
            }
        };

    }
    public void finalizeEdit(View view){
        Assignment newAssignment = new Assignment(
                myTitle.getText().toString(),
                myDate.getText().toString(),
                myClass.getSelectedItem().toString(),
                myDesc.getText().toString()
        );
        myDBHandler.changeAssignment(
                open.getTitle(), open.getClassName(),
                newAssignment.getTitle(),
                newAssignment.getClassName(),
                newAssignment.getDayOfMonth(),
                newAssignment.getMonth(),
                newAssignment.getYear(),
                newAssignment.getDesc()

        );
        Intent intent = new Intent(this, AssignmentDepth.class);
        intent.putExtra(AssignmentAdapter.ASSIGNMENT_TITLE, newAssignment.getTitle());
        intent.putExtra(AssignmentAdapter.ASSIGNMENT_CLASSNAME, newAssignment.getClassName());
        startActivity(intent);

    }
}
