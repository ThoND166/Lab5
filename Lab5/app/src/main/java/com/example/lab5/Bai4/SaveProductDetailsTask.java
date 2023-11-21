package com.example.lab5.Bai4;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.app.Activity;
import android.content.Intent;

import com.example.lab5.Bai2.JSONParer;
import com.example.lab5.Bai2.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveProductDetailsTask extends AsyncTask<String, String, Boolean> {
    private Context context;
    private ProgressDialog pDialog;
    private JSONParer jsonParser;

    public SaveProductDetailsTask(Context context) {
        this.context = context;
        jsonParser = new JSONParer();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Saving product...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean boolSuccess = false;

        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsPid = new HashMap<>();
        hsPid.put(Constants.TAG_PID, strings[0]);
        params.add(hsPid);
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put(Constants.TAG_NAME, strings[1]);
        params.add(hsName);
        HashMap<String, String> hsPrice = new HashMap<>();
        hsPrice.put(Constants.TAG_PRICE, strings[2]);
        params.add(hsPrice);
        HashMap<String, String> hsDes = new HashMap<>();
        hsDes.put(Constants.TAG_DESCRIPTION, strings[3]);
        params.add(hsDes);

        // sending modified data through http request
        // Notice that update product url accepts POST method
        JSONObject object =
                jsonParser.makeHttpRequest(Constants.url_update_product, "POST", params);

        try {
            int success = object.getInt(Constants.TAG_SUCCESS);
            boolSuccess = (success == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return boolSuccess;
    }

    @Override
    protected void onPostExecute(Boolean boolSuccess) {
        super.onPostExecute(boolSuccess);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }

        if (boolSuccess) {
            Intent intent = ((Activity) context).getIntent();
            // send result code 100 to notify about product update
            ((Activity) context).setResult(100, intent);
            ((Activity) context).finish();
        }
    }
}
