package au.edu.utas.ssaadat.assignment2.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import au.edu.utas.ssaadat.assignment2.R;
import au.edu.utas.ssaadat.assignment2.db.DBHelper;

public class ChartActivity extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Expenditure Graph");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBHelper dbHelper=new DBHelper(getApplicationContext());
        int jan= dbHelper.getPricePerMonth("Jan");
        int feb= dbHelper.getPricePerMonth("Feb");
        int mar= dbHelper.getPricePerMonth("Mar");
        int apr= dbHelper.getPricePerMonth("Apr");
        int may= dbHelper.getPricePerMonth("May");
        int jun= dbHelper.getPricePerMonth("Jun");
        int jul= dbHelper.getPricePerMonth("Jul");
        int aug= dbHelper.getPricePerMonth("Aug");
        int sep= dbHelper.getPricePerMonth("Sep");
        int oct= dbHelper.getPricePerMonth("Oct");
        int nov= dbHelper.getPricePerMonth("Nov");
        int dec= dbHelper.getPricePerMonth("Dec");

        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(jan, 0));
        entries.add(new BarEntry(feb, 1));
        entries.add(new BarEntry(mar, 2));
        entries.add(new BarEntry(apr, 3));
        entries.add(new BarEntry(may, 4));
        entries.add(new BarEntry(jun, 5));
        entries.add(new BarEntry(jul, 6));
        entries.add(new BarEntry(aug, 7));
        entries.add(new BarEntry(sep, 8));
        entries.add(new BarEntry(oct, 9));
        entries.add(new BarEntry(nov, 10));
        entries.add(new BarEntry(dec, 11));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        labels.add("Sep");
        labels.add("Oct");
        labels.add("Nov");
        labels.add("Dec");


        BarData data = new BarData( labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

       barChart.setDescription("Expenditure for the year 2018");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for toolbar arrow
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), PurchasedItem.class));
                finish();
                break;
        }

        return true;
    }
}