package longestperiod.util;

import longestperiod.exception.FileUploadException;
import longestperiod.service.model.Note;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class FileHandler
{
    private final DateFormatter dateFormatter;

    public FileHandler(DateFormatter dateFormatter)
    {
        this.dateFormatter = dateFormatter;
    }

    public List<Note> parseFile(MultipartFile file)
    {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()))
        {
            return csvParser.getRecords()
                    .stream()
                    .map(csvLine -> new Note(
                            csvLine.get("EmpID"),
                            csvLine.get("ProjectID"),
                            dateFormatter.formatDate(csvLine.get("DateFrom")),
                            dateFormatter.formatDate(csvLine.get("DateTo"))
                    )).toList();
        }
        catch (IOException e)
        {
            throw new FileUploadException("Failed to upload file", e);
        }
    }
}
