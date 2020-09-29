package com.epam.atithi.util;

import com.epam.atithi.constants.enums.StatusEnum;
import com.epam.atithi.dto.*;
import com.epam.atithi.model.Admin;
import com.epam.atithi.model.Invitation;
import com.epam.atithi.model.Visitor;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Converter Util class
 *
 * @author Spallya Omar
 */
public class ConverterUtils {

    /**
     * Helper method for creating the {@link ErrorDTO} from {@link Exception}
     *
     * @param ex Exception
     * @return {@link ErrorDTO}
     */
    static ErrorDTO getErrorDtoFromException(Exception ex) {
        return ErrorDTO.builder()
                .exception(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    public static Invitation convertInvitationDTOToEntity(InvitationDTO invitationDTO) {
        return Invitation.builder()
                .epamlocation(invitationDTO.getEpamLocation())
                .visitorid(invitationDTO.getVisitorId())
                .estimatedduration(invitationDTO.getEstimatedDuration())
                .poc(invitationDTO.getPoc())
                .purpose(invitationDTO.getPurpose())
                .visitdatetime(invitationDTO.getVisitDateTime())
                .visitorlocation(invitationDTO.getVisitorLocation())
                .intime(invitationDTO.getInTime())
                .outtime(invitationDTO.getOutTime())
                .build();
    }

    public static InvitationDTO convertInvitationEntityToDTO(Invitation invitation) {
        boolean completed = StringUtils.isNotEmpty(invitation.getIntime()) && StringUtils.isNotEmpty(invitation.getOuttime());
        String status = completed ? StatusEnum.COMPLETED.name() : StatusEnum.NOT_STARTED.name();
        if (!completed && (StringUtils.isNotEmpty(invitation.getIntime()) && StringUtils.isEmpty(invitation.getOuttime()))) {
            status = StatusEnum.STARTED.name();
        }
        return InvitationDTO.builder()
                .invitationId(invitation.getInvitationid())
                .epamLocation(invitation.getEpamlocation())
                .visitorId(invitation.getVisitorid())
                .estimatedDuration(invitation.getEstimatedduration())
                .poc(invitation.getPoc())
                .purpose(invitation.getPurpose())
                .visitDateTime(invitation.getVisitdatetime())
                .visitorLocation(invitation.getVisitorlocation())
                .inTime(invitation.getIntime())
                .outTime(invitation.getOuttime())
                .visitCompleted(completed)
                .status(status)
                .visitorResponse(invitation.getVisitorresponse())
                .build();

    }

    public static VisitorDTO convertVisitorEntityToDTO(Visitor visitor, List<Invitation> allInvitationForVisitor) {
        List<InvitationDTO> invitationDTOS = allInvitationForVisitor.stream()
                .map(ConverterUtils::convertInvitationEntityToDTO)
                .collect(Collectors.toList());
        return VisitorDTO.builder()
                .activated("Y".equalsIgnoreCase(visitor.getActivated()))
                .companyName(visitor.getCompanyname())
                .email(visitor.getEmail())
                .firstName(visitor.getFirstname())
                .invitations(!invitationDTOS.isEmpty() ? invitationDTOS : Collections.emptyList())
                .lastName(visitor.getLastname())
                .nationalId(visitor.getNationalid())
                .primaryCountryCode(visitor.getPrimarycountrycode())
                .primaryPhNumber(visitor.getPrimaryphnumber())
                .secondaryCountryCode(visitor.getSecondarycountrycode())
                .secondaryPhNumber(visitor.getSecondaryphnumber())
                .verificationEmailSent("Y".equalsIgnoreCase(visitor.getVerificationemailsent()))
                .visitorId(visitor.getVisitorsid())
                .adminId(visitor.getAdminid())
                .build();
    }

    public static Visitor convertVisitorDtoToEntity(VisitorDTO visitorDTO) {
        return Visitor.builder()
                .activated(visitorDTO.isActivated() ? "Y" : "N")
                .companyname(visitorDTO.getCompanyName())
                .email(visitorDTO.getEmail())
                .firstname(visitorDTO.getFirstName())
                .lastname(visitorDTO.getLastName())
                .nationalid(visitorDTO.getNationalId())
                .primarycountrycode(visitorDTO.getPrimaryCountryCode())
                .primaryphnumber(visitorDTO.getPrimaryPhNumber())
                .secondarycountrycode(visitorDTO.getSecondaryCountryCode())
                .secondaryphnumber(visitorDTO.getSecondaryPhNumber())
                .verificationemailsent(visitorDTO.isVerificationEmailSent() ? "Y" : "N")
                .build();
    }

    public static AdminDTO convertAdminEntityToDTO(Admin admin) {
        return AdminDTO.builder()
                .adminId(admin.getAdminid())
                .email(admin.getEmail())
                .firstName(admin.getFirstname())
                .lastName(admin.getLastname())
                .build();
    }

}
