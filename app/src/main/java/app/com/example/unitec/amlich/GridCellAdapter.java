package app.com.example.unitec.amlich;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Unitec on 24/11/2017.
 */

public class GridCellAdapter extends BaseAdapter  {
    String  curentDateString;
    private SimpleCallBack callback;
    private static final String tag = "GridCellAdapter";
    private final Context _context;
    public static List<String> list;
    private static final int DAY_OFFSET = 1;


    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private TextView dateSolar,dateLuna;
    private View previousView;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd-MMM-yyyy");

    public GridCellAdapter(Context _context, int month, int year,SimpleCallBack callback) {
        super();
        this._context = _context;
        this.callback = callback;
        this.list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

        printMonth(month, year);

        GregorianCalendar cal_month = (GregorianCalendar) GregorianCalendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        curentDateString = df.format(cal_month.getTime());

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    private void printMonth(int mm, int yy) {
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;
        int currentMonth = mm - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);


        GregorianCalendar cal = new GregorianCalendar(yy,currentMonth,1);

        if (currentMonth==11){
            prevMonth = currentMonth-1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy+1;
        }else if (currentMonth == 0){
            prevMonth = 11;
            prevYear = yy-1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);

        }else {

            prevMonth = currentMonth -1;
            prevYear =yy;
            nextMonth = currentMonth+1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }
        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK)-1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR))){
            if (mm==2){
                ++daysInMonth;
            }else if (mm==3){
                ++daysInPrevMonth;
            }
        }
        // Trailing Month days
        for (int i = 0 ;i<trailingSpaces;i++){

            list.add(String.valueOf((daysInPrevMonth-trailingSpaces+DAY_OFFSET)+i)+
                    "-GRAY"+ "-"+getMonthAsString(prevMonth)+"-"+prevYear);
        }
        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            list.add(String.valueOf(i) + "-WHITE" + "-"
                    + getMonthAsString(currentMonth) + "-" + yy);

        }
        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {

            list.add(String.valueOf(i + 1) + "-GRAY" + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);
        }

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.screen_gridcell,null);
        }
        dateSolar = (TextView) view.findViewById(R.id.date);
        dateLuna = (TextView)view.findViewById(R.id.dateLuna);



        String[] CurrentTime = curentDateString.split("-");
        String CurrentTimeDay = CurrentTime[2].replaceFirst("^0*", "");
        String CurrentTimeMonth = getMonthAsString(Integer.parseInt(CurrentTime[1])-1);
        String CurrentTimeYear = CurrentTime[0];

        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];

        int MonthToCovert = getIdMonthAsString(themonth);
        int DayToCovert =   Integer.parseInt(theday);
        int YearToCovert =  Integer.parseInt(theyear);

        //covert to luna date
        int[] dateLunaCover = CovertoLunaDate(DayToCovert,MonthToCovert,YearToCovert);
        String[] dateLunaAsString = getLunaDayAsString(DayToCovert,MonthToCovert,YearToCovert);

        callback.onReturnValue(dateLunaAsString,position);
        if (dateLunaCover[0]==1){
            dateLuna.setText(String.valueOf(dateLunaCover[0])+"/"+dateLunaCover[1]);
        }else {
            dateLuna.setText(String.valueOf(dateLunaCover[0]));
        }

        dateLuna.setTag(dateLunaCover[0] + "-" + dateLunaCover[1] + "-" + dateLunaCover[2]);
        dateSolar.setText(theday);
        dateSolar.setTag(theday + "-" + themonth + "-" + theyear);

        if (day_color[1].equals("CYAN")) {
            dateSolar.setTextColor(Color.CYAN);
            dateLuna.setTextColor(Color.CYAN);
        }
        if (day_color[1].equals("WHITE")) {
            dateSolar.setTextColor(Color.WHITE);
            dateLuna.setTextColor(Color.WHITE);
        }
        if (day_color[1].equals("GRAY")) {
            dateSolar.setTextColor(Color.GRAY);
            dateLuna.setTextColor(Color.GRAY);
        }
        if ((theday.equals(CurrentTimeDay)) && (themonth.equals(CurrentTimeMonth)) && (theyear.equals(CurrentTimeYear))){
            view.setBackgroundColor(Color.BLUE);
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }


        return view;
    }
    public View setSelected(View view, int pos) {
        if (previousView != null) {
            previousView.setBackgroundColor(Color.TRANSPARENT);
        }

        view.setBackgroundColor(Color.CYAN);

        int len = list.size();
        if (len > pos) {
            if (list.get(pos).equals(curentDateString)) {
            } else {
                previousView = view;
            }
        }
        return view;
    }


   // public int getCurrentDayOfMonth() {
   //     return currentDayOfMonth;
   // }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
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

    public static int getIdMonthAsString(String s){
        return Arrays.asList(Util.months).indexOf(s)+1;
    }
    private String getMonthAsString(int i) {
        return Util.months[i];
    }
    private String getWeekDayAsString(int i) {
        return Util.weekdays[i];
    }
    private int getNumberOfDaysOfMonth(int i) {
        return Util.daysOfMonth[i];
    }

}
interface SimpleCallBack {
    void onReturnValue(String[] value, int i);
}
