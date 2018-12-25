package kr.gojumangte.reservation.repository;

import kr.gojumangte.reservation.model.Point;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("test")
public interface PointRedisRepository extends CrudRepository<Point, String> {
}
