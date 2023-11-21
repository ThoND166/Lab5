package com.example.lab5.Bai4;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import com.example.lab5.Bai2.Constants;
import com.example.lab5.Bai2.JSONParer;
import com.example.lab5.Bai2.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetProducDetailsTask extends AsyncTask<String, String, Product> {
    private Context context;
    private ProgressDialog pDialog;
    private JSONParer jsonParser;
    private EditText edtName, edtPrice, edtDes;

    public GetProducDetailsTask(Context context, EditText edtName,
                                 EditText edtPrice, EditText edtDes) {
        this.context = context;
        this.edtName = edtName;
        this.edtPrice = edtPrice;
        this.edtDes = edtDes;
        jsonParser = new JSONParer();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Product Details. Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Product doInBackground(String... strings) {
        Product product = new Product();

        // Building Parameters
        List<HashMap<String, String>> params = new ArrayList<>();
        HashMap<String, String> hsPid = new HashMap<>();
        hsPid.put(Constants.TAG_PID, strings[0]);
        params.add(hsPid);

        try {
            // getting product details by Making HTTP request
            // Note that product details url will use GET request
            JSONObject json = jsonParser.makeHttpRequest(Constants.url_product_detail, "GET", params);
            // json success tag
            int success = json.getInt(Constants.TAG_SUCCESS);
            if (success == 1) {
                // successfully received product detail
                JSONArray productObj = json.getJSONArray(Constants.TAG_PRODUCT);
                // get first product object from JSON Array
                JSONObject obj = productObj.getJSONObject(0);
                // product with this pid found
                product.setName(obj.getString(Constants.TAG_NAME));
                product.setPrice(obj.getString(Constants.TAG_PRICE));
                product.setDescription(obj.getString(Constants.TAG_DESCRIPTION));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    protected void onPostExecute(Product product) {
        super.onPostExecute(product);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        // Update EditText fields with product details
        if (product != null) {
            edtName.setText(product.getName());
            edtPrice.setText(product.getPrice());
            edtDes.setText(product.getDescription());
        }
    }
}
