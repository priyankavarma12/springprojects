package com.epam.atithi.repository;

import com.epam.atithi.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByVisitorid(Long visitorid);

}
