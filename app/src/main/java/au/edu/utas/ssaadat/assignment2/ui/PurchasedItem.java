package au.edu.utas.ssaadat.assignment2.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import au.edu.utas.ssaadat.assignment2.R;
import au.edu.utas.ssaadat.assignment2.classes.ListAdapter;
import au.edu.utas.ssaadat.assignment2.classes.PurchasedItemAdapter;
import au.edu.utas.ssaadat.assignment2.db.DBHelper;
import au.edu.utas.ssaadat.assignment2.model.ListData;
import au.edu.utas.ssaadat.assignment2.model.PurchasedData;

public class PurchasedItem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ArrayList<PurchasedData> obj = new ArrayList<>();
    PurchasedItemAdapter adapter;
    TextView emptyList, tvExpenditure;
    Toolbar toolbar;
    int expenditure = 0;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_item);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Purchased Item");

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        emptyList = findViewById(R.id.emptyPurchasedItem);
        recyclerView = findViewById(R.id.rvPurchasedItem);
        tvExpenditure = findViewById(R.id.tvExpenditure);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        obj = dbHelper.showPurchasedList();


        if (obj.size() > 0) {

            for (int i = 0; i < obj.size(); i++) {
                expenditure = expenditure + Integer.parseInt(obj.get(i).getPrice());
            }
           adapter = new PurchasedItemAdapter(this, obj);
            recyclerView.setAdapter(adapter);

            String amount = "Total Expenditure " + expenditure + " AUD";
            tvExpenditure.setText(amount);

        } else {
            recyclerView.setVisibility(View.GONE);
            tvExpenditure.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        }


        tvExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PurchasedItem.this,ChartActivity.class));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.menu_list) {
            startActivity(new Intent(this, ShowList.class));
            finish();
        }

        if (id == R.id.menu_purchased) {
            startActivity(new Intent(this, PurchasedItem.class));
            finish();
        }

        if (id == R.id.menu_chart) {
            startActivity(new Intent(this,ChartActivity.class));
            finish();
        }
        return false;
    }

    //For Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        MenuItem item=menu.findItem(R.id.menuFilter);

        SearchView searchView= (SearchView) item.getActionView();
        searchView.setQueryHint("Search Date");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.filter(newText);
                return false;
            }
        });

        return true;
    }


}
