package com.epam.atithi.controller;

import com.epam.atithi.dto.InvitationDTO;
import com.epam.atithi.dto.VisitorDTO;
import com.epam.atithi.dto.VisitorInvitationResponseDTO;
import com.epam.atithi.exception.UnableToFetchDataException;
import com.epam.atithi.exception.UnableToSaveDataException;
import com.epam.atithi.exception.UnableToUpdateDataException;
import com.epam.atithi.service.AdminService;
import com.epam.atithi.service.InvitationService;
import com.epam.atithi.service.VisitorService;
import com.epam.atithi.util.ControllersUtils;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/visitors")
public class VisitorsController {

    private final VisitorService visitorService;

    private final InvitationService invitationService;

    private final AdminService adminService;

    @GetMapping(value = "/{visitorId}")
    public ResponseEntity<VisitorDTO> getVisitor(@ApiParam(value = "Visitor's ID whose details needs to be fetched", required = true) @PathVariable("visitorId") Long visitorId) {
        Optional<VisitorDTO> visitorDTO = visitorService.getVisitor(visitorId);
        return visitorDTO.map( ControllersUtils::getOkResponseEntity)
                .orElseThrow(() -> new UnableToSaveDataException("Something went wrong. Please check logs!!!"));
    }

    @PutMapping(value = "/{visitorId}")
    public ResponseEntity<VisitorDTO> updateVisitor(@PathVariable("visitorId") Long visitorId, @Valid @RequestBody VisitorDTO visitorDTO){
        Optional<VisitorDTO> updateVisitor = visitorService.updateVisitor(visitorId, visitorDTO);
        return updateVisitor.map( ControllersUtils::getOkResponseEntity)
                .orElseThrow(() -> new UnableToUpdateDataException("Something went wrong. Please check logs!!!"));
    }

    @PutMapping(value = "/{visitorId}/invitations/{invitationId}/visitorResponse")
    public HttpStatus updateVisitorInvitationResponse(@PathVariable("visitorId") Long visitorId,
                                                      @PathVariable("invitationId") Long invitationId,
                                                      @RequestBody VisitorInvitationResponseDTO visitorInvitationResponseDTO) {
        invitationService.updateVisitorResponse(invitationId, visitorInvitationResponseDTO);
        return HttpStatus.OK;
    }


    @PutMapping(value = "/invitations/{invitationId}")
    public ResponseEntity<InvitationDTO> updateInvitation(@PathVariable("invitationId") Long invitationId, @Valid @RequestBody InvitationDTO invitationDTO ){
        InvitationDTO invitation = adminService.updateInvitation( invitationId, invitationDTO);
        return ControllersUtils.getOkResponseEntity(invitation);
    }

    @GetMapping(value = "/invitations/{invitationId}/qrCode", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getQRCodeForInvitation(@ApiParam(value = "Invitation ID for which QR Code is required", required = true) @PathVariable("invitationId") Long invitationId, HttpServletResponse response) {
        try{
          return visitorService.getQRCode( invitationId );
        } catch (Exception ex){
            throw new UnableToFetchDataException("Unable to fetch QRCode for invitation id "+ invitationId);
        }

    }



}
