package zerobase.weather.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @Tag(name = "Weather Diary API")
    @Operation(summary = "Diary 생성", description = "날씨 일기 생성 API")
    @PostMapping("/diary/create")
    public void createDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "날씨 일기를 생성할 날짜", example = "2024-01-01")
            LocalDate date,
            @RequestBody String text
    ) {
        diaryService.createDiary(date, text);
    }

    @Tag(name = "Weather Diary API")
    @Operation(summary = "Diary 조회", description = "날씨 일기 조회 API")
    @GetMapping("/diary/read")
    public List<Diary> readDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "날씨 일기를 조회할 날짜", example = "2024-01-01")
            LocalDate date
    ) {
        return diaryService.readDiary(date);
    }

    @Tag(name = "Weather Diary API")
    @Operation(summary = "Diary 조회", description = "날씨 일기 구간 조회 API")
    @GetMapping("/diaries/read")
    public List<Diary> readDiaries(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "날씨 일기를 조회할 시작 날짜", example = "2024-01-01")
            LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "날씨 일기를 조회할 끝 날짜", example = "2024-01-01")
            LocalDate endDate
    ) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @Tag(name = "Weather Diary API")
    @Operation(summary = "Diary 수정", description = "날씨 일기 수정 API")
    @PutMapping("/diary/update")
    public void updateDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "날씨 일기를 수정할 날짜", example = "2024-01-01")
            LocalDate date,
            @RequestBody String text
    ) {
        diaryService.updateDiary(date, text);
    }

    @Tag(name = "Weather Diary API")
    @Operation(summary = "Diary 삭제", description = "날씨 일기 삭제 API")
    @DeleteMapping("/diary/delete")
    public void deleteDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "날씨 일기를 삭제할 날짜", example = "2024-01-01")
            LocalDate date
    ) {
        diaryService.deleteDiary(date);
    }
}
