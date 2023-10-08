package com.project.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.entities.${model.className};
@Repository
public interface ${model.className}Repository extends JpaRepository<${model.className}, Long> {

}
