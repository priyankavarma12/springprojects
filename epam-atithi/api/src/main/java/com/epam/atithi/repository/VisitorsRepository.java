package com.epam.atithi.repository;

import com.epam.atithi.model.Admin;
import com.epam.atithi.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Class containing Data Access Logic for Visitor Entity
 *
 * @author Spallya Omar
 */
public interface VisitorsRepository extends JpaRepository<Visitor, Long> {

    List<Visitor> findAllByAdminid(Long adminid);

    Optional<Visitor> findByEmailAndPassword(String email, String password);

}