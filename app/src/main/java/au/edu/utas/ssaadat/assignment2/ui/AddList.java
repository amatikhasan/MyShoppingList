package au.edu.utas.ssaadat.assignment2.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import au.edu.utas.ssaadat.assignment2.R;
import au.edu.utas.ssaadat.assignment2.db.DBHelper;
import au.edu.utas.ssaadat.assignment2.model.ListData;

import static android.util.Log.e;

public class AddList extends AppCompatActivity {
    boolean isUpdate = false;
    EditText name, quantity, category;
    ImageView image;
    byte[] byteArray;
    public static byte[] bytes;
    private static final int IMAGE_REQUEST = 1;
    private Uri filePath;
    String mName,mQuantity,mCategory;
    int id;
    DBHelper dbHelper;
    Bundle extras;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shopping List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.etItemName);
        quantity = findViewById(R.id.etItemQuantity);
        category = findViewById(R.id.etItemCategory);
        image = (ImageView) findViewById(R.id.ivItemImage);

        dbHelper=new DBHelper(getApplicationContext());

        //get Intent Data
        extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            mName = extras.getString("name");
            mQuantity = extras.getString("quantity");
            mCategory = extras.getString("category");
            byteArray=extras.getByteArray("image");
           // Log.d("check", "onCreate: "+byteArray);

            isUpdate = true;
            name.setText(mName);
            quantity.setText(mQuantity);
            category.setText(mCategory);

            if(byteArray!=null) {
                //Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                image.setImageBitmap(bitmap);
                // btnDelete.setVisibility(View.VISIBLE);
            }
        }
        Log.d("Extra Data Check", id + " " + mName + " " + isUpdate);

        checkFilePermissions();
    }

    public void browsImages(View view) {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // special intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        // if you want any file type, you can skip next line
        sIntent.putExtra("CONTENT_TYPE", "image/*");
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (getPackageManager().resolveActivity(sIntent, 0) != null) {
            // it is device with samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Select File");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Select File");
        }
        startActivityForResult(chooserIntent, IMAGE_REQUEST);


    }

    public void captureImage(View view){
        Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }


    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
            int permissionCheck = AddList.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += AddList.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {
            Log.d("Check", "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK && data != null){
            try {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            // bitmap.recycle();

                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("check", "onActivityResult: " + image);

        }

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
               // bitmap.recycle();
                stream.close();

                Log.d("check", "onActivityResult: " + image);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addList(){

        mName=name.getText().toString().trim();
        mQuantity=quantity.getText().toString().trim();
        mCategory=category.getText().toString().trim();

        if(isUpdate) {
            ListData listData = new ListData(id,mName, mQuantity, mCategory, byteArray);
            int id = dbHelper.updateList(listData);
            Log.d("check", "addList: "+id);
        }
        else{
            ListData listData = new ListData(mName, mQuantity, mCategory, byteArray);
            int id = dbHelper.insertList(listData);
            Log.d("check", "addList: "+id);
        }

        Log.d("check", "addList: "+id);

        startActivity(new Intent(this,ShowList.class));
        finish();
    }

    public void deleteItem(){
        dbHelper.deleteList(id);
        startActivity(new Intent(this,ShowList.class));
        finish();
    }

    //For Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isUpdate) getMenuInflater().inflate(R.menu.menu_update, menu);
        if (!isUpdate) {
            getMenuInflater().inflate(R.menu.menu_add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for toolbar arrow
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), ShowList.class));
                finish();
                break;
            case R.id.menuSave:
               addList();
                //Toast.makeText(getApplicationContext(), "Save Button Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDelete:
                deleteItem();
                //Toast.makeText(getApplicationContext(), "Delete Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
