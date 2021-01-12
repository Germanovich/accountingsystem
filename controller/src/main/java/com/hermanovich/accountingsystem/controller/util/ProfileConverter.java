package com.hermanovich.accountingsystem.controller.util;

import com.hermanovich.accountingsystem.dto.profile.ProfileDto;
import com.hermanovich.accountingsystem.model.profile.Profile;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ProfileConverter {

    public ProfileDto convertProfileToProfileDto(Profile profile) {
        return ProfileDto.builder()
                .id(profile.getId())
                .name(profile.getName())
                .surname(profile.getSurname())
                .sex(profile.getSex())
                .dateOfBirth(profile.getDateOfBirth())
                .dateOfRegistration(profile.getDateOfRegistration())
                .build();
    }

    public Profile convertProfileDtoToProfile(ProfileDto profileDto) {
        return Profile.builder()
                .id(profileDto.getId())
                .name(profileDto.getName())
                .surname(profileDto.getSurname())
                .sex(profileDto.getSex())
                .dateOfBirth(profileDto.getDateOfBirth())
                .dateOfRegistration(profileDto.getDateOfRegistration())
                .build();
    }
}
