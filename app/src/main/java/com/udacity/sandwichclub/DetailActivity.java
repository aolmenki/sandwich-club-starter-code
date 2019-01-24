package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json, getApplicationContext());
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich p_sandwich) {
        // Get Text Views
        TextView tvAKA = findViewById(R.id.also_known_tv);
        TextView tvOrigin = findViewById(R.id.origin_tv);
        TextView tvDescription = findViewById(R.id.description_tv);
        TextView tvIngredients = findViewById(R.id.ingredients_tv);

        // Set also known as
        String strAKA = String.join(", ", p_sandwich.getAlsoKnownAs());
        if (strAKA.equals("")) {
            TextView tvAKALabel = findViewById(R.id.also_known_label_tv);
            tvAKALabel.setVisibility(View.GONE);
            tvAKA.setVisibility(View.GONE);
        } else {
            tvAKA.setText(strAKA);
        }

        // Set Origin
        String strOrigin = p_sandwich.getPlaceOfOrigin();
        if (strOrigin.equals("")) {
            TextView tvOriginLabel = findViewById(R.id.origin_label_tv);
            tvOriginLabel.setVisibility(View.GONE);
            tvOrigin.setVisibility(View.GONE);
        } else {
            tvOrigin.setText(strOrigin);
        }

        // Set Description
        tvDescription.setText(p_sandwich.getDescription());

        // Build the String for Ingredients
        StringBuilder strBuildIngredients = new StringBuilder();
        strBuildIngredients.append("- "); // begins a list with a bullet
        // the joined list will have the bullet except in front of the first element/ingredient
        strBuildIngredients.append(String.join("\n- ", p_sandwich.getIngredients()));
        tvIngredients.setText(strBuildIngredients);
    }
}
