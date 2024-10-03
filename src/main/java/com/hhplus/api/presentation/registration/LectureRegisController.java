package com.hhplus.api.presentation.registration;

import com.hhplus.api.application.CourseInfo;
import com.hhplus.api.application.RegistInfo;
import com.hhplus.api.application.RegistrationFacade;
import com.hhplus.api.presentation.common.exception.CourseNotFoundException;
import com.hhplus.api.presentation.common.response.ApiResponse;
import com.hhplus.api.presentation.common.response.ApiResponseHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecture")
public class LectureRegisController {

    private final RegistrationFacade registrationFacade;

    public LectureRegisController(RegistrationFacade registrationFacade) {
        this.registrationFacade = registrationFacade;
    }

    /**
     * 특강 신청
     */
    @PostMapping("/regis")
    public ApiResponse regist(@RequestBody RegistRequest request) {
        RegistInfo info = registrationFacade.regist(request);
        if (info == null){
            throw new CourseNotFoundException(new ApiResponseHeader(400, "해당 강의를 찾을 수 없습니다."));
        }
        return ApiResponse.success("data", info);
    }


    /**
     * 날짜별 특강 선택가능 목록
     */
    @GetMapping("/course/list")
    public ApiResponse selectableList(@RequestParam(name = "date") String date,
                                      @RequestParam(name = "userId") Long userId) {
        List<CourseInfo> info = registrationFacade.selectableCourseList(date, userId);
        if (info == null){
            throw new CourseNotFoundException(new ApiResponseHeader(400, "해당 강의를 찾을 수 없습니다."));
        }
        return ApiResponse.success("data", info);
    }


    /**
     * 특강 신청완료 목록
     */
    @GetMapping("/lecture/list/{userId}")
    public ApiResponse completeRegistLectureList(@PathVariable Long userId) {
        List<CourseInfo> info = registrationFacade.registCompleteList(userId);
        if (info == null){
            throw new CourseNotFoundException(new ApiResponseHeader(400, "해당 강의를 찾을 수 없습니다."));
        }
        return ApiResponse.success("data", info);
    }


}
