package com.poc.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.Model.Example;

@Repository
public interface ExampleRepo extends JpaRepository<Example,Long> {

}
