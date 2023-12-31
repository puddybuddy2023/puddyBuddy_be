package com.example.puddyBuddy.controller;

import com.example.puddyBuddy.domain.Comment;
import com.example.puddyBuddy.dto.comment.CommentListRes;
import com.example.puddyBuddy.dto.petsize.PetInfoRes;
import com.example.puddyBuddy.dto.petsize.PetsizeCreateReq;
import com.example.puddyBuddy.dto.petsize.PetsizeCreateRes;
import com.example.puddyBuddy.dto.petsize.PetsizeInfoRes;
import com.example.puddyBuddy.dto.prefer.PreferCreateRes;
import com.example.puddyBuddy.exception.common.BusinessException;
import com.example.puddyBuddy.response.BaseResponse;
import com.example.puddyBuddy.service.PetsizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RestController
@RequestMapping( value = "/petsize",  produces = "application/json;charset=utf8")
@Tag(name = "펫사이즈 API")
public class PetsizeController {
    private final PetsizeService petsizeService;

    public PetsizeController(PetsizeService petsizeService) {
        this.petsizeService = petsizeService;
    }

    @Operation(summary = "사이즈 조회", description = "사이즈 조회하도록")
    @GetMapping("/show/{petsizeId}")
    public BaseResponse<PetInfoRes> getSizeInfo(@PathVariable Long petsizeId) {
        try {
            PetInfoRes petInfoRes = petsizeService.getInfo(petsizeId);
            return new BaseResponse<>(petInfoRes);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }



    @Operation(summary = "사이즈 등록", description = "사이즈 정보를 주면 그걸 등록합니다.")
    @PostMapping("/create")
    public BaseResponse<PetsizeCreateRes> createSize(@RequestBody PetsizeCreateReq petsizeCreateReq) {
        try {
            PetsizeCreateRes petsizeCreateRes = petsizeService.createSize(
                    petsizeCreateReq.getPreferId(),
                    petsizeCreateReq.getNeck(),
                    petsizeCreateReq.getChest(),
                    petsizeCreateReq.getBack(),
                    petsizeCreateReq.getLeg()
            );
            return new BaseResponse<>(petsizeCreateRes);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }

    @Operation(summary = "펫 사이즈 정보 조회", description = "펫 사이즈 정보를 조회합니다.")
    @GetMapping("/getInfo/{petsizeId}")
    public BaseResponse<PetsizeInfoRes> getPercentages(@PathVariable Long petsizeId) {
        try {
            PetsizeInfoRes petsizeInfoRes = petsizeService.getPercentages(petsizeId);
            return new BaseResponse<>(petsizeInfoRes);
        } catch (BusinessException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }
}
