package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
}
