package longestperiod.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class DateParser
{
    private static final String NULL = "null";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public LocalDate parseDate(String dateInString)
    {
        if (dateInString == null || dateInString.equalsIgnoreCase(NULL))
        {
            return LocalDate.now();
        }

        return LocalDate.parse(dateInString, ofPattern(DATE_FORMAT));
    }
}
