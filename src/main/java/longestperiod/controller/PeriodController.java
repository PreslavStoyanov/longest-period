package longestperiod.controller;

import longestperiod.service.LongestPeriodService;
import longestperiod.service.model.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/longest/period")
public class PeriodController
{
    private final LongestPeriodService longestPeriodService;

    public PeriodController(LongestPeriodService longestPeriodService)
    {
        this.longestPeriodService = longestPeriodService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pair getLongestPeriodFromFile(@RequestParam MultipartFile file)
    {
        return longestPeriodService.getLongestPeriodFromFile(file);
    }

    @PostMapping(value = "/all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Pair> getAllPeriods(@RequestParam MultipartFile file)
    {
        return longestPeriodService.getAllPeriodsFromFile(file);
    }
}
