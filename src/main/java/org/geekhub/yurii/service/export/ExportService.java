package org.geekhub.yurii.service.export;

import lombok.RequiredArgsConstructor;
import org.geekhub.yurii.model.cube.Discipline;
import org.geekhub.yurii.model.export.AdminStats;
import org.geekhub.yurii.model.export.FileFormat;
import org.geekhub.yurii.model.export.UserActivity;
import org.geekhub.yurii.model.export.UserBestSolve;
import org.geekhub.yurii.repository.ExportRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final ExportRepository exportRepository;

    public Resource getAdminStatsResource(FileFormat fileFormat) {
        List<AdminStats> adminsStatsList = exportRepository.getAdminStats();

        DocumentResourceCreator<AdminStats> resourceCreator = getResourceCreator(fileFormat, AdminStats.class);
        resourceCreator.setData(adminsStatsList);

        return resourceCreator.createResource();
    }

    public Resource getUsersActivityResource(FileFormat fileFormat) {
        List<UserActivity> usersActivity = exportRepository.getUsersActivity();

        DocumentResourceCreator<UserActivity> resourceCreator = getResourceCreator(fileFormat, UserActivity.class);
        resourceCreator.setData(usersActivity);

        return resourceCreator.createResource();
    }

    public Resource getUsersBestSolveResource(FileFormat fileFormat, Discipline discipline) {
        List<UserBestSolve> usersBestSolve = exportRepository.getUsersBestSolve(discipline);

        DocumentResourceCreator<UserBestSolve> resourceCreator = getResourceCreator(fileFormat, UserBestSolve.class);
        resourceCreator.setData(usersBestSolve);

        return resourceCreator.createResource();
    }

    private <T> DocumentResourceCreator<T> getResourceCreator(FileFormat fileFormat, Class<T> clazz) {
        switch (fileFormat) {
            case WORD:
                return new WordResourceCreator<>(clazz);
            case PDF:
                return new PdfResourceCreator<>(clazz);
            case EXCEL:
                return new ExcelResourceCreator<>(clazz);
            default:
                throw new IllegalArgumentException("File format is incorrect");
        }
    }
}
