package com.epam.atithi.controller;

import com.epam.atithi.dto.AdminDTO;
import com.epam.atithi.dto.AuthenticationDTO;
import com.epam.atithi.dto.VisitorDTO;
import com.epam.atithi.exception.AtithiAuthenticationFailedException;
import com.epam.atithi.service.AdminService;
import com.epam.atithi.service.VisitorService;
import com.epam.atithi.util.ControllersUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AdminService adminService;

    private final VisitorService visitorService;

    @PostMapping(value = "/admin/authenticate")
    public ResponseEntity<AdminDTO> authenticateAdmin(@RequestBody AuthenticationDTO authenticationDTO) {
        Optional<AdminDTO> adminDTO = adminService.authenticate( authenticationDTO );
        return adminDTO.map( ControllersUtils::getOkResponseEntity )
                .orElseThrow( () -> new AtithiAuthenticationFailedException( "Admin Authentication Failed!!!" + authenticationDTO.toString() ) );
    }

    @PostMapping(value = "/visitors/authenticate")
    public ResponseEntity<VisitorDTO> authenticateVisitor(@RequestBody AuthenticationDTO authenticationDTO) {
        Optional<VisitorDTO> visitorDTO = visitorService.authenticate(authenticationDTO);
        return visitorDTO.map( ControllersUtils::getOkResponseEntity)
                .orElseThrow(() -> new AtithiAuthenticationFailedException("Visitor Authentication Failed!!!" + authenticationDTO.toString()));
    }

}
