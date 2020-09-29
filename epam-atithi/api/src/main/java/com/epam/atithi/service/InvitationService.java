package com.epam.atithi.service;

import com.epam.atithi.dto.VisitorInvitationResponseDTO;
import com.epam.atithi.model.Invitation;
import com.epam.atithi.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationService {

    private final InvitationRepository invitationRepository;

    public Invitation saveInvitation(Invitation invitation) {
        try {
            return invitationRepository.save(invitation);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public void deleteInvitation(Long invitationid) {
        try {
            invitationRepository.deleteById(invitationid);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public Optional<Invitation> getInvitation(Long invitationid) {
        try {
            return invitationRepository.findById(invitationid);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public Invitation updateInvitation(Invitation invitation) {
        try {
            return invitationRepository.save(invitation);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public List<Invitation> getAllInvitationForVisitor(Long visitorid) {
        try {
            return invitationRepository.findAllByVisitorid(visitorid);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public void updateVisitorResponse(Long invitationId, VisitorInvitationResponseDTO visitorInvitationResponseDTO) {
        Optional<Invitation> optionalInvitation = getInvitation(invitationId);
        if (optionalInvitation.isPresent()) {
            Invitation invitation = optionalInvitation.get();
            invitation.setVisitorresponse(visitorInvitationResponseDTO.isVisitorResponse() ? "Y" : "N");
            invitationRepository.save(invitation);
        }
    }
}
