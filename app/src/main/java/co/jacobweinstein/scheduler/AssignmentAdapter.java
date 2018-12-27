package co.jacobweinstein.scheduler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private static final String TAG = "AssignmentAdapter";
    public static final String ASSIGNMENT_TITLE = "assignment_title";
    public static final String ASSIGNMENT_CLASSNAME = "assignment_className";

    private List<Assignment> assignments;
    private Context context;

    public AssignmentAdapter(List<Assignment> assignments, Context context) {
        this.assignments = assignments;
        this.context = context;
        Log.i(TAG, "constructor reached");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_items, viewGroup, false);
        Log.i(TAG, "onCreate reached");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Assignment assignment = assignments.get(i);

        viewHolder.textViewTitle.setText(assignment.getTitle());
        viewHolder.textViewDate.setText(assignment.getDate());
        viewHolder.textViewClassName.setText(assignment.getClassName());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i(TAG, "clicked on " + assignment.getTitle());

                Intent intent = new Intent(context, AssignmentDepth.class);
                intent.putExtra(ASSIGNMENT_TITLE, assignment.getTitle());
                intent.putExtra(ASSIGNMENT_CLASSNAME, assignment.getClassName());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout parentLayout;
        public TextView textViewTitle;
        public TextView textViewDate;
        public TextView textViewClassName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.parentOfCard);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewClassName = (TextView) itemView.findViewById(R.id.textViewClassName);
        }
    }
}
