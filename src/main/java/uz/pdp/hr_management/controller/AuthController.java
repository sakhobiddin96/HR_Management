package uz.pdp.hr_management.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hr_management.payload.ApiResponse;
import uz.pdp.hr_management.payload.LoginDto;
import uz.pdp.hr_management.payload.RegisterDto;
import uz.pdp.hr_management.payload.VerifyEmailDto;
import uz.pdp.hr_management.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    @PostMapping("/registerEmployee")
    public HttpEntity<?> registerEmployee(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerEmployee(registerDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.ok(apiResponse.getMessage());
        }
        return ResponseEntity.status(409).body(apiResponse.getMessage());
    }
    @PreAuthorize(value = "hasRole('ROLE_DIRECTOR')")
    @PostMapping("/registerManager")
    public HttpEntity<?> registerManager(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerManager(registerDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.ok(apiResponse.getMessage());
        }
        return ResponseEntity.status(409).body(apiResponse.getMessage());
    }
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("/registerDirector")
    public HttpEntity<?> registerDirector(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerDirector(registerDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.ok(apiResponse.getMessage());
        }
        return ResponseEntity.status(409).body(apiResponse.getMessage());
    }

    @PostMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String email, @RequestParam String emailCode, @RequestBody VerifyEmailDto verifyEmailDto){
        ApiResponse apiResponse = authService.verifyEmail(email, emailCode,verifyEmailDto.getPassword());
        if (apiResponse.isSuccess())
            return ResponseEntity.ok(apiResponse.getMessage());
        return ResponseEntity.status(401).body("System error");
    }

    @PostMapping("/login")
    public HttpEntity<?> loginToSystem(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse=authService.loginToSystem(loginDto);
        if (apiResponse.isSuccess())
            return ResponseEntity.ok(apiResponse.getMessage());
        return ResponseEntity.status(401).body(apiResponse.getMessage());
    }


}

