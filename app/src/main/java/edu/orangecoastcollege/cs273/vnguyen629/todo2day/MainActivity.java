package edu.orangecoastcollege.cs273.vnguyen629.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter; // custom ListAdapter

    private EditText taskEditText;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR NOW (TEMPORARY), delete the old database THEN create the new since there will be a
        // database that will permanently exist even after closing project/program
        // Unreliable/inefficient method
        //this.deleteDatabase(DBHelper.DATABASE_TABLE);

        // Make a DBHelper reference
        database = new DBHelper(this);

        // Add a dummy task
        //database.addTask(new Task("Dummy Task", 1));

        // Fill the list with Tasks from the database
        taskList = database.getAllTasks();

        // Let's create our custom list adapter
        // Associate the adapter with the context (this), layout, and the list.
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        // Connect the ListView with our layout
        taskListView = (ListView) findViewById(R.id.taskListView);

        // Associate the adapter with the ListView
        taskListView.setAdapter(taskListAdapter);

        // Connect the EditText with our layout
        taskEditText = (EditText) findViewById(R.id.taskEditText);
    }

    public void addTask(View view) {
        String description = taskEditText.getText().toString();

        // WARNING: If they enter spaces, newlines, or tabs, they can bypass this if statement
        // Only checks for other non-whitespace characters
        if (description.isEmpty())
            Toast.makeText(this, "Task description cannot be empty.", Toast.LENGTH_SHORT).show();
        else {
            // Make a new task to add
            Task newTask = new Task(description, 0);

            // Add the task to the list
            //taskList.add(newTask);

            // Rather than adding to the list, we add to the ListAdapter instead
            taskListAdapter.add(newTask);

            // Add the task to the database
            database.addTask(newTask);

            // Clears the remaining text inside the EditText
            taskEditText.setText("");
        }
    }

    public void changeTaskStatus(View view) {
        // changeTaskStatus is being applied/reused for ALL the checkboxes
        // The view tells us which one out of all is checked/unchecked
        // Convert the view (which is the checkbox itself) to data type CheckBox if not CheckBox
        if (view instanceof CheckBox) {
            CheckBox selectedCheckBox = (CheckBox) view;
            Task selectedTask = (Task) selectedCheckBox.getTag();
            selectedTask.setIsDone((selectedCheckBox.isChecked())? 1 : 0);
            // Update the selectedTask in the database
            database.updateTask(selectedTask);
        }
    }

    public void clearAllTasks(View view) {
        // Clear the list
        taskList.clear();
        // Delete all records (Tasks) within the database
        database.deleteAllTasks();
        // Tell the TaskListAdapter to update itself (since every record is deleted)
        // Checks only what is missing
        taskListAdapter.notifyDataSetChanged();
    }
}