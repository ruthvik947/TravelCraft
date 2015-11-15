package com.rn.travelcraft.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.rn.travelcraft.R;
import com.rn.travelcraft.parseData.Charity;

import java.util.List;

public class CharityAdapter extends
        RecyclerView.Adapter<CharityAdapter.ViewHolder> {

    private static final String TAG = CharityAdapter.class.getSimpleName();

    private static Context mContext;
    private List<Charity> mCharityData;
    private static RecyclerViewClickListener mListener;

    public CharityAdapter(Context context, List<Charity> charityData, RecyclerViewClickListener listener) {
        mContext = context;
        mCharityData = charityData;
        mListener = listener;
    }

    @Override
    public CharityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item_organization, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(CharityAdapter.ViewHolder holder, int position) {
        Charity currentCharity = mCharityData.get(position);

        TextView nameView = holder.nameView;
        nameView.setText(currentCharity.getName());

        TextView descriptionView = holder.descriptionView;
        descriptionView.setText(currentCharity.getDescription());

        final ImageView logoView = holder.logoView;
        ParseFile charityLogo = currentCharity.getLogo();
        if (charityLogo != null) {
            charityLogo.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    logoView.setImageBitmap(bmp);
                }
            });
        }

        final CardView cardView = holder.cardView;
        ParseFile charityBackground = currentCharity.getBackground();
        if (charityBackground != null) {
            charityBackground.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Drawable d = new BitmapDrawable(bmp);
                    cardView.setBackground(d);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCharityData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        public TextView nameView;
        public TextView descriptionView;
        public ImageView logoView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            nameView = (TextView) itemView.findViewById(R.id.name);
            descriptionView = (TextView) itemView.findViewById(R.id.description);
            logoView = (ImageView) itemView.findViewById(R.id.logo);
            cardView = (CardView) itemView.findViewById(R.id.card);
        }

        @Override
        public void onClick(View v) {
            mListener.recyclerViewListClicked(v, this.getPosition());
        }
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }
}
