package dbtest.dbtest.repository;

import dbtest.dbtest.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
