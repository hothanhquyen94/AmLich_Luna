package app.com.example.unitec.amlich;

/**
 * Created by QuyenHT on 11/25/2017.
 */

public class Util {

    public static final String[] weekdays = new String[] {
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
    };
    public static final String[] months = {
            "January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"
    };
    public static final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };


 // public static String getLunarDate(int date){
 //     String[] can = new String[] {"Giap", "At","Binh","Dinh","Mau","Ki","Canh","Tan","Nham","Qui"};
 //     String[] chi = new String[] {"Ti","Suu","Dan","Mao","Thinh","Ti","Ngo","Mui","Than","Dau","Tuat","Hoi"};
 //     long juliusDay = convertToJuliusDay();
 //     return can[(int)((juliusDay+9)%10)-1]+" "+chi[(int)((juliusDay+12)%12)];
 // }

 // public static  String getLunarMonth(){
 //     String[] can = new String[] {"Giap", "At","Binh","Dinh","Mau","Ki","Canh","Tan","Nham","Qui"};
 //     String[] chi = new String[] {"Dan","Mao","Thinh","Ti","Ngo","Mui","Than","Dau","Tuat","Hoi","Ti","Suu"};

 //     int mod = (mLunarYear*12+ mLunarMonth+3)%10;
 //     return can[mod]+" "+chi[mLunarMonth-1];
 // }

 // public static String getLunarYear(){
 //     String[] can = new String[] {"Giap", "At","Binh","Dinh","Mau","Ki","Canh","Tan","Nham","Qui"};
 //     String[] chi = new String[] {"Ty","Suu","Dan","Mao","Thinh","Ti","Ngo","Mui","Than","Dau","Tuat","Hoi"};
 //     return can[(mLunarYear+6)%10] +" "+chi[(mLunarYear+8)%12];
 // }
}
