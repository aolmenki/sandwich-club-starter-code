package com.udacity.sandwichclub.utils;

import android.content.Context;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json, Context p_context) {
        Sandwich returnSandwich = new Sandwich();

        try {
            JSONObject jsonSandwich = new JSONObject(json);
            // Sandwich Names
            JSONObject jsonName = jsonSandwich.getJSONObject(p_context.getString(R.string.json_key_name));

            // Sandwich Main Name
            String strMainName = jsonName.getString(p_context.getString(R.string.json_key_mainName));
            returnSandwich.setMainName(strMainName);

            // Sandwich Also Known As
            JSONArray jsonArrAKA = jsonName.getJSONArray(p_context.getString(R.string.json_key_alsoKnownAs));
            List<String> listAKA = new ArrayList<>();
            for (int i = 0; i < jsonArrAKA.length(); i++) {
                listAKA.add(jsonArrAKA.getString(i));
            }
            returnSandwich.setAlsoKnownAs(listAKA);

            // Sandwich Origin
            String strOrigin = jsonSandwich.getString(p_context.getString(R.string.json_key_placeOfOrigin));
            returnSandwich.setPlaceOfOrigin(strOrigin);

            // Sandwich Description
            String strDesc = jsonSandwich.getString(p_context.getString(R.string.json_key_description));
            returnSandwich.setDescription(strDesc);

            // Sandwich Image
            String strImg = jsonSandwich.getString(p_context.getString(R.string.json_key_image));
            returnSandwich.setImage(strImg);

            // Sandwich Ingredients
            JSONArray jsonArrIngd = jsonSandwich.getJSONArray(p_context.getString(R.string.json_key_ingredients));
            List<String> listIngd = new ArrayList<>();
            for (int i = 0; i < jsonArrIngd.length(); i++) {
                listIngd.add(jsonArrIngd.getString(i));
            }
            returnSandwich.setIngredients(listIngd);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return returnSandwich;
    }
}
