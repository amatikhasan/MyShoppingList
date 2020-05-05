package au.edu.utas.ssaadat.assignment2.classes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import au.edu.utas.ssaadat.assignment2.R;
import au.edu.utas.ssaadat.assignment2.db.DBHelper;
import au.edu.utas.ssaadat.assignment2.model.ListData;
import au.edu.utas.ssaadat.assignment2.ui.AddList;
import au.edu.utas.ssaadat.assignment2.ui.ShowList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context contex;
    ArrayList<ListData> data;

    public ListAdapter(Context contex, ArrayList<ListData> data) {
        this.contex = contex;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListData obj = data.get(position);

        holder.name.setText(obj.getName());
        holder.quantity.setText(obj.getQuantity());
        holder.category.setText(obj.getCategory());

        if (obj.getImage() != null) {
            byte[] decodedByte = obj.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            holder.itemImage.setImageBitmap(bitmap);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(contex, AddList.class);
                intent.putExtra("id", obj.getId());
                intent.putExtra("name", obj.getName());
                intent.putExtra("quantity", obj.getQuantity());
                intent.putExtra("category", obj.getCategory());
                intent.putExtra("image", obj.getImage());


               // AddList.bytes=obj.getImage();
                contex.startActivity(intent);
                ((Activity)contex).finish();

            }
        });

        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(contex);
                View view=layoutInflater.inflate(R.layout.add_purchase_input_layout,null);

                AlertDialog.Builder builder=new AlertDialog.Builder(contex);
                builder.setView(view);

                final EditText price=view.findViewById(R.id.etPurchasedPrice);

                builder.setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper dbHelper=new DBHelper(contex);

                                // Get the calander
                                Calendar c = Calendar.getInstance();

                                // From calander get the year, month, day, hour, minute
                                int year = c.get(Calendar.YEAR);
                                int month = c.get(Calendar.MONTH);
                                int day = c.get(Calendar.DAY_OF_MONTH);

                                c.set(year, month, day);
                                String date = day + "-" + (month+1) + "-" + year;

                                String formattedDate;
                                SimpleDateFormat sdtf = new SimpleDateFormat("dd MMM yyyy");
                                Date now = c.getTime();
                                formattedDate = sdtf.format(now);

                                Log.d("check", "onClick: date "+date +" "+formattedDate);


                                int id=dbHelper.addPurchased(obj.getId(),obj.getName(),obj.getQuantity(),price.getText().toString(),formattedDate,obj.getImage());
                                Log.d("check", "onClick: "+id);

                                dbHelper.deleteList(obj.getId());

                                contex.startActivity(new Intent(contex,ShowList.class));
                                ((Activity)contex).finish();
                                Toast.makeText(contex, "Item Added in Purchased List", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, quantity, category;

        ImageView itemImage, addImage;
        CardView card;
        private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
        private TextDrawable mDrawableBuilder;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvListItemName);
            quantity = itemView.findViewById(R.id.tvListItemQuantity);
            category = itemView.findViewById(R.id.tvListItemCategory);
            itemImage = itemView.findViewById(R.id.ivList);
            addImage = itemView.findViewById(R.id.ivAdd);
            card = itemView.findViewById(R.id.card_list);
        }

    }

}
