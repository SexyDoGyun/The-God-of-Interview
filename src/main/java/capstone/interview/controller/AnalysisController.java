package capstone.interview.controller;

import capstone.interview.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    @Autowired
    AnalysisService analysisService;

    @PostMapping("/save-analysis")
    public ResponseEntity<String> saveAnalysis(@RequestBody Map<String, Object> analyzedData) {
        // 사용자 이름, 직업, 질문 데이터 추출
        String userIdStr = (String) analyzedData.get("user_id");
        String userName = (String) analyzedData.get("user_name");
        String job = (String) analyzedData.get("job");

        Integer userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid user ID format");
        }

        // 질문 데이터가 배열로 전달된다고 가정하고 List<String>으로 캐스팅
        List<String> questions = (List<String>) analyzedData.get("questions");

        // 필수 데이터가 null인지 확인
        if (userId == null || userName == null || job == null || questions == null || questions.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing required fields: user_id, user_name, job, or questions");
        }

        try {
            // 분석 결과와 함께 서비스로 전달
            analysisService.saveAnalysisResults(analyzedData, userId, userName, job, questions);
        } catch (ClassCastException e) {
            return ResponseEntity.badRequest().body("Invalid data format in request body");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while saving analysis data");
        }

        return ResponseEntity.ok("Analysis data saved successfully");
    }

}
