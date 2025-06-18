package com.playerscout.controller;

import com.playerscout.model.Profile;
import com.playerscout.model.User;
import com.playerscout.service.ProfileService;
import com.playerscout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * ProfileController exposes API endpoints for managing player profiles.
 * Some endpoints are protected and require a valid JWT.
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;

    /**
     * Create or update the logged-in user's profile.
     * Protected: requires JWT.
     */
    @PostMapping
    public ResponseEntity<?> saveOrUpdateProfile(@RequestBody Profile profileData, Authentication authentication) {
        String username = authentication.getName(); // Extract username from JWT
        Profile saved = profileService.saveOrUpdateProfile(username, profileData);
        return ResponseEntity.ok(saved);
    }

    /**
     * Get the logged-in user's profile.
     * Protected: requires JWT.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Profile> profileOpt = profileService.getProfileForUser(userOpt.get());
        return profileOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get a public profile by username.
     * Public: does not require JWT.
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getProfileByUsername(@PathVariable String username) {
        Optional<Profile> profileOpt = profileService.getProfileByUsername(username);
        return profileOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
