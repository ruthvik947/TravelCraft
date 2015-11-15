package com.rn.travelcraft.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.rn.travelcraft.R;
import com.rn.travelcraft.parseData.Product;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private static final String TAG = ProductAdapter.class.getSimpleName();

    private static Context mContext;
    private List<Product> mProductData;

    public ProductAdapter(Context context, List<Product> productData) {
        mContext = context;
        mProductData = productData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View contactView = inflater.inflate(R.layout.list_item_product, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.ViewHolder holder, int position) {
        final Product currentProduct = mProductData.get(position);

        holder.name.setText(currentProduct.getName());

        String costString = "$" + currentProduct.getCost();
        holder.cost.setText(costString);

        String weightString = currentProduct.getWeight() + " lbs";
        holder.weight.setText(weightString);

        final ImageView image = holder.image;
        ParseFile charityLogo = currentProduct.getImage();
        if (charityLogo != null) {
            charityLogo.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    image.setImageBitmap(bmp);
                }
            });
        }

        holder.addToCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();

                if (user.getJSONArray("cart") == null) {
                    JSONArray cartArray = new JSONArray();
                    cartArray.put(currentProduct.getParseId());
                    user.put("cart", cartArray);
                } else {
                    JSONArray cart = user.getJSONArray("cart");
                    cart.put(currentProduct.getParseId());
                }

                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast.makeText(mContext,
                        "Product added to cart. Checkout in the homepage sidebar!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductData.size();
    }

    // ??
    public static class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private ImageView image;
        private TextView name;
        private TextView cost;
        private TextView weight;
        private ImageButton addToCart;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            image = (ImageView) itemView.findViewById(R.id.product_image);
            name = (TextView) itemView.findViewById(R.id.product_name);
            cost = (TextView) itemView.findViewById(R.id.product_cost);
            weight = (TextView) itemView.findViewById(R.id.product_weight);
            addToCart = (ImageButton) itemView.findViewById(R.id.add_to_cart);

        }

        @Override
        public void onClick(View v) {}
    }
}
