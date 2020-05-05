package au.edu.utas.ssaadat.assignment2.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import au.edu.utas.ssaadat.assignment2.R;
import au.edu.utas.ssaadat.assignment2.classes.ListAdapter;
import au.edu.utas.ssaadat.assignment2.db.DBHelper;
import au.edu.utas.ssaadat.assignment2.model.ListData;

public class ShowList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
RecyclerView recyclerView;
    ArrayList<ListData> obj = new ArrayList<>();
    TextView emptyList;
    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shopping List");

        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        emptyList=findViewById(R.id.emptyList);
        recyclerView=findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        obj=dbHelper.showList();


        if(obj.size()>0) {
            ListAdapter adapter = new ListAdapter(this, obj);
            recyclerView.setAdapter(adapter);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        }
    }

    public void fabBtn(View view){
        Intent intent=new Intent(this,AddList.class);
        finishAffinity();
        startActivity(intent);
        finish();
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
            startActivity(new Intent(this,ShowList.class));
            finish();
        }

        if (id == R.id.menu_purchased) {
            startActivity(new Intent(this,PurchasedItem.class));
            finish();
        }
        if (id == R.id.menu_chart) {
            startActivity(new Intent(this,ChartActivity.class));
            finish();
        }


        return false;
    }
}
