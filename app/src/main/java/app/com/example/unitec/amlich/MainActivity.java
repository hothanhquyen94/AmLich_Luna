package app.com.example.unitec.amlich;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static app.com.example.unitec.amlich.GridCellAdapter.getIdMonthAsString;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,SimpleCallBack {
    private static final String tag = "MyCalendarActivity";
    private TextView currentMonth;
    private TextView selectedDayMonthYearButton;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "M"; /** Called when the activity is first created. */
    private static final String yearTempalte = "yyyy"; /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView btn = (TextView)findViewById(R.id.selectedDayMonthYear);
        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH)+1;
        year = _calendar.get(Calendar.YEAR);

        selectedDayMonthYearButton = (TextView) this.findViewById(R.id.selectedDayMonthYear);
        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);
        currentMonth =(TextView) this.findViewById(R.id.currentMonth);
        currentMonth.setText("Tháng "+DateFormat.format(dateTemplate, _calendar.getTime()));
        btn.setText(DateFormat.format(yearTempalte, _calendar.getTime()));
        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) this.findViewById(R.id.calendar);
        adapter = new GridCellAdapter(getApplicationContext(), month, year,this);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((GridCellAdapter) parent.getAdapter()).setSelected(view,position);
                String[] selectedGridDate = GridCellAdapter.list
                        .get(position).split("-");
                String theday = selectedGridDate[0];
                String themonth = selectedGridDate[2];
                String theyear = selectedGridDate[3];

                int MonthToCovert = getIdMonthAsString(themonth);
                int DayToCovert =   Integer.parseInt(theday);
                int YearToCovert =  Integer.parseInt(theyear);

                //covert to luna date
                int[] dateLunaCover = CovertoLunaDate(DayToCovert,MonthToCovert,YearToCovert);
                String[] dateLunaAsString = getLunaDayAsString(DayToCovert,MonthToCovert,YearToCovert);
                TextView textView = (TextView)findViewById(R.id.display_luna_date);
                textView.setText("Ngày "+dateLunaAsString[0]+" tháng "+dateLunaAsString[1]+" năm "+dateLunaAsString[2]);

                //btn.setText(selectedGridDate[0] + selectedGridDate[2]+selectedGridDate[3]);
                if ((DayToCovert > 8)&& (position < 8)){
                    nextMonthView();
                }else if (( DayToCovert < 7 )&& ( position > 28 )){
                    prevMonthView();
                }
            }
        });
    }

    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getApplicationContext(), month, year,this);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

    }
    public void nextMonthView(){
        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        setGridCellAdapterToDate(month, year);
    }
    public void prevMonthView(){
        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }
        setGridCellAdapterToDate(month, year);
    }

    @Override
    public void onClick(View v) {
        if (v == prevMonth) {
            nextMonthView();
        }
        if (v == nextMonth) {
            prevMonthView();
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onReturnValue(String[] value,int i) {
        // Button btn = (Button)findViewById(R.id.selectedDayMonthYear);
        // btn.setText(value[0] + value[2]+value[3]);
       //int date = Integer.parseInt(value[0]);
       //if ((date > 8)&& (i < 8)){
       //    nextMonthView();
       //}else if (( date < 7 )&& ( i > 28 )){
       //    prevMonthView();
       //}
       // TextView textView = (TextView)findViewById(R.id.display_luna_date);
       // textView.setText(value[0]+", "+value[1]+", "+value[2]+ i);
    }

    public int[] CovertoLunaDate(int day,int month,int year){
        ChinaCalendar lunaDate = new ChinaCalendar(day,month,year);
        int[] dateLuna = lunaDate.ConVertToLunar();
        return dateLuna;
    }
    public String[] getLunaDayAsString(int day,int month,int year){
        ChinaCalendar lunaDate = new ChinaCalendar(day,month,year);
        String dayCanChi = lunaDate.getLunarDate();
        String monthCanChi = lunaDate.getLunarMonth();
        String yearCanChi = lunaDate.getLunarYear();
        return new String[] {dayCanChi,monthCanChi,yearCanChi};
    }
}
