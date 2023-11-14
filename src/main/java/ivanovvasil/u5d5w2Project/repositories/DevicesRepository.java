package ivanovvasil.u5d5w2Project.repositories;

import ivanovvasil.u5d5w2Project.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevicesRepository extends JpaRepository<Device, Integer> {
  @Query("SELECT d FROM Device d WHERE d.user.id = :id")
  List<Device> findAllByUserId(int id);

}
