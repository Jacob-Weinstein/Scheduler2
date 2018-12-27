package co.jacobweinstein.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyDBHandler myDBHandler;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView subheader;

    private List<Assignment> assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHandler = new MyDBHandler(this, null, null, 1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignments = myDBHandler.databaseToAssignments();

        adapter = new AssignmentAdapter(assignments, this);
        recyclerView.setAdapter(adapter);

        subheader = (TextView) findViewById(R.id.upcoming_txt);
        String s;
        if (adapter.getItemCount() == 0){
            s = getResources().getString(R.string.upcoming_alternate);
        }else{
            s = getResources().getString(R.string.upcoming_ass_text);

        }
        subheader.setText(s);

    }

    public void switchToAdd(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }
}
