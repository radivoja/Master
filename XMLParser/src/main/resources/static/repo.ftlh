package com.example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${model.className}Repository extends JpaRepository<${model.className}, Long> {

}
