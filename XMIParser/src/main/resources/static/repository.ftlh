package [=model.uri].repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import [=model.name]entities.[=model.name?cap_first];

@Repository
public interface [=model.name?cap_first]Repository extends JpaRepository<[=model.name?cap_first], Integer> {

}
