package com.example.controller;

import com.example.entity.User;
import com.example.entity.VerificationCode;
import com.example.repository.VerificationCodeRepository;
import com.example.service.AuthService;
import com.example.service.UserService;
import com.example.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    private final Map<String, Boolean> verifiedEmails = new ConcurrentHashMap<>();



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if (!verifiedEmails.getOrDefault(user.getEmail(), false)) {
            return ResponseEntity.badRequest().body("Email chưa được xác minh");
        }

        User registeredUser = userService.register(user);

        verifiedEmails.remove(user.getEmail());

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest){
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request){
        String email = request.get("email");
        return authService.handleForgotPassword(email);
    }

    @PostMapping("/send-code")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request){
        String email = request.get("email");
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email không được để trống");
        }

        try {
            verificationService.sendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent to " + email);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi gửi mã xác minh: " + e.getMessage());
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> request){
        String email = request.get("email");
        String code = request.get("code");

        if (email == null || code == null) {
            return ResponseEntity.badRequest().body("Thiếu email hoặc mã xác minh");
        }

        Optional<VerificationCode> vcodeOpt = verificationCodeRepository.findByEmailAndCode(email, code);

        if (vcodeOpt.isEmpty()) {
            return ResponseEntity.status(400).body("Sai mã xác minh");
        }

        VerificationCode vcode = vcodeOpt.get();
        LocalDateTime now = LocalDateTime.now();

        if (vcode.getCreatedAt().plusMinutes(10).isBefore(now)) {
            return ResponseEntity.status(400).body("Mã xác minh đã hết hạn");
        }

        verifiedEmails.put(email, true);

        return ResponseEntity.ok("Email đã được xác minh thành công");
    }

}
