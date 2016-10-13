package edu.orangecoastcollege.cs273.vnguyen629.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

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
    }
}