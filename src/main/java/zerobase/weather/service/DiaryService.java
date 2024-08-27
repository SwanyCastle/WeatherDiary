package zerobase.weather.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DiaryRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Value("${openweathermap.key}")
    private String apiKey;

    public void createDiary(LocalDate date, String text) {
        String weatherData = getWeather();
        Map<String, Object> parsedWeather = parseWeather(weatherData);
        Diary nowDiary = Diary.builder()
                .weather(parsedWeather.get("main").toString())
                .icon(parsedWeather.get("icon").toString())
                .temperature((Double) parsedWeather.get("temp"))
                .text(text)
                .date(date)
                .build();
        diaryRepository.save(nowDiary);
    }

    public List<Diary> readDiary(LocalDate date) {
        return diaryRepository.findAllByDate(date);
    }

    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    public void updateDiary(LocalDate date, String text) {
        Diary nowDiary = diaryRepository.getFirstByDate(date);
        nowDiary.setText(text);
        diaryRepository.save(nowDiary);
    }

    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }

    private String getWeather() {
        String apiUrl =
                "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid="
                        + apiKey;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
            } else {
                br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream())
                );
            }

            String inputLine;
            StringBuilder sb = new StringBuilder();

            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();

            return sb.toString();
        } catch (Exception e) {
            return "Failed to get Response";
        }
    }

    private Map<String, Object> parseWeather(String jsongString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsongString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        JSONArray jsonArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) jsonArray.get(0);

        resultMap.put("temp", mainData.get("temp"));
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }
}
