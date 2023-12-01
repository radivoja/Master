package com.project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.entities.${model.name};
@Repository
public interface ${model.name}Repository extends JpaRepository<${model.name}, Integer> {

}
