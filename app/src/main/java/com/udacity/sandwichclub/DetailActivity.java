package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    // textviews for the sandwich data
    private TextView tv_origin, tv_akaList,tv_description,tv_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
      //  add references to the textviews for the sandwich data
        tv_origin = findViewById(R.id.origin_tv);
        tv_akaList = findViewById(R.id.also_known_tv);
        tv_description = findViewById(R.id.description_tv);
        tv_ingredients = findViewById(R.id.ingredients_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)

                .load(sandwich.getImage())
                .error(R.drawable.image_not_found)
                .into(ingredientsIv);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // sets the origin textview to the sandwich place of origin
         tv_origin.setText(sandwich.getPlaceOfOrigin());
        // set the description textview to the sandwich description
         tv_description.setText(sandwich.getDescription());
         // using string builder to get rid of the brackets and commas in the textview
         StringBuilder mIngredientBuilder = new StringBuilder();
         // getting list of ingredients from the sandwich to parse
         List<String> mIngredients = sandwich.getIngredients();
         //iterates thru each item and adds it the string with a carriage return at the end
         for(String ingredient:mIngredients )
         {
             mIngredientBuilder.append(ingredient+"\n");
         }
         //get rid of last return- first get length
        int length= mIngredientBuilder.length();
        // delete the last character to get rid of white line below ingredients
         mIngredientBuilder.deleteCharAt(length-1);
         // sets the ingredients textview to the ingredients string builder
        tv_ingredients.setText(mIngredientBuilder.toString());
        // using string builder to get rid of the brackets and commas in the textview
        StringBuilder mAkaListBuilder = new StringBuilder();
        // getting list of aka names from the sandwich to parse
         List<String > mAkaList = sandwich.getAlsoKnownAs();
        //iterates thru each item and adds it the string with a carriage return at the end
        for(String aka :mAkaList )
        {
            mAkaListBuilder.append(aka+"\n");
        }
        // sets the aka textview to the aka string builder
        tv_akaList.setText(mAkaListBuilder.toString());
    }
}
