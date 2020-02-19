package run.kabuctl.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.kabuctl.demo.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
}
