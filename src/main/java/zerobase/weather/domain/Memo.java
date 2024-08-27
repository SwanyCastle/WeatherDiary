package zerobase.weather.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Memo {
    @Id
    // GeneratedValue
    // - GenerationType.AUTO : 상황에 맞춰서 알아서 자동적으로 키 생성해 주는 옵션
    // - GenerationType.IDENTITY : 기본적인 키 생성을 DB 에 맡기겠다는 옵션 (Spring Boot 는 키 생성 X)
    // - GenerationType.SEQUENCE : DataBase Object 라는걸 만들어서 얘가 키 생성을 해주는 옵션
    // - GenerationType.TABLE : 키 생성만을 위한 테이블을 만들어서 키 생성을 해주는 옵션
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
}
