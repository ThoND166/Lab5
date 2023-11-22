package com.example.lab5.Bai3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import com.example.lab5.Bai2.AllProductsActivity;
import com.example.lab5.Bai2.Constants;
import com.example.lab5.Bai2.JSONParer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateNewProducTask extends AsyncTask<String, String, String> {

    private Context context;
    private ProgressDialog pDialog;
    private JSONParer jsonParser;

    public CreateNewProducTask(Context context) {
        this.context = context;
        jsonParser = new JSONParer();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put("name", strings[0]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put("price", strings[1]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put("description", strings[2]);
        params.add(hsDes);

        JSONObject jsonObject = jsonParser.makeHttpRequest(Constants.url_create_products, "POST", params);
        Log.d("Create response", jsonObject.toString());

        try {
            int success = jsonObject.getInt(Constants.TAG_SUCCESS);
            if (success == 1) {
                // Successfully created product
                Intent intent = new Intent(context, AllProductsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
