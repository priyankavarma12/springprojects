package com.epam.atithi.controller;

import com.epam.atithi.dto.AdminDTO;
import com.epam.atithi.dto.InvitationDTO;
import com.epam.atithi.dto.VisitorDTO;
import com.epam.atithi.exception.UnableToFetchDataException;
import com.epam.atithi.exception.UnableToSaveDataException;
import com.epam.atithi.service.AdminService;
import com.epam.atithi.service.VisitorService;
import com.epam.atithi.util.ControllersUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    private final VisitorService visitorService;

    @ApiOperation(value = "create invitation to visitor", response = InvitationDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created an invitation"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    @PostMapping(value = "/{adminId}/invitations")
    public HttpStatus createInvitation(@ApiParam(value = "Admin's ID for which invitation needs to be send", required = true) @PathVariable("adminId") Long adminId,
                                 @ApiParam(value = "Invitation model that needs to be added", required = true) @Valid @RequestBody InvitationDTO invitationDTO) {
        adminService.saveInvitation(invitationDTO, adminId);
        return HttpStatus.CREATED;
    }


    @GetMapping(value = "/invitations/{invitationId}")
    public ResponseEntity<InvitationDTO> getInvitation(@PathVariable("invitationId") Long invitationId) {
        InvitationDTO invitation = adminService.getInvitation( invitationId );
        return ControllersUtils.getOkResponseEntity(invitation);
    }

    @DeleteMapping(value = "/{adminId}/invitations/{invitationId}")
    public void deleteInvitation(@PathVariable("adminId") Long adminId, @PathVariable("invitationId") Long invitationId ){
        if(adminId == null)
            throw new RuntimeException( "Admin id not found" );
        adminService.deleteInvitation(invitationId);
    }

    @PostMapping(value = "/{adminId}/visitors")
    public HttpStatus createVisitor(@ApiParam(value = "Admin's ID for which invitation needs to be send", required = true) @PathVariable("adminId") Long adminId,
                                 @ApiParam(value = "Invitation model that needs to be added", required = true) @Valid @RequestBody VisitorDTO visitorDTO) {
        visitorService.saveVisitor(adminId, visitorDTO);
        return HttpStatus.CREATED;
    }

    @GetMapping(value = "/{adminId}/visitors")
    public ResponseEntity<List<VisitorDTO>> getAllVisitorsForAdmin(@ApiParam(value = "Admin's ID for which invitation needs to be send", required = true) @PathVariable("adminId") Long adminId) {
        Optional<List<VisitorDTO>> allVisitorsForAdmin = visitorService.getAllVisitorsForAdmin( adminId );
        if(allVisitorsForAdmin.isPresent()) {
            return ControllersUtils.getOkResponseEntity( allVisitorsForAdmin.get() );
        }
        throw new UnableToFetchDataException("Something went wrong while fetching the date. Please check logs for more information ");
    }

    @GetMapping(value = "/{adminId}")
    public ResponseEntity<AdminDTO> getAdmin(@ApiParam(value = "Admin ID whose details needs to be fetched", required = true) @PathVariable("adminId") Long adminId) {
        Optional<AdminDTO> admin = adminService.getAdmin( adminId );
        return admin.map( ControllersUtils::getOkResponseEntity)
                .orElseThrow(() -> new UnableToSaveDataException("Something went wrong. Please check logs!!!"));
    }

}
