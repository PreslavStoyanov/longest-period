package longestperiod.service;

import longestperiod.service.model.Note;
import longestperiod.service.model.Pair;
import longestperiod.util.FileHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LongestPeriodService
{
    private final FileHandler fileHandler;
    private final OverlapCalculatorService overlapCalculatorService;

    public LongestPeriodService(FileHandler fileHandler, OverlapCalculatorService overlapCalculatorService)
    {
        this.fileHandler = fileHandler;
        this.overlapCalculatorService = overlapCalculatorService;
    }

    public Pair getLongestPeriodFromFile(MultipartFile filePath)
    {
        List<Note> notesFromFile = fileHandler.parseFile(filePath);

        List<Pair> overlappedPairsByProject =
                overlapCalculatorService.getMostOverlappedPairsByProject(getNotesByProjectId(notesFromFile));

        return overlappedPairsByProject
                .stream()
                .max(Comparator.comparing(Pair::workedDaysTogether))
                .get();
    }

    public List<Pair> getAllPeriodsFromFile(MultipartFile filePath)
    {
        List<Note> notesFromFile = fileHandler.parseFile(filePath);

        return overlapCalculatorService.getMostOverlappedPairsByProject(getNotesByProjectId(notesFromFile));
    }

    private static Map<String, List<Note>> getNotesByProjectId(List<Note> notes)
    {
        return notes.stream().collect(Collectors.groupingBy(Note::projectId));
    }
}