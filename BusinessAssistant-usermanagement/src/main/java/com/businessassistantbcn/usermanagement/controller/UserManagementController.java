package com.businessassistantbcn.usermanagement.controller;

import com.businessassistantbcn.usermanagement.document.User;
import com.businessassistantbcn.usermanagement.dto.UserCreationDto;
import com.businessassistantbcn.usermanagement.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/businessassistantbcn/api/v1/usermanagement")
public class UserManagementController {

    //@Autowired
    //UserService userService;

    @GetMapping(value="/test")
    public String test() {
        return "Hello from BusinessAssistant User!!!";
    }


   /* @PostMapping(value = "/addUser")
      @Operation(summary = "Save user search")
      @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "400", description = "Bad Request"),
	      @ApiResponse(responseCode = "404", description = "Not Found"),
	      @ApiResponse(responseCode = "503", description = "Service Unavailable") })
    public ResponseEntity <UserDto> newUser(@Valid @RequestBody UserCreationDto userCreationDto){
        return userService.addUser(userCreationDto);
    }*/
}
