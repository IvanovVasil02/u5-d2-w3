package ivanovvasil.u5d5w2Project.repositories;

import ivanovvasil.u5d5w2Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
