package longestperiod.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class DateFormatter
{
    private static final String NULL = "null";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public LocalDate formatDate(String dateInString)
    {
        if (dateInString.equalsIgnoreCase(NULL))
        {
            return LocalDate.now();
        }

        return LocalDate.parse(dateInString, ofPattern(DATE_FORMAT));
    }
}
