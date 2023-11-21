package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lab5.Bai2.AllProductsActivity;
import com.example.lab5.Bai3.AddProductActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnViewProducts;
    private Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        btnViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        AllProductsActivity.class);
                startActivity(intent);
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        AddProductActivity.class);
                startActivity(intent);
            }
        });


    }
}