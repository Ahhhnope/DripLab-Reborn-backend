package com.example.cafe.Service;

import com.example.cafe.Entity.PasswordResetToken;
import com.example.cafe.Repository.PasswordResetTokenRepository;
import com.example.cafe.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    // ── Bước 1: Tạo OTP và gửi email ─────────────────────────────────────────
    @Transactional
    public void sendOtp(String email) {
        // Kiểm tra email tồn tại
        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với email này."));

        // Xoá token cũ (nếu có)
        tokenRepository.deleteByEmail(email);

        // Tạo OTP 6 chữ số
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Lưu token (hash OTP)
        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email);
        token.setOtp(passwordEncoder.encode(otp));
        token.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        token.setUsed(false);
        tokenRepository.save(token);

        // Gửi email
        sendOtpEmail(email, otp);
    }

    // ── Bước 2: Xác nhận OTP ─────────────────────────────────────────────────
    public void verifyOtp(String email, String otp) {
        PasswordResetToken token = tokenRepository.findTopByEmailOrderByExpiresAtDesc(email)
                .orElseThrow(() -> new RuntimeException("Mã OTP không hợp lệ."));

        if (token.isUsed()) {
            throw new RuntimeException("Mã OTP đã được sử dụng.");
        }
        if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
            throw new RuntimeException("Mã OTP đã hết hạn.");
        }
        if (!passwordEncoder.matches(otp, token.getOtp())) {
            throw new RuntimeException("Mã OTP không đúng.");
        }
    }

    // ── Bước 3: Đặt lại mật khẩu ─────────────────────────────────────────────
    @Transactional
    public void resetPassword(String email, String otp, String newPassword) {
        // Verify lại OTP lần cuối
        PasswordResetToken token = tokenRepository.findTopByEmailOrderByExpiresAtDesc(email)
                .orElseThrow(() -> new RuntimeException("Mã OTP không hợp lệ."));

        if (token.isUsed()) {
            throw new RuntimeException("Mã OTP đã được sử dụng.");
        }
        if (LocalDateTime.now().isAfter(token.getExpiresAt())) {
            throw new RuntimeException("Mã OTP đã hết hạn.");
        }
        if (!passwordEncoder.matches(otp, token.getOtp())) {
            throw new RuntimeException("Mã OTP không đúng.");
        }

        // Cập nhật mật khẩu mới
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản."));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Đánh dấu token đã dùng
        token.setUsed(true);
        tokenRepository.save(token);
    }

    // ── Gửi email OTP ─────────────────────────────────────────────────────────
    private void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setSubject("[Drip Lab] Mã xác nhận đặt lại mật khẩu");
        mail.setText(
                "Xin chào,\n\n" +
                        "Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản Drip Lab.\n\n" +
                        "Mã OTP của bạn là: " + otp + "\n\n" +
                        "Mã có hiệu lực trong 10 phút. Vui lòng không chia sẻ mã này với bất kỳ ai.\n\n" +
                        "Nếu bạn không thực hiện yêu cầu này, hãy bỏ qua email này.\n\n" +
                        "Trân trọng,\nDrip Lab Team ☕"
        );
        mailSender.send(mail);
    }
}