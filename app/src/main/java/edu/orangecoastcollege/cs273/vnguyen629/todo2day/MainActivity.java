package edu.orangecoastcollege.cs273.vnguyen629.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR NOW (TEMPORARY), delete the old database THEN create the new
        this.deleteDatabase(DBHelper.DATABASE_TABLE);

        // Make a DBHelper reference
        DBHelper db = new DBHelper(this);

        // Makes a new task and permanently adds it to the database
        // The tasks will exist even after the program is closed
        // Rerunning with adding the same tasks (same ID as before will cause problems)
        // Thus a temporary measure to avoid this is to delete the database before adding (as done above)
        db.addTask(new Task(1, "Study for CS273 Midterm", 0));
        db.addTask(new Task(2, "Play League of Legends", 0));
        db.addTask(new Task(3, "Register for research symposium", 0));
        db.addTask(new Task(4, "Learn doubly-linked lists", 0));
        db.addTask(new Task(5, "Master the FragmentManager", 0));

        // Get all the tasks from the database and print them with Log.i()
        ArrayList<Task> allTasks = db.getAllTasks();
        // Loop through each task, print to Log.i()
        for (Task singleTask : allTasks)
            Log.i("DATABASE TASK", singleTask.toString());
    }
}