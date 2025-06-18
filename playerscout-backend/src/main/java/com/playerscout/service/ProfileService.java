package com.playerscout.service;

import com.playerscout.model.Profile;
import com.playerscout.model.User;
import com.playerscout.repository.ProfileRepository;
import com.playerscout.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * ProfileService contains business logic for managing player profiles.
 * It uses the ProfileRepository to interact with the database.
 */
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Create or update a profile for a user.
     * If the user already has a profile, update it. Otherwise, create a new one.
     */
    public Profile saveOrUpdateProfile(String username, Profile profileData) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Profile> existing = profileRepository.findByUser(user);
        Profile profile = existing.orElse(new Profile());
        // Set fields from profileData
        profile.setName(profileData.getName());
        profile.setTeam(profileData.getTeam());
        profile.setPosition(profileData.getPosition());
        profile.setJerseyNumber(profileData.getJerseyNumber());
        profile.setAge(profileData.getAge());
        profile.setGoals(profileData.getGoals());
        profile.setAssists(profileData.getAssists());
        profile.setSpeed(profileData.getSpeed());
        profile.setVideoLinks(profileData.getVideoLinks());
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    /**
     * Get a profile by username (public view).
     */
    public Optional<Profile> getProfileByUsername(String username) {
        return userRepository.findByUsername(username)
                .flatMap(profileRepository::findByUser);
    }

    /**
     * Get the profile for a specific user (private view).
     */
    public Optional<Profile> getProfileForUser(User user) {
        return profileRepository.findByUser(user);
    }
}
