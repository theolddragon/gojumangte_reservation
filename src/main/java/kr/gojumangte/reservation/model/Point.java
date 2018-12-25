package kr.gojumangte.reservation.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Profile("test")
@RedisHash("point")
public class Point implements Serializable {

  @Id
  private String id;
  private Long amount;
  private LocalDateTime refreshTime;

  @Builder
  public Point(String id, Long amount, LocalDateTime refreshTime) {
    this.id = id;
    this.amount = amount;
    this.refreshTime = refreshTime;
  }

  public void refresh(long amount, LocalDateTime refreshTime) {

    // 저장된 데이터보다 최신 데이터일 경우
    if (refreshTime.isAfter(this.refreshTime)) {
      this.amount = amount;
      this.refreshTime = refreshTime;
    }
  }
}
