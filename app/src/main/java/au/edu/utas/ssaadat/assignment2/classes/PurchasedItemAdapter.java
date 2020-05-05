package au.edu.utas.ssaadat.assignment2.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Locale;

import au.edu.utas.ssaadat.assignment2.R;
import au.edu.utas.ssaadat.assignment2.model.ListData;
import au.edu.utas.ssaadat.assignment2.model.PurchasedData;
import au.edu.utas.ssaadat.assignment2.ui.AddList;
import au.edu.utas.ssaadat.assignment2.ui.EditPurchasedItem;

public class PurchasedItemAdapter extends RecyclerView.Adapter<PurchasedItemAdapter.ViewHolder> {
    Context contex;
    ArrayList<PurchasedData> data;
    private ArrayList<PurchasedData> arraylist;

    public PurchasedItemAdapter(Context contex, ArrayList<PurchasedData> data) {
        this.contex = contex;
        this.data = data;
        this.arraylist = new ArrayList<PurchasedData>();
        this.arraylist.addAll(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.purchased_item_layout, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Bitmap[] bitmap = new Bitmap[1];
        final PurchasedData obj = data.get(position);
        String price = obj.getPrice() + " AUD";
        holder.name.setText(obj.getName());
        holder.quantity.setText(obj.getQuantity());
        holder.price.setText(price);
        holder.date.setText(obj.getDate());

        if (obj.getImage() != null) {
            byte[] decodedByte = obj.getImage();
            bitmap[0] = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            holder.itemImage.setImageBitmap(bitmap[0]);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (obj.getImage() != null) {
                    byte[] decodedByte = obj.getImage();
                    bitmap[0] = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
                }

                Intent intent = new Intent(contex, EditPurchasedItem.class);
                intent.putExtra("id", obj.getId());
                intent.putExtra("name", obj.getName());
                intent.putExtra("quantity", obj.getQuantity());
                intent.putExtra("price", obj.getPrice());
                //EditPurchasedItem.bytes=obj.getImage();
                intent.putExtra("image", obj.getImage());
                //String bytes= Base64.encodeToString(obj.getImage(),Base64.DEFAULT);
                //SharedPrefManager.getmInstance(contex).setImage(bytes);

                contex.startActivity(intent);

                ((Activity)contex).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, quantity, price, date;

        ImageView itemImage, addImage;
        CardView card;
        private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
        private TextDrawable mDrawableBuilder;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvPurchasedItemName);
            quantity = itemView.findViewById(R.id.tvPurchasedItemQuantity);
            price = itemView.findViewById(R.id.tvPurchasedItemPrice);
            date = itemView.findViewById(R.id.tvPurchasedItemDate);
            itemImage = itemView.findViewById(R.id.ivPurchasedItem);
            //addImage= itemView.findViewById(R.id.ivAdd);
            card = itemView.findViewById(R.id.card_purchased);
        }

    }

    // Filter Class to filter and show the result in list view
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(arraylist);
        } else {
            for (PurchasedData wp : arraylist) {
                if (wp.getDate().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
