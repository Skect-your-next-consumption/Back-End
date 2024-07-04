package com.hana.api.diary.service;

import com.hana.api.diary.dto.request.DiaryCreateRequest;
import com.hana.api.diary.entity.PictureDiary;
import com.hana.api.diary.repository.PictureDiaryRepository;
import com.hana.api.user.entity.User;
import com.hana.common.exception.ErrorCode;
import com.hana.common.response.Response;
import com.hana.common.type.Role;
import com.hana.common.util.PictureDiaryGenerator;
import com.hana.common.util.UuidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureDiaryService {

    private final Response response;
    private final PictureDiaryRepository pictureDiaryRepository;
    private final PictureDiaryGenerator pictureDiaryGenerator;

    public ResponseEntity<?> create(DiaryCreateRequest diaryCreateRequest, User user) throws IOException, InterruptedException {
        String diaryCount = pictureDiaryRepository.countAllByUser(user)+1 + "";
        PictureDiary pictureDiary = PictureDiary.builder()
                .diaryCode(UuidGenerator.generateUuid())
                .diaryConcept(diaryCreateRequest.getDiaryConcept())
                .diaryTitle("그림일기" + diaryCount)
                .diaryTags(Map.of("tags", diaryCreateRequest.getDiaryTags()))
                .diaryPayments(Map.of("payments", diaryCreateRequest.getDiaryPayments()))
                .user(user)
                .build();
        pictureDiary.setDiaryImage(pictureDiaryGenerator.generatePictureDiary(pictureDiary,user));
        return response.success(pictureDiaryRepository.save(pictureDiary));
    }

    public ResponseEntity<?> updateDiaryTitle(String diaryId,String title, User user){
        PictureDiary pictureDiary = pictureDiaryRepository.findById(diaryId).orElse(null);
        if(pictureDiary == null){
            return response.fail(ErrorCode.DIARY_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        if(!pictureDiary.getUser().getUserCode().equals(user.getUserCode()) && !user.getUserRole().equals(Role.Admin)){
            return response.fail(ErrorCode.DIARY_NOY_AUTHORIZED, HttpStatus.BAD_REQUEST);
        }
        pictureDiary.setDiaryTitle(title);
        return response.success(pictureDiaryRepository.save(pictureDiary));
    }

    public ResponseEntity<?> getDiaryList(User user){
        JSONArray jsonArray = new JSONArray();
        for(PictureDiary pictureDiary : pictureDiaryRepository.findAllByUser(user)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("diaryCode", pictureDiary.getDiaryCode());
            jsonObject.put("diaryTitle", pictureDiary.getDiaryTitle());
            jsonObject.put("diaryConcept", pictureDiary.getDiaryConcept());
            jsonObject.put("diaryTags", pictureDiary.getDiaryTags());
            jsonObject.put("diaryPayments", pictureDiary.getDiaryPayments());
            jsonObject.put("diaryImage", pictureDiary.getDiaryImage());
            jsonObject.put("diaryCreateDate", pictureDiary.getCreatedDate());
            jsonArray.add(jsonObject);
        }
        return response.success(jsonArray);
    }

    public ResponseEntity<?> getDiaryInfo(String diaryCode, User user){
        Optional<PictureDiary> pictureDiary = pictureDiaryRepository.findByDiaryCode(diaryCode);
        if (user.equals(null)){
            return response.fail(ErrorCode.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        if(!pictureDiary.isPresent()){
            return response.fail(ErrorCode.DIARY_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        return response.success(pictureDiary);
    }

    public ResponseEntity<?> getHotDiaryCategory(){
        return response.success(pictureDiaryRepository.findHotDiaryCategory());
    }
}
