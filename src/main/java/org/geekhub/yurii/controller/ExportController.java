package org.geekhub.yurii.controller;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.exception.ResourceCreationException;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.export.FileFormat;
import org.geekhub.yurii.service.export.ExportService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/admin/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/admins-stats")
    public ResponseEntity<Resource> exportAdminsStats(@RequestParam("format") FileFormat fileFormat) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getContentType(fileFormat));
        String filename = "admins-stats " + getDate() + fileFormat.getExtension();
        headers.setContentDispositionFormData("attachment", filename);

        try {
            Resource resource = exportService.getAdminStatsResource(fileFormat);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (ResourceCreationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users-activity")
    public ResponseEntity<Resource> exportUsersActivity(@RequestParam("format") FileFormat fileFormat) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getContentType(fileFormat));
        String filename = "users-activity " + getDate() + fileFormat.getExtension();
        headers.setContentDispositionFormData("attachment", filename);

        try {
            Resource resource = exportService.getUsersActivityResource(fileFormat);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (ResourceCreationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/best-solves")
    public ResponseEntity<Resource> exportUsersBestSolve(@RequestParam("format") FileFormat fileFormat,
                                                         @RequestParam Discipline discipline) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getContentType(fileFormat));
        String filename = discipline.toString() + " best-solves " + getDate() + fileFormat.getExtension();
        headers.setContentDispositionFormData("attachment", filename);

        try {
            Resource resource = exportService.getUsersBestSolveResource(fileFormat, discipline);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (ResourceCreationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private MediaType getContentType(FileFormat fileFormat) {
        switch (fileFormat) {
            case WORD:
            case EXCEL:
                return MediaType.APPLICATION_OCTET_STREAM;
            case PDF:
                return MediaType.APPLICATION_PDF;
            default:
                throw new IllegalArgumentException("File format is incorrect");
        }
    }

    private String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.now().format(formatter);
    }
}
