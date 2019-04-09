package com.example.accountbanking.dao;


import com.example.accountbanking.entity.RealCostumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RealCostumerDao extends JpaRepository<RealCostumer, Integer> {


    @Query(" select c from RealCostumer c  where (c.nationalCode=:nationalCode) and (c.isDeleted=1) ")
    RealCostumer findRealByNationalCode(@Param("nationalCode") String nationalCode);

    @Query("select c from RealCostumer c where (c.name like %:name% or :name is null )" +
            " and (c.lastName like  %:lastName% or :lastName is null) and (c.isDeleted=1)")
    List<RealCostumer> findReal (@Param("name") String name , @Param("lastName") String lastName);
}
