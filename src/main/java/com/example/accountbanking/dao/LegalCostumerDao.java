package com.example.accountbanking.dao;

import com.example.accountbanking.entity.LegalCostumer;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LegalCostumerDao extends JpaRepository<LegalCostumer, Integer> {

//    @Query(" select c from LegalCostumer c join Account  a on  c.id =a.id where a.accountNumber=:accountNumber ")
//    LegalCostumer findLegalByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query(" select c from LegalCostumer c join Account  a on  c.id =a.id where a.companyNumber=:companyNumber ")
    LegalCostumer findLegalByCompanyNumber(@Param("companyNumber") String companyNumber);

    @Query("select c from LegalCostumer c where c.name like %:name% or  c.companyTpe like %:companyTpe%")
    List<LegalCostumer> findLegal (@Param("name") String name , @Param("companyTpe") String companyTpe);



}