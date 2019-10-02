package com.example.sampletodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.sampletodo.MainActivity.TASK_NAME;
import static com.example.sampletodo.MainActivity.TASK_POSITION;

public class EditTaskActivity extends AppCompatActivity {
    EditText editText;
    Button saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        editText = findViewById(R.id.update_et);
        saveButton = findViewById(R.id.save_button);

        getSupportActionBar().setTitle("Edit Items");

        editText.setText(getIntent().getStringExtra(TASK_NAME));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(TASK_NAME,editText.getText().toString());
                intent.putExtra(TASK_POSITION,getIntent().getExtras().getInt(TASK_POSITION));

                setResult(RESULT_OK,intent);

                finish();
            }
        });
    }
}
