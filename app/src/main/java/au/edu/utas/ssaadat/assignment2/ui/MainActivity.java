package au.edu.utas.ssaadat.assignment2.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import au.edu.utas.ssaadat.assignment2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void showList(View view){
        startActivity(new Intent(this,ShowList.class));
    }

    public void showPurchased(View view){
        startActivity(new Intent(this,PurchasedItem.class));
    }
}
