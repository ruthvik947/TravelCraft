package com.rn.travelcraft.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rn.travelcraft.R;
import com.rn.travelcraft.adapters.ProductAdapter;
import com.rn.travelcraft.parseData.Product;
import com.rn.travelcraft.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private static final String TAG = ProductListActivity.class.getSimpleName();

    final List<Product> productData = new ArrayList<>();
    private RecyclerView container;
    private ProductAdapter productAdapter;

    private CardView headerImage;
    private TextView headerText;
    private ImageView headerLogo;
    private View mProgressView;

    private String currentOrgName;
    private ParseObject currentOrg;
    private String currentOrgId;

    private Utils mUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        mUtils = new Utils(ProductListActivity.this);

        currentOrgName = getIntent().getExtras().getString(OrganizationListActivity.SELECTED_CHARITY);
        currentOrgId = getIntent().getExtras().getString(OrganizationListActivity.SELECTED_CHARITY_ID);

        mProgressView = findViewById(R.id.progress);
        mUtils.showProgress(mProgressView, true);

        initializeViews();
        populateList();
    }

    private void initializeViews() {
        headerImage = (CardView) findViewById(R.id.card);
        headerText = (TextView) findViewById(R.id.org_name);
        headerLogo = (ImageView) findViewById(R.id.logo);

        headerText.setText(currentOrgName);

        ParseQuery<ParseObject> query = new ParseQuery("Charities");
        query.whereEqualTo("name", currentOrgName);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> resultList, ParseException e) {
                if (e == null) {
                    currentOrg = resultList.get(0);

                    ParseFile orgImage = currentOrg.getParseFile("background");
                    if (orgImage != null) {
                        orgImage.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                headerImage.setBackground(new BitmapDrawable(bmp));
                            }
                        });
                    }

                    ParseFile orgLogo = currentOrg.getParseFile("logo");
                    if (orgLogo != null) {
                        orgLogo.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                headerLogo.setImageBitmap(bmp);
                            }
                        });
                    }


                } else {
                    Log.d(TAG, "CharityQuery error: " + e);
                }
            }
        });

    }


    private void populateList() {
        container = (RecyclerView) findViewById(R.id.product_container);

        ParseQuery<ParseObject> q = new ParseQuery("Charities");
        q.whereEqualTo("name", currentOrgName);

        try {
            currentOrg = q.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Query parse for products that belong to this org/charity
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Product");
        query.whereEqualTo("parentOrg", currentOrg);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    mUtils.showProgress(mProgressView, false);

                    Log.d(TAG, "" + objects.size() + " ID: " + currentOrgId);

                    for (ParseObject p : objects) {
                        Product product = new Product(p.getString("name"),
                                p.getDouble("cost"), p.getDouble("weight"),
                                p.getParseFile("image"));

                        product.setParseId(p.getObjectId());
                        productData.add(product);
                    }

                    container.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));
                    productAdapter = new ProductAdapter(ProductListActivity.this, productData);
                    container.setAdapter(productAdapter);

                } else {
                    Log.d(TAG, "CharityQuery error: " + e);
                }
            }
        });

    }


}
