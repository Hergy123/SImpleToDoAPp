package com.example.app;

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
public static final String KEY_ITEM_TEXT = "item_text";
public static final String KEY_ITEM_POSITION ="item_position";
public static final  int EDIT_TEXT_CODE = 20;

    List<String> Add;
    Button btn;
    EditText item;
    RecyclerView reci;
    itemsAdapter itemsAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        item = findViewById(R.id.item);
        reci = findViewById(R.id.reci);


        loadItems();
        itemsAdapter.OnLongClickListener onLongClickListener = new itemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongCliked(int position) {
                Add.remove(position);
                itemsAdap.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
                saveItem();

            }


        };

    itemsAdapter.OnclickListerner onclickListerner =new itemsAdapter.OnclickListerner() {
        @Override
        public void onitemClicked(int position) {
         Log.d("MainActivity" , "Single Click" + position);

            Intent i = new Intent(MainActivity.this , EditActivity.class);
            i.putExtra(KEY_ITEM_TEXT , Add.get(position));
            i.putExtra(KEY_ITEM_POSITION, position);
            startActivityForResult(i , EDIT_TEXT_CODE);
        }

    };
        itemsAdap = new itemsAdapter(Add, onLongClickListener , onclickListerner);
        reci.setAdapter(itemsAdap);
        reci.setLayoutManager(new LinearLayoutManager(this));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = item.getText().toString();
                Add.add(todoItem);
                itemsAdap.notifyItemInserted(Add.size() - 1);
                item.setText(" ");
                Toast.makeText(getApplicationContext(), "Item ADD", Toast.LENGTH_SHORT).show();
                saveItem();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode== RESULT_OK && requestCode == EDIT_TEXT_CODE){
            String itemtext = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            Add.set(position , itemtext);
            itemsAdap.notifyItemChanged(position);
            saveItem();
            Toast.makeText(getApplicationContext(), "Item update successfuly", Toast.LENGTH_SHORT).show();


        }else {
            Log.w("MainActivity " , "UNKNOWM CALL TO OnClickActivityResult");
        }
    }

    private File getdatafile() {
       return new File(getFilesDir(), "data.txt");


























































    }
   private void loadItems(){

        try {
            Add = new ArrayList<>(FileUtils.readLines(getdatafile(),Charset.defaultCharset()));

        }catch (IOException e){
            Log.e("MainActivity" , "Error reading items" , e);
            Add = new ArrayList<>();

        }



   }
   public void saveItem(){

       try {
           FileUtils.writeLines(getdatafile() , Add);
       } catch (IOException e) {
           Log.e("MainActivity" , "Error reading items" , e);
       }
   }
}
