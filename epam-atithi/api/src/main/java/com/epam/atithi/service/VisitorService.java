package com.epam.atithi.service;

import com.epam.atithi.dto.AuthenticationDTO;
import com.epam.atithi.dto.InvitationDTO;
import com.epam.atithi.dto.VisitorDTO;
import com.epam.atithi.exception.InvitationNotFoundException;
import com.epam.atithi.exception.NoVisitorsFoundForAdminException;
import com.epam.atithi.exception.VisitorNotFoundException;
import com.epam.atithi.model.Invitation;
import com.epam.atithi.model.Visitor;
import com.epam.atithi.notifications.email.visitors.VisitorAccountVerificationEmailNotifications;
import com.epam.atithi.repository.VisitorsRepository;
import com.epam.atithi.util.ConverterUtils;
import com.epam.atithi.util.QRCodeUtils;
import com.epam.atithi.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorService {

    private final VisitorsRepository visitorsRepository;

    private final InvitationService invitationService;

    private final VisitorAccountVerificationEmailNotifications visitorAccountVerificationEmailNotifications;

    public void saveVisitor(Long adminId, VisitorDTO visitorDTO) {
        try {
            Visitor visitor = ConverterUtils.convertVisitorDtoToEntity(visitorDTO);
            visitor.setAdminid(adminId);
            visitor.setPassword(Utils.getAlphaNumericString(7));
            visitor.setActivated("N");
            Visitor savedVisitor = visitorsRepository.save(visitor);
            boolean emailSent = visitorAccountVerificationEmailNotifications.sendAccountVerificationEmail(savedVisitor);
            savedVisitor.setVerificationemailsent(emailSent ? "Y" : "N");
            visitorsRepository.save(savedVisitor);
            log.info("Email sent successfully: " + emailSent);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public void deleteVisitor(Long visitorId) {
        try {
            visitorsRepository.deleteById(visitorId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public Optional<VisitorDTO> getVisitor(Long visitorId) {
        Optional<Visitor> optionalVisitor = Optional.empty();
        try {
            optionalVisitor = visitorsRepository.findById(visitorId);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
        if (optionalVisitor.isPresent()) {
            Visitor visitor = optionalVisitor.get();
            List<Invitation> allInvitationForVisitor = getAllInvitationForVisitor(visitor.getVisitorsid());
            return Optional.of(ConverterUtils.convertVisitorEntityToDTO(visitor, allInvitationForVisitor));
        } else {
            throw new VisitorNotFoundException(visitorId);
        }

    }

    public Optional<VisitorDTO> updateVisitor(Long visitorId, VisitorDTO visitorDTO) {
        Optional<Visitor> optionalVisitor = visitorsRepository.findById(visitorId);
        if (optionalVisitor.isPresent()) {
            Visitor visitor = optionalVisitor.get();
            Visitor updatedVisitor = ConverterUtils.convertVisitorDtoToEntity(visitorDTO);
            updatedVisitor.setAdminid(visitor.getAdminid());
            updatedVisitor.setPassword(visitor.getPassword());
            updatedVisitor.setVisitorsid(visitor.getVisitorsid());
            updatedVisitor.setVerificationemailsent(visitor.getVerificationemailsent());
            updatedVisitor.setActivated(visitor.getActivated());
            visitorsRepository.save(updatedVisitor);
            return getVisitor(visitorId);
        } else {
            throw new RuntimeException("Visitor not found with id " + visitorId);
        }
    }

    public List<Invitation> getAllInvitationForVisitor(Long visitorid) {
        try {
            return invitationService.getAllInvitationForVisitor(visitorid);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public Optional<List<VisitorDTO>> getAllVisitorsForAdmin(Long adminId) {
        List<Visitor> allByAdminid = visitorsRepository.findAllByAdminid(adminId);
        if (null != allByAdminid && !allByAdminid.isEmpty()) {
            return Optional.of(allByAdminid.stream()
                    .map(visitor -> ConverterUtils.convertVisitorEntityToDTO(visitor, Collections.emptyList()))
                    .collect(Collectors.toList()));
        } else {
            throw new NoVisitorsFoundForAdminException(adminId);
        }
    }

    public Optional<VisitorDTO> authenticate(AuthenticationDTO authenticationDTO) {
        String email = authenticationDTO.getEmail();
        String password = authenticationDTO.getPassword();
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Email/Password can not be null/empty");
        }
        Optional<Visitor> optionalVisitor = visitorsRepository.findByEmailAndPassword(email, password);
        if (optionalVisitor.isPresent()) {
            Visitor visitor = optionalVisitor.get();
            visitor.setActivated("Y");
            visitorsRepository.save(visitor);
            return getVisitor(visitor.getVisitorsid());
        }
        return Optional.empty();
    }

    public byte[] getQRCode(Long invitationId) throws JsonProcessingException {
        Optional<Invitation> optionalInvitation = invitationService.getInvitation(invitationId);
        if (optionalInvitation.isPresent()) {
            Invitation invitation = optionalInvitation.get();
            InvitationDTO invitationDTO = ConverterUtils.convertInvitationEntityToDTO(invitation);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(invitationDTO);
            return QRCodeUtils.getQRCodeImage(jsonString, 300, 300);
        } else {
            throw new InvitationNotFoundException(invitationId);
        }
    }

}
