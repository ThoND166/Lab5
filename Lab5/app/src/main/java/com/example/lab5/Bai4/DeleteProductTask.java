package com.example.lab5.Bai4;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;

import com.example.lab5.Bai2.Constants;
import com.example.lab5.Bai2.JSONParer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeleteProductTask extends AsyncTask<String, String, Boolean> {
    private Context context;
    private ProgressDialog pDialog;
    private JSONParer jsonParser;
    private boolean checkSuccess = false;

    public DeleteProductTask(Context context) {
        this.context = context;
        jsonParser = new JSONParer();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Deleting product...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            // Building Parameters
            List<HashMap<String, String>> params = new ArrayList<>();
            HashMap<String, String> hsPid = new HashMap<>();
            hsPid.put(Constants.TAG_PID, strings[0]);
            params.add(hsPid);

            // getting product detail by making HTTP request
            JSONObject object =
                    jsonParser.makeHttpRequest(Constants.url_delete_product, "POST", params);

            // check your log for json response
            Log.d("Delete product", object.toString());

            // json success tag
            int success = object.getInt(Constants.TAG_SUCCESS);
            checkSuccess = (success == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkSuccess;
    }

    @Override
    protected void onPostExecute(Boolean checkSuccess) {
        super.onPostExecute(checkSuccess);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (checkSuccess) {
            // notify previous activity by sending code 100
            Intent intent = ((Activity) context).getIntent();
            // send result code 100 to notify about product deletion
            ((Activity) context).setResult(100, intent);
            ((Activity) context).finish();
        }
    }
}
