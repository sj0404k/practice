package dbtest.dbtest.repository;

import dbtest.dbtest.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface DbMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

}
