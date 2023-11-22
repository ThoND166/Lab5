package com.example.lab5.Bai2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lab5.Bai3.AddProductActivity;
import com.example.lab5.Bai4.EditProductActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class loadAllProducts extends AsyncTask<String, String, String> {
    private Context context;
    private ListView lvProducts;
    private ProgressDialog pDialog;
    private JSONParer jParser;
    private ArrayList<Product> listProducts;
    private JSONArray products = null;
    private AdapterProduct adapterProduct;

    public loadAllProducts(Context context, ListView lvProducts) {
        this.context = context;
        this.lvProducts = lvProducts;
        jParser = new JSONParer();
        listProducts = new ArrayList<>();
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
        JSONObject jsonObject =
                jParser.makeHttpRequest(Constants.url_all_products, "GET", params);
        try {
            int success = jsonObject.getInt("success");
            if (success == 1) {
                Log.d("All Products: ", jsonObject.toString());
                products = jsonObject.getJSONArray(Constants.TAG_PRODUCTS);
                for (int i = 0; i < products.length(); i++) {
                    JSONObject c = products.getJSONObject(i);
                    String id = c.getString(Constants.TAG_PID);
                    String name = c.getString(Constants.TAG_NAME);
                    Product product = new Product();
                    product.setId(id);
                    product.setName(name);
                    listProducts.add(product);
                }
            } else {
                // Do something if no products are found
                // For example, launch Add New Product Activity
                Intent intent = new Intent(context, AddProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
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
        adapterProduct = new AdapterProduct(context, listProducts);
        lvProducts.setAdapter(adapterProduct);
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view,
                                    int i, long l) {
                String pid = listProducts.get(i).getId();
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra(Constants.TAG_PID, pid);
                ((android.app.Activity) context).startActivityForResult(intent, 100);
            }
        });
    }
}
