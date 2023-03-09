package longestperiod.service.model;

import java.time.LocalDate;

public record Note(String employeeId, String projectId, LocalDate dateFrom, LocalDate dateTo)
{

}
