package com.elsafty.roomwordssample;

import android.content.Intent;
import android.os.Bundle;

import com.elsafty.roomwordssample.db.Word;
import com.elsafty.roomwordssample.db.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private RecyclerView wordRecyclerView;
    private WordListAdapter mAdapter;
    private WordViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wordRecyclerView = findViewById(R.id.recycleView);
        mAdapter = new WordListAdapter(this);
        wordRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        wordRecyclerView.setAdapter(mAdapter);
        mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mViewModel.getmAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                mAdapter.setWords(words);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newWordIntent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(newWordIntent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word word = mAdapter.getWordAtPosition(position);
//                Toast.makeText(MainActivity.this, "Deleting " +
//                        word.getWord(), Toast.LENGTH_LONG).show();
                Snackbar.make(viewHolder.itemView,"Deleting " +
                        word.getWord(), BaseTransientBottomBar.LENGTH_LONG).show();
                mViewModel.deleteWord(word);
            }
        });

        itemTouchHelper.attachToRecyclerView(wordRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE) {
            Word word = new Word();
            word.setWord(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mViewModel.insertWord(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_deleteAll) {
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();
            mViewModel.deleteAllWords();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}