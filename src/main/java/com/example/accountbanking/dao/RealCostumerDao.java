package com.example.accountbanking.dao;

import com.example.accountbanking.entity.LegalCostumer;
import com.example.accountbanking.entity.RealCostumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RealCostumerDao extends JpaRepository<RealCostumer, Integer> {


//    @Query(" select c from RealCostumer c join Account  a on  c.id =a.id where a.accountNumber=:accountNumber ")
//    RealCostumer findRealByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("select c from RealCostumer c where c.name like %:name% or c.lastName like %:lastName%")
    List<RealCostumer> findReal (@Param("name") String name , @Param("lastName") String lastName);


    @Query("select c from RealCostumer c where c.nationalCode=:nationalCode")
    RealCostumer findRealByNationalCode (@Param("nationalCode") String nationalCode);
}
