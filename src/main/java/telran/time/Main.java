package telran.time;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class Main {
    record MonthYear(int month, int year) {
        public MonthYear {
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("Month must be between 1 and 12");
            }
            if (year < 0) {
                throw new IllegalArgumentException("Wrong year. Year must be positive");
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            MonthYear monthYear = getMonthYear(args);
            printCalendar(monthYear);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } 
        catch (RuntimeException e) {
                e.printStackTrace();
        }
        catch (Exception e) {
           System.out.println(e.getMessage());
        }
       
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDates(monthYear);
    }

    private static void printDates(MonthYear monthYear) {
        int firstWeekDay = getFirstDayofWeek(monthYear);
        int lastDay = getLastDayOfMonth(monthYear);

        for (int day = 0; day < firstWeekDay; day++) {
            System.out.printf("%4s"," ");
        }
        for (int day = 1; day <= lastDay; day++) {
            System.out.printf("%4d", day);
            if ((day + firstWeekDay) % 7 == 0) {
                System.out.println();
            }
        }
    }

    private static void printWeekDays() {
        DateFormatSymbols dfs = new DateFormatSymbols(Locale.ENGLISH);
        String [] week = dfs.getShortWeekdays();
        for (int i = 1; i < week.length; i++) {
            System.out.printf("%4s", week[i]);
        }
        System.out.println();
    }

    private static void printTitle(MonthYear monthYear) {
        System.out.printf("%14d, %s\n", monthYear.year(), Month.of(monthYear.month()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH));
    }

    private static MonthYear getMonthYear (String[] args) throws Exception {
        LocalDate currDate = LocalDate.now();
        int year = 0, month = 0;
        try {
            year =  args.length > 0 ? Integer.parseInt(args[0]) : currDate.getYear();
            month = args.length > 1 ? Integer.parseInt(args[1]) : currDate.getMonthValue();
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong argumnts, should be numbers");
        };
        return new MonthYear(month, year);
    }
    private static int getFirstDayofWeek(MonthYear monthYear) {
        return LocalDate.of(monthYear.year(), monthYear.month(), 1).getDayOfWeek().getValue();
    }

    private static int getLastDayOfMonth(MonthYear monthYear) {
        return LocalDate.of(monthYear.year(), monthYear.month(), 1).lengthOfMonth(); 
    }
}
