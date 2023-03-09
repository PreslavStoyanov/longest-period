package longestperiod.service;

import longestperiod.service.model.Note;
import longestperiod.service.model.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class OverlapCalculatorService
{

    public List<Pair> getMostOverlappedPairsByProject(Map<String, List<Note>> notesByProject)
    {
        return notesByProject.values()
                .stream()
                .filter(notes -> notes.size() > 1)
                .map(OverlapCalculatorService::getMostOverlappedPair)
                .toList();
    }

    private static Pair getMostOverlappedPair(List<Note> sameProjectNotes)
    {
        return IntStream.range(0, sameProjectNotes.size() - 1)
                .mapToObj(i -> getPairWithWorkedDaysTogether(sameProjectNotes, i))
                .max(Comparator.comparing(Pair::workedDaysTogether)).get();
    }

    private static Pair getPairWithWorkedDaysTogether(List<Note> sameProjectNotes, int i)
    {
        Note firstNote = sameProjectNotes.get(i);
        Note secondNote = sameProjectNotes.get(i + 1);
        long pairWorkedDays = countDaysWorkedTogether(firstNote.dateFrom(), firstNote.dateTo(),
                secondNote.dateFrom(), secondNote.dateTo());

        return new Pair(firstNote.employeeId(), secondNote.employeeId(),
                sameProjectNotes.get(0).projectId(), pairWorkedDays);
    }

    private static long countDaysWorkedTogether(
            LocalDate firstNoteDateFrom, LocalDate firstNoteDateTo,
            LocalDate secondNoteDateFrom, LocalDate secondNoteDateTo)
    {
        if (!arePeriodsOverlapped(firstNoteDateFrom, firstNoteDateTo, secondNoteDateFrom, secondNoteDateTo))
        {
            return 0;
        }

        LocalDate latestStart = getLatest(firstNoteDateFrom, secondNoteDateFrom);
        LocalDate earliestEnd = getEarliest(firstNoteDateTo, secondNoteDateTo);

        return countDaysBetween(latestStart, earliestEnd);
    }

    private static long countDaysBetween(LocalDate latestStart, LocalDate earliestEnd)
    {
        return ChronoUnit.DAYS.between(latestStart, earliestEnd) + 1;
    }

    private static LocalDate getLatest(LocalDate first, LocalDate second)
    {
        return first.isAfter(second) ? first : second;
    }

    private static LocalDate getEarliest(LocalDate first, LocalDate second)
    {
        return first.isBefore(second) ? first : second;
    }

    private static boolean arePeriodsOverlapped(LocalDate firstDateFrom, LocalDate firstDateTo,
                                                LocalDate secondDateFrom, LocalDate secondDateTo)
    {
        return !firstDateFrom.isAfter(secondDateTo) && !secondDateFrom.isAfter(firstDateTo);
    }
}
