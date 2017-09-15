package saleswebapp.components;

/**
 * Created by Alexander Carl on 12.09.2017.
 */
public class DateReOrder {

    public String reOrderDateString(String dateAsString) {
        if(dateAsString.length() != 10) { //10
            return dateAsString;
        } else {
            String year = dateAsString.substring(0,4);
            String month = dateAsString.substring(5,7);
            String day = dateAsString.substring(8,10);

            return day + "-" + month + "-" + year;
        }
    }

    public String reOrderDateTimeString(String dateAsString) {
        if(dateAsString.length() != 16) { //16
            return dateAsString;
        } else {
            String date = dateAsString.substring(0,10);
            date = reOrderDateString(date);
            String time = dateAsString.substring(11,16);

            return date + " " + time;
        }
    }
}
