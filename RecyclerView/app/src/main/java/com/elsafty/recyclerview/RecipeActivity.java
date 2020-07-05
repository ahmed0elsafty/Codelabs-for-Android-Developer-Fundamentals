package com.elsafty.recyclerview;



import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.elsafty.recyclerview.Utilities.NetworkUtils;


public class RecipeActivity extends AppCompatActivity {
    private TextView tvIngredients;
    private TextView tvSteps;
    private Recipe mRecipe;
    private ImageView ivRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ActionBar actionBar = getSupportActionBar();

        ivRecipe = findViewById(R.id.iv_recipe);
        tvIngredients = findViewById(R.id.tv_ingredients);
        tvSteps = findViewById(R.id.tv_steps);
        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra(MainActivity.RECIPE);
        String url = mRecipe.getImageUrl();
        for (Ingredients ingredient :mRecipe.getIngredients()) {
            tvIngredients.append(ingredient.getName()+" " +ingredient.getQuantity()+" "+"( "+ingredient.getType()+" )\n\n");
        }
        int count = 1;
        for (String step : mRecipe.getSteps()) {
            tvSteps.append(count + ". "+step+ "\n\n");
            count++;
        }
        actionBar.setTitle(mRecipe.getName());
        new loadBitMap().execute(url);

    }
    public class loadBitMap extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageURL = strings[0];
            return NetworkUtils.laodImage(imageURL);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivRecipe.setImageBitmap(bitmap);
            cancel(true);
        }
    }
}