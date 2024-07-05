package com.hana.api.admin.service;

import com.hana.api.admin.dto.response.AccumulatedAmountResponseDto;
import com.hana.common.response.Response;
import com.hana.common.util.DateParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final Response response;
    @Value("${file.path}")
    private String filePath;

    public ResponseEntity<?> getAccumulatedAmounts(int period){

        //String filePath = "src/main/resources/Accumulated_amount.txt";
        List<AccumulatedAmountResponseDto> amounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int days = period;
            String line;

            while ((line = br.readLine()) != null) {

                // days 까지의 내역만 전송
                if(days <= 0) break;
                else days -= 1;

                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    LocalDate date = DateParser.parseDate(parts[0].trim());
                    String accumulatedAmount = parts[1].split(":")[1].trim();
                    String addAmount = parts[2].split(":")[1].trim();
                    String refundedAmount = parts[3].split(":")[1].trim();
                    amounts.add(new AccumulatedAmountResponseDto(date, accumulatedAmount, addAmount, refundedAmount));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response.success(amounts);
    }

    public void addAmount(LocalDate date, long accumulatedAmount, long refundedAmount, long addAmount){

        // 새로운 데이터를 생성
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String formattedDate = date.format(formatter);
        String newEntry = String.format("%s|누적액:%d|참가액:%d|환급액:%d", formattedDate, accumulatedAmount, addAmount, refundedAmount);

        try {
            // 기존 파일 읽기
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            // 새로운 데이터를 리스트의 첫 번째 줄에 삽입
            lines.add(0, newEntry);

            // 파일에 다시 쓰기
            Files.write(path, lines);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
