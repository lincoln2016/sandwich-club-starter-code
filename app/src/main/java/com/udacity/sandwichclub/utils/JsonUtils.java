package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class JsonUtils
{
    public static Sandwich parseSandwichJson(String json)
    {
        Sandwich sandwich = new Sandwich();

        try
        {
            // get initial json object
            org.json.JSONObject jObjectInitial = new org.json.JSONObject(json);
           // get the name object from the initial object
            org.json.JSONObject jObjectSandwichName = jObjectInitial.getJSONObject("name");
            // get the mainName string from the sandwich object
            String mSandwichName = jObjectSandwichName.getString("mainName");
            // adds the main name to the sandwich object
            sandwich.setMainName(mSandwichName);
            // gets the sandwich alsoKnownAs name array
            org.json.JSONArray mSandwichAkaArray = jObjectSandwichName.getJSONArray("alsoKnownAs");
            // gets the sandwich ingredients list Array
            org.json.JSONArray mIngredientsArray = jObjectInitial.getJSONArray("ingredients");
            // converts the json aka Array to arrayList
            ArrayList<String> akaList = convertJsonArray(mSandwichAkaArray);
            // adds the aka list to the sandwich object
            sandwich.setAlsoKnownAs(akaList);
            // converts the Ingredients JsonArray to the arrayList
            ArrayList<String> ingredientList = convertJsonArray(mIngredientsArray);
            // adds the ingredient list to the sandwich object
            sandwich.setIngredients(ingredientList);
            // get place of Origin string from the jsonObject
            String mOrigin = jObjectInitial.getString("placeOfOrigin");
            // adds the placeOfOrigin to the sandwich object
            sandwich.setPlaceOfOrigin(mOrigin);
            // gets the description string from the jsonObject name
             String mDescription = jObjectInitial.getString("description");
            // adds the description to the sandwich object
            sandwich.setDescription(mDescription);
            //get image url from json object
            String mImage = jObjectInitial.getString("image");
            // adds image string to sandwich object
            sandwich.setImage(mImage);
        }
           catch (org.json.JSONException error)
        {
                error.printStackTrace();
        }

        android.util.Log.v("JsonUtils",json);

        return sandwich;
    }

    private static ArrayList<String> convertJsonArray(JSONArray jsonArray)
    {
        ArrayList<String> list = new ArrayList<>();
            if (jsonArray != null)
        {
            for (int i=0;i<jsonArray.length();i++)
            {
                try
                {
                    list.add(jsonArray.getString(i));
                }
                    catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
          return list;
    }
}
