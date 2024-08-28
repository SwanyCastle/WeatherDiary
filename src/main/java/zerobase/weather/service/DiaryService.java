package zerobase.weather.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.WeatherApplication;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DateWeatherRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;

    @Value("${openweathermap.key}")
    private String apiKey;

    @Transactional()
    public void createDiary(LocalDate date, String text) {
        logger.info("Started to create diary");
        DateWeather weatherData = getWeatherFormDB(date);

//        Diary nowDiary = Diary.builder()
//                .weather(weatherData.getWeather())
//                .icon(weatherData.getIcon())
//                .temperature(weatherData.getTemperature())
//                .text(text)
//                .date(date)
//                .build();

        Diary nowDiary = new Diary();
        nowDiary.setDateWeather(weatherData);
        nowDiary.setText(text);

        diaryRepository.save(nowDiary);
        logger.info("End to create diary");
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date) {
        logger.info("Read diary");
        return diaryRepository.findAllByDate(date);
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    @Transactional
    public void updateDiary(LocalDate date, String text) {
        Diary nowDiary = diaryRepository.getFirstByDate(date);
        nowDiary.setText(text);
        diaryRepository.save(nowDiary);
    }

    @Transactional
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }

    @Transactional
//    @Scheduled(cron = "0/3 * * * * *") // 3초마다 데이터 불러오는지 확인 - 날짜 값이 기본키라 한 개 밖에 저장 안됨
    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherDate() {
        logger.info("Success to get weather data");
        dateWeatherRepository.save(getWeatherFromApi());
    }

    private DateWeather getWeatherFromApi() {
        String weatherData = getWeather();
        Map<String, Object> parsedWeather = parseWeather(weatherData);
        return DateWeather.builder()
                .date(LocalDate.now())
                .weather(parsedWeather.get("main").toString())
                .icon(parsedWeather.get("icon").toString())
                .temperature((Double) parsedWeather.get("temp"))
                .build();
    }

    private DateWeather getWeatherFormDB(LocalDate date) {
        List<DateWeather> weatherList = dateWeatherRepository.findAllByDate(date);

        if (weatherList.isEmpty()) {
            return getWeatherFromApi();
        }
        return weatherList.get(0);
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
