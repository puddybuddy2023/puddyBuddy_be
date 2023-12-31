package com.example.puddyBuddy.service;

import com.example.puddyBuddy.domain.BreedTag;
import com.example.puddyBuddy.domain.PersonalColor;
import com.example.puddyBuddy.domain.Prefer;
import com.example.puddyBuddy.domain.User;
import com.example.puddyBuddy.domain.Petsize;
import com.example.puddyBuddy.dto.prefer.PreferCreateRes;
import com.example.puddyBuddy.dto.prefer.PreferNumRes;
import com.example.puddyBuddy.dto.prefer.PreferRes;
import com.example.puddyBuddy.exception.common.BusinessException;
import com.example.puddyBuddy.exception.common.ErrorCode;
import com.example.puddyBuddy.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreferService {
    private final PreferRepository preferRepository;
    private final UserRepository userRepository;
    private final PersonalColorRepository personalColorRepository;
    private final BreedTagRepository breedTagRepository;
    private final PetsizeRepository petsizeRepository;


//    public List<Prefer> getPrefers(){
//        List<Prefer> prefers = preferRepository.findAll();
//        return prefers;
//    }

    public List<PreferRes> getPreferUser(Long userId) {
        List<Prefer> prefers = preferRepository.findByUserUserIdOrderByPreferIdAsc(userId);
        if(prefers.isEmpty()){
            throw new BusinessException(ErrorCode.EMPTY_DATA);
        }
        List<PreferRes> response = prefers.stream()
                .map(PreferRes::new)
                .collect(Collectors.toList());
        return response;
    }

    public List<PreferNumRes> getPreferId(Long preferId) {
        Optional<Prefer> prefers = preferRepository.findByPreferId(preferId);
        if(prefers.isEmpty()){
            throw new BusinessException(ErrorCode.EMPTY_DATA);
        }
        List<PreferNumRes> response = prefers.stream()
                .map(PreferNumRes::new)
                .collect(Collectors.toList());
        return response;
    }

    public PreferCreateRes createPrefer(Long userId, String preferName, Long personalColorId, Long breedTagId) {
        Prefer newPrefer = new Prefer();
        PreferCreateRes preferCreateRes = new PreferCreateRes();

        // user
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_EXIST_USER);
        }
        newPrefer.setUser(user.get());

        // personal color
        if (personalColorId != null) {
            Optional<PersonalColor> personalColor = personalColorRepository.findByPersonalColorId(personalColorId);
            if (personalColor.isEmpty()) {
                throw new BusinessException(ErrorCode.NO_EXIST_PERSONALCOLORCODE);
            }
            newPrefer.setPersonalColor(personalColor.get());
        } else {
            // If personalColorId is not provided, set PersonalColor to default (id = 5)
            Optional<PersonalColor> defaultPersonalColor = personalColorRepository.findById(5L);
            if (defaultPersonalColor.isPresent()) {
                newPrefer.setPersonalColor(defaultPersonalColor.get());
            }
        }

        // breed
        if (breedTagId != null) {
            Optional<BreedTag> breedTag = breedTagRepository.findByBreedTagId(breedTagId);
            if (breedTag.isEmpty()) {
                throw new BusinessException(ErrorCode.NO_EXIST_BREEDTAGID);
            }
            newPrefer.setBreedTag(breedTag.get());
        } else {
            // If breedTagId is not provided, set BreedTag to null or handle it accordingly
            newPrefer.setBreedTag(null); // or handle it as needed
        }

        // petsize
        Optional<Petsize> defaultPetsize = petsizeRepository.findById(2L);
        if (defaultPetsize.isPresent()) {
            newPrefer.setPetsize(defaultPetsize.get());
        }

        // etc
        newPrefer.setName(preferName);

        Long newPreferId = preferRepository.save(newPrefer).getPreferId();
        preferCreateRes.setPreferId(newPreferId);

        return preferCreateRes;
    }

    public void deletePrefer(Long preferId) {
        preferRepository.deleteById(preferId);
    }
}