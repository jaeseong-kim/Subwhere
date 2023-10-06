package com.example.subwhere.repository;

import com.example.subwhere.entity.MyTrain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyTrainRepository extends JpaRepository<MyTrain, Integer> {

    List<MyTrain> findAllByEmail(String email);

    Optional<MyTrain> findByTrainNoAndEmail(String trainNo, String email);

    boolean existsByTrainNoAndEmail(String trainNo, String email);
}
