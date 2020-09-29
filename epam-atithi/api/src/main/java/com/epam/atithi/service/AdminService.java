package com.epam.atithi.service;

import com.epam.atithi.dto.AdminDTO;
import com.epam.atithi.dto.AuthenticationDTO;
import com.epam.atithi.dto.InvitationDTO;
import com.epam.atithi.exception.AdminNotFoundException;
import com.epam.atithi.model.Admin;
import com.epam.atithi.model.Invitation;
import com.epam.atithi.model.Visitor;
import com.epam.atithi.notifications.email.invitations.VisitCompletedEmailNotification;
import com.epam.atithi.notifications.email.invitations.VisitCreatedEmailNotification;
import com.epam.atithi.notifications.email.invitations.VisitStartedEmailNotification;
import com.epam.atithi.repository.AdminRepository;
import com.epam.atithi.repository.VisitorsRepository;
import com.epam.atithi.util.ConverterUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final InvitationService invitationService;

    private final AdminRepository adminRepository;

    private final VisitorsRepository visitorsRepository;

    private final VisitCreatedEmailNotification visitCreatedEmailNotification;

    private final VisitStartedEmailNotification visitStartedEmailNotification;

    private final VisitCompletedEmailNotification visitCompletedEmailNotification;

    public void saveInvitation(InvitationDTO invitationDTO, Long adminId) {
        Invitation invitation = ConverterUtils.convertInvitationDTOToEntity(invitationDTO);
        Date currentDate = new Date(System.currentTimeMillis());
        invitation.setCreatedbyadmin(adminId);
        invitation.setVisitorresponse("P");
        invitation.setCreatedate(currentDate);
        invitation.setUpdatedbyadmin(adminId);
        invitation.setUpdatedate(currentDate);
        Invitation saveInvitation = invitationService.saveInvitation(invitation);
        Optional<Visitor> optionalVisitor = visitorsRepository.findById(invitation.getVisitorid());
        if (optionalVisitor.isPresent()) {
            boolean emailSent = visitCreatedEmailNotification.sendNewVisitCreatedEmail(saveInvitation, optionalVisitor.get());
            log.info("Invitation create email sent successfully: " + emailSent);
        }
    }

    public InvitationDTO getInvitation(Long invitationId) {
        return ConverterUtils.convertInvitationEntityToDTO(invitationService.getInvitation(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id " + invitationId)));
    }


    public void deleteInvitation(Long invitationId) {
        invitationService.deleteInvitation(invitationId);
    }

    public InvitationDTO updateInvitation(Long invitationId, InvitationDTO invitationDTO) {
        Optional<Invitation> invitationOpt = invitationService.getInvitation(invitationId);
        if (invitationOpt.isPresent()) {
            Invitation invitation = invitationOpt.get();
            Invitation updatedInvitation = ConverterUtils.convertInvitationDTOToEntity(invitationDTO);
            Date currentDate = new Date(System.currentTimeMillis());
            updatedInvitation.setInvitationid(invitation.getInvitationid());
            updatedInvitation.setUpdatedate(currentDate);
            updatedInvitation.setVisitorresponse(invitation.getVisitorresponse());
            Invitation recentUpdated = invitationService.updateInvitation(updatedInvitation);
            Optional<Visitor> optionalVisitor = visitorsRepository.findById(recentUpdated.getVisitorid());
            if (optionalVisitor.isPresent()) {
                if (StringUtils.isNotEmpty(recentUpdated.getIntime()) && StringUtils.isNotEmpty(recentUpdated.getOuttime())) {
                    boolean emailSent = visitCompletedEmailNotification.sendVisitCompletedEmail(recentUpdated, optionalVisitor.get());
                    log.info("Visit completed email sent successfully: " + emailSent);
                } else if (StringUtils.isNotEmpty(recentUpdated.getIntime()) && StringUtils.isEmpty(recentUpdated.getOuttime())) {
                    boolean emailSent = visitStartedEmailNotification.sendVisitStartedEmail(recentUpdated, optionalVisitor.get());
                    log.info("Visit Started email sent successfully: " + emailSent);
                }
            }

            return ConverterUtils.convertInvitationEntityToDTO(recentUpdated);
        } else {
            throw new RuntimeException("Invitation not found with id " + invitationId);
        }
    }

    public Optional<AdminDTO> authenticate(AuthenticationDTO authenticationDTO) {
        String email = authenticationDTO.getEmail();
        String password = authenticationDTO.getPassword();
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Email/Password can not be null/empty");
        }
        Optional<Admin> optionalAdmin = adminRepository.findByEmailAndPassword(email, password);
        return optionalAdmin.map(ConverterUtils::convertAdminEntityToDTO);
    }


    public Optional<AdminDTO> getAdmin(Long adminId){
        Optional<Admin> optionalAdmin = Optional.empty();
        try {
            optionalAdmin = adminRepository.findById(adminId);
        } catch (Exception ex) {
            throw new RuntimeException( ex.getLocalizedMessage() );
        }
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            return Optional.of( ConverterUtils.convertAdminEntityToDTO(admin) );
        } else {
            throw new AdminNotFoundException(adminId);
        }
    }
}
