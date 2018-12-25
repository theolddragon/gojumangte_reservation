package kr.gojumangte.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import kr.gojumangte.reservation.model.Point;
import kr.gojumangte.reservation.repository.PointRedisRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

  @Autowired
  private PointRedisRepository pointRedisRepository;

  @After
  public void tearDown() {
    pointRedisRepository.deleteAll();
  }

  @Test
  public void insert_and_select_test() {

    // given
    String id = "tigger";
    LocalDateTime refreshTime = LocalDateTime.now();
    Point point = Point.builder()
        .id(id)
        .amount(1000L)
        .refreshTime(refreshTime)
        .build();

    // when
    pointRedisRepository.save(point);

    // then
    Point savedPoint = pointRedisRepository.findById(id).get();
    assertThat(savedPoint.getAmount()).isEqualTo(1000L);
    assertThat(savedPoint.getRefreshTime()).isEqualTo(refreshTime);
  }

  @Test
  public void edit_test() {

    // given
    String id = "tigger";
    LocalDateTime refreshTime = LocalDateTime.now();
    pointRedisRepository.save(Point.builder()
        .id(id)
        .amount(1000L)
        .refreshTime(refreshTime)
        .build());

    // when
    Point savedPoint = pointRedisRepository.findById(id).get();
    savedPoint.refresh(2000L, LocalDateTime.now());
    pointRedisRepository.save(savedPoint);

    // then
    Point refreshPoint = pointRedisRepository.findById(id).get();
    assertThat(refreshPoint.getAmount()).isEqualTo(2000L);
  }
}
