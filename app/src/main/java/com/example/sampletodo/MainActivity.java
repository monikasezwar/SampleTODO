package com.example.sampletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static final String TASK_NAME = "task_name" ;
    protected static final String TASK_POSITION = "task_position";
    protected static final int EDIT_TEXT_CODE = 101;
    private EditText editText;
    private Button addButton;
    private RecyclerView recyclerView;
    private String newTask;
    private List<String> todoList;
    private AddOnAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.rv);

        loadItems();

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        AddOnAdapter.onTaskLongClickListener longClickListener = new AddOnAdapter.onTaskLongClickListener() {
            @Override
            public void onTaskLongClicked(int position) {

                todoList.remove(position);
                adapter.notifyItemRemoved(position);
                saveItems(todoList);
                Toast.makeText(getApplicationContext(),"Item Removed",Toast.LENGTH_SHORT).show();
            }
        };

        AddOnAdapter.onTaskClickListener onClickListener = new AddOnAdapter.onTaskClickListener() {

            @Override
            public void onTaskClicked(int position) {
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra(TASK_POSITION,position);
                intent.putExtra(TASK_NAME,todoList.get(position));
                startActivityForResult(intent,EDIT_TEXT_CODE);
            }
        };

        adapter = new AddOnAdapter(this, todoList, longClickListener, onClickListener);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newTask = editText.getText().toString();
                if(!newTask.isEmpty()){
                    todoList.add(newTask);
                    adapter.notifyItemInserted(todoList.size()-1);
                    editText.setText("");
                    saveItems(todoList);
                    Toast.makeText(getApplicationContext(),"Task added",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Add a task first",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_TEXT_CODE && resultCode == RESULT_OK){
            String todoTask = data.getStringExtra(TASK_NAME);
            int taskPosition = data.getExtras().getInt(TASK_POSITION);
            //update model
            todoList.set(taskPosition,todoTask);
            //notify adapter
            adapter.notifyItemChanged(taskPosition);
            //save for persistence storage
            saveItems(todoList);

        }else{
            Log.w("MainActivity","Unable to call EditTaskActivity");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }

    private void loadItems(){
        try{
            todoList = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }catch(IOException e){
            Log.e("MainActivity","");
        }

    }

    private void saveItems(List<String> todoList){
        try{
            FileUtils.writeLines(getDataFile(),todoList);
        }catch (IOException e){
            Log.e("MainActivity","Error writing items=",e);
        }
    }


}
