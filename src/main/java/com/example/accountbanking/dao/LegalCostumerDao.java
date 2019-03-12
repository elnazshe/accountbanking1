package com.example.accountbanking.dao;

import com.example.accountbanking.entity.LegalCostumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LegalCostumerDao extends JpaRepository<LegalCostumer, Integer> {


    @Query(" select c from LegalCostumer c  where c.companyNumber=:companyNumber ")
    LegalCostumer findLegalByCompanyNumber(@Param("companyNumber") String companyNumber);

    @Query(" select c from LegalCostumer c where (c.name like %:name% or :name is null) or " +
            " (c.companyTpe=:companyType or :companyType is null) ")
    List<LegalCostumer> findLegal (@Param("name") String name , @Param("companyType") String companyType);

}
