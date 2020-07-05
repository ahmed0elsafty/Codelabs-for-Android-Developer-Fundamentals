package com.elsafty.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.elsafty.recyclerview.Utilities.DataUtils;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnListItemClick {

    public static final String RECIPE = "recipe";
    private static final String TAG = "MainActivity";
    RecyclerView mRecyclerView;
    RecipeListAdapter mAdapter;
    ArrayList<String> mImageUrl = DataUtils.imageURLs;
    private ArrayList<Recipe> mRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Recipes");
        try {
            mRecipes = DataUtils.getRecipes(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new RecipeListAdapter(this, mRecipes, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                int wordListSize = mWordList.size();
                mWordList.addLast("word "+wordListSize);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(wordListSize);
            }
        });
    */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            recreate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemCLick(int itemClicked) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RECIPE, mRecipes.get(itemClicked));
        startActivity(intent);
    }
}