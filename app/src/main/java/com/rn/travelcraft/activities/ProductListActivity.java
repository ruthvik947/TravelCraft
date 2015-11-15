package com.rn.travelcraft.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.rn.travelcraft.R;
import com.rn.travelcraft.adapters.ProductAdapter;
import com.rn.travelcraft.parseData.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    final List<Product> productData = new ArrayList<>();
    private RecyclerView container;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        populateList();
    }

    private void populateList() {
        container = (RecyclerView) findViewById(R.id.product_container);

        // Query parse for products that belong to this org/charity

    }


}
