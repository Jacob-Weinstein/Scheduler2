package co.jacobweinstein.scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AssignmentDepth extends AppCompatActivity {
    MyDBHandler myDBHandler;
    Assignment open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_depth);

        myDBHandler = new MyDBHandler(this, null, null, 1);

        TextView header = (TextView) findViewById(R.id.header_depth);
        TextView subheader = (TextView) findViewById(R.id.subheader_depth);
        TextView desc = (TextView) findViewById(R.id.desc_depth);

        Intent intent = getIntent();
        open = myDBHandler.getAssignment(
                intent.getStringExtra(AssignmentAdapter.ASSIGNMENT_TITLE),
                intent.getStringExtra(AssignmentAdapter.ASSIGNMENT_CLASSNAME)
        );

        header.setText(open.getTitle() + ", due " + open.getDate());
        subheader.setText(open.getClassName());
        desc.setText(open.getDesc());


    }
    public void editAssignment(View view){
        Intent intent = new Intent(this, EditAssignment.class);
        intent.putExtra(AssignmentAdapter.ASSIGNMENT_TITLE, open.getTitle());
        intent.putExtra(AssignmentAdapter.ASSIGNMENT_CLASSNAME, open.getClassName());
        startActivity(intent);
    }

    public void markAssignmentDone(View view){
        myDBHandler.deleteAssignment(open.getTitle(), open.getClassName());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
