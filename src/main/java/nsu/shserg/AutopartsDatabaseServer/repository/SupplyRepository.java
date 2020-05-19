package nsu.shserg.AutopartsDatabaseServer.repository;

import nsu.shserg.AutopartsDatabaseServer.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Integer> {
}