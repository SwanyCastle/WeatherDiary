package zerobase.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.weather.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WeatherDiaryException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public WeatherDiaryException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
