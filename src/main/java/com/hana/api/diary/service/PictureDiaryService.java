package com.hana.api.diary.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana.api.account.dto.response.AccountLogResponse;
import com.hana.api.account.repository.AccountHistoryRepository;
import com.hana.api.diary.dto.request.DiaryCreateRequest;
import com.hana.api.diary.dto.response.DiaryInfoResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureDiaryService {

    private final Response response;
    private final PictureDiaryRepository pictureDiaryRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final PictureDiaryGenerator pictureDiaryGenerator;
    private final ObjectMapper objectMapper;

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

public ResponseEntity<?> regenerate(String diaryCode, User user) throws IOException, InterruptedException {
        Optional<PictureDiary> diary = pictureDiaryRepository.findById(diaryCode);
        if(!diary.isPresent()){
            return response.fail(ErrorCode.DIARY_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        if(!diary.get().getUser().getUserCode().equals(user.getUserCode()) && !user.getUserRole().equals(Role.Admin)){
            return response.fail(ErrorCode.DIARY_NOY_AUTHORIZED, HttpStatus.BAD_REQUEST);
        }
        diary.get().setDiaryImage(pictureDiaryGenerator.generatePictureDiary(diary.get(),user));
        return response.success(pictureDiaryRepository.save(diary.get()));
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
        for(PictureDiary pictureDiary : pictureDiaryRepository.findAllByUserOrderByCreatedDateDesc(user)){
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
        if (user == null){
            return response.fail(ErrorCode.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        Optional<PictureDiary> pictureDiary = pictureDiaryRepository.findByDiaryCode(diaryCode);
        if(!pictureDiary.isPresent()){
            return response.fail(ErrorCode.DIARY_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }

        List<String> payList = extractPaymentsArray(pictureDiary.get().getDiaryPayments());
        List<AccountLogResponse> diaryPayments = new ArrayList<>();

        for (String payId : payList) {
            accountHistoryRepository.findById(payId).ifPresent(accountHistory ->
                    diaryPayments.add(new AccountLogResponse(accountHistory))
            );
        }

        DiaryInfoResponseDto diaryInfoResponseDto = DiaryInfoResponseDto.builder()
                .createdDate(pictureDiary.get().getCreatedDate())
                .state(pictureDiary.get().getState())
                .diaryCode(pictureDiary.get().getDiaryCode())
                .diaryTitle(pictureDiary.get().getDiaryTitle())
                .diaryImage(pictureDiary.get().getDiaryImage())
                .diaryTags(pictureDiary.get().getDiaryTags())
                .diaryPayments(diaryPayments)
                .diaryConcept(pictureDiary.get().getDiaryConcept())
                .build();

        return response.success(diaryInfoResponseDto);
    }

    public ResponseEntity<?> getHotDiaryCategory(){
        return response.success(pictureDiaryRepository.findHotDiaryCategory());
    }

    public List<String> extractPaymentsArray(Map<String, Object> json){
        try {
            // Map에서 JSON 문자열 추출
            String diaryPaymentsJson = objectMapper.writeValueAsString(json);

            // JSON 문자열을 JsonNode 객체로 변환
            JsonNode jsonNode = objectMapper.readTree(diaryPaymentsJson);

            // 'payments' 배열 추출
            JsonNode paymentsNode = jsonNode.get("payments");

            // JsonNode를 List<String>으로 변환
            return objectMapper.convertValue(paymentsNode, List.class);

        } catch (IOException e) {
            throw new RuntimeException("JSON 변환 실패", e);
        }
    }
    public ResponseEntity<?> getRecentDiary(User user){
        List<String> diaryImages = new ArrayList<>();
        for(PictureDiary pictureDiary : pictureDiaryRepository.findTop6ByUserOrderByCreatedDateDesc(user)){
            diaryImages.add(pictureDiary.getDiaryImage());
        }
        return response.success(diaryImages);
    }
}
