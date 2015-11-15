package com.rn.travelcraft.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rn.travelcraft.R;
import com.rn.travelcraft.adapters.CharityAdapter.ViewHolder;
import com.rn.travelcraft.parseData.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    private static Context mContext;
    private List<Product> mProductData;

    public ProductAdapter(Context context, List<Product> productData) {
        mContext = context;
        mProductData = productData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_item_product, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product currentProduct = mProductData.get(position);


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
