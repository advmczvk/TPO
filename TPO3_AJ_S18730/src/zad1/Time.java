/**
 *
 *  @author Adamczyk Jakub S18730
 *
 */

package zad1;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.*;

public class Time {
    public static String passed(String from, String to) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}");

        boolean isTimeSpecified = pattern.matcher(from).matches() || pattern.matcher(to).matches();
        String[] months = {"stycznia", "luty", "marca", "kwietnia", "maja", "czerwca", "lipca", "sierpnia", "września", "października", "listopada", "grudnia"};
        String[] weekdays = {"poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota", "niedziela"};

        long dayCount;
        double weekCount;
        String passed = "";
        ZonedDateTime fromDate, toDate;
        if(isTimeSpecified){
            try{
                fromDate = LocalDateTime.parse(from).atZone(ZoneId.of("Europe/Warsaw"));
                toDate = LocalDateTime.parse(to).atZone(ZoneId.of("Europe/Warsaw"));
            }catch (DateTimeParseException e){
                return "***" + e;
            }

            long hoursCount, minutesCount;
            dayCount = DAYS.between(fromDate, toDate);
            weekCount = (double)dayCount/7 % 1 == 0 ? (int)dayCount/7 : (double)dayCount/7;
            Double weekCountRounded = BigDecimal.valueOf(weekCount).setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            hoursCount = HOURS.between(fromDate, toDate);
            minutesCount = MINUTES.between(fromDate, toDate);

            String dayFormat = dayCount == 1 ? " dzień" : " dni", minutesFromFormat = fromDate.getMinute() <= 9 ? "0" : "", minutesToFormat = toDate.getMinute() <= 9 ? "0" : "";

            passed = "Od " + fromDate.getDayOfMonth() + " " + months[fromDate.getMonth().getValue() - 1] + " " + fromDate.getYear() + " (" + weekdays[fromDate.getDayOfWeek().getValue() - 1] + ") godz. " + fromDate.getHour() + ":" + minutesFromFormat + fromDate.getMinute() + " do "
                    + toDate.getDayOfMonth() + " " + months[toDate.getMonth().getValue() - 1] + " " + toDate.getYear() + " (" + weekdays[toDate.getDayOfWeek().getValue() - 1] + ") godz. " + toDate.getHour() + ":" + minutesToFormat + toDate.getMinute() + "\n"
                    + " - mija: " + dayCount + dayFormat + ", tygodni " + weekCountRounded
                    + "\n - godzin: " + hoursCount + ", minut: " + minutesCount;

        }else{
            try{
                fromDate = LocalDate.parse(from).atStartOfDay(ZoneId.of("Europe/Warsaw"));
                toDate = LocalDate.parse(to).atStartOfDay(ZoneId.of("Europe/Warsaw"));
            }catch(DateTimeParseException e){
                return "***" + e;
            }
            dayCount = DAYS.between(fromDate, toDate);
            weekCount = (double)dayCount/7 % 1 == 0 ? (int)dayCount/7 : (double)dayCount/7;
            Double weekCountRounded = BigDecimal.valueOf(weekCount).setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            String dayFormat = dayCount == 1 ? " dzień" : " dni";

            passed = "Od " + fromDate.getDayOfMonth() + " " + months[fromDate.getMonth().getValue() - 1] + " " + fromDate.getYear() + " (" + weekdays[fromDate.getDayOfWeek().getValue() - 1] + ") do "
                    + toDate.getDayOfMonth() + " " + months[toDate.getMonth().getValue() - 1] + " " + toDate.getYear() + " (" + weekdays[toDate.getDayOfWeek().getValue() - 1] + ")\n"
                    + " - mija: " + dayCount + dayFormat + ", tygodni " + weekCountRounded;
        }

        if(dayCount > 0){
            Period calendar = Period.between(fromDate.toLocalDate(), toDate.toLocalDate());
            long yearCountCalendar = calendar.getYears(), monthCountCalendar = calendar.getMonths(), dayCountCalendar = calendar.getDays();
            String yearFormat = yearCountCalendar == 1 ? " rok" : (yearCountCalendar >= 5 && yearCountCalendar <= 21) || yearCountCalendar % 10 > 5 ? " lat" : " lata", monthFormat = monthCountCalendar == 1 ? " miesiąc" : " miesiące", dayCountFormat = dayCountCalendar == 1 ? " dzień": " dni";
            passed += "\n - kalendarzowo: ";
            if(yearCountCalendar > 0) passed += yearCountCalendar + yearFormat;
            if(yearCountCalendar != 0 && monthCountCalendar > 0) passed += ", " +  monthCountCalendar + monthFormat;
            if(dayCountCalendar != 0 && yearCountCalendar > 0 || monthCountCalendar > 0) passed += ", " + dayCountCalendar + dayCountFormat;
            else if(dayCountCalendar != 0 && yearCountCalendar == 0 && monthCountCalendar == 0) passed += dayCountCalendar + dayCountFormat;
        }
        return passed;
    }
}
