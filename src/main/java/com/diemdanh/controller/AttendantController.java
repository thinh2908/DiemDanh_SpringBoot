package com.diemdanh.controller;

import com.diemdanh.Utils.SessionHelper;
import com.diemdanh.base.BaseFunction;
import com.diemdanh.base.Constants;
import com.diemdanh.base.CoverStringToTime;
import com.diemdanh.factory.FilesStorageService;
import com.diemdanh.model.Attendant;
import com.diemdanh.model.Employee;
import com.diemdanh.model.Users;
import com.diemdanh.request.AttendantRequest;
import com.diemdanh.response.AttendantResponse;
import com.diemdanh.service.Impl.AttendantServiceImpl;
import com.diemdanh.service.Impl.EmployeeServiceImpl;
import com.diemdanh.service.Impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.swagger.annotations.ResponseHeader;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/attendant")
public class AttendantController {

    @Autowired
    private AttendantServiceImpl attendantService;
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FilesStorageService filesStorageService;

//    @GetMapping("")
//    public ResponseEntity<?> listALlAttendant(){
//        List<Attendant> attendantList = attendantService.listAttendant();
//        List<AttendantResponse> attendantResponseList = attendantList.stream().map(AttendantResponse::new)
//                                                        .collect(Collectors.toList());
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Content-Range",
//                "users 0-1/"+attendantList.size());
//        return ResponseEntity.ok().headers(httpHeaders).body(attendantResponseList);
//    }

    @GetMapping("/day")
    public ResponseEntity<?> listALlAttendantWithStartAndEndDay(@RequestParam String startDay, @RequestParam String endDay){
        Users currentUser = SessionHelper.getCurrentUser();
        LocalDateTime start = CoverStringToTime.cover2(startDay);
        LocalDateTime end = CoverStringToTime.cover2(endDay);
        List<Attendant> attendantList = attendantService.listAttendantBetweenDayForUser(currentUser,start,end);
        List<AttendantResponse> attendantResponseList = attendantList.stream().map(AttendantResponse::new)
                .collect(Collectors.toList());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Range",
                "users 0-1/"+attendantList.size());
        return ResponseEntity.ok().headers(httpHeaders).body(attendantResponseList);
    }

    @GetMapping("/day/{userId}")
    public ResponseEntity<?> listALlAttendantWithStartAndEndDayByManager
                (@RequestParam String startDay, @RequestParam String endDay, @PathVariable Long userId){
        Users user = userService.getUserById(userId);
        LocalDateTime start = CoverStringToTime.cover2(startDay);
        LocalDateTime end = CoverStringToTime.cover2(endDay);
        List<Attendant> attendantList = attendantService.listAttendantBetweenDayForUser(user,start,end);
        List<AttendantResponse> attendantResponseList = attendantList.stream().map(AttendantResponse::new)
                .collect(Collectors.toList());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Range",
                "users 0-1/"+attendantList.size());
        return ResponseEntity.ok().headers(httpHeaders).body(attendantResponseList);
    }


    @PostMapping("")
    public ResponseEntity<?> addAttendantForCurrentUser(@ModelAttribute AttendantRequest attendantRequest) throws Exception {
        Users currentUser = SessionHelper.getCurrentUser();
        byte[] imageByte= Base64.decodeBase64(attendantRequest.getFaceImage().split("base64,")[1]);

        String directory=filesStorageService.convertRelativeToAbsolutePath("/uploads").toString()
                + "/User"+ currentUser.getEmployee().getId() + "_temp.jpg";

        String pictureUrl = Constants.BASE_URL + "/files/images/" + "User"+currentUser.getEmployee().getId() + "_temp.jpg";




        FileOutputStream file = new FileOutputStream(directory);
        file.write(imageByte);
        file.close();

        URL url = new URL("http://localhost:5000/api/face-recognize");

        // Mở kết nối HTTP
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Đặt phương thức yêu cầu là POST
        connection.setRequestMethod("POST");

        // Thiết lập header của yêu cầu
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        // Bật việc gửi dữ liệu lên server
        connection.setDoOutput(true);

        // Tạo một Map để đại diện cho nội dung yêu cầu
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("validFaceImage",currentUser.getEmployee().getAvatar());
        requestBodyMap.put("image", pictureUrl);
        System.out.println(pictureUrl);
        System.out.println(currentUser.getEmployee().getAvatar());
        // Chuyển đổi Map thành một đối tượng JSON
        Gson gson = new Gson();

        String requestBodyJson = gson.toJson(requestBodyMap);

        // Gửi dữ liệu yêu cầu lên server
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(requestBodyJson.getBytes());
        outputStream.flush();

        // Đọc phản hồi từ máy chủ
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        Map<String,Boolean> responseMap = BaseFunction.stringToMapStringBoolean(response.toString());

        // In ra phản hồi
        System.out.println(responseMap.get("isReal"));
        System.out.println("Response: " + response.toString());

        // Đóng kết nối
        connection.disconnect();

        if(!responseMap.get("isReal")){
            return ResponseEntity.badRequest().body("{'message' : 'Fake is fake'}");
        }

        if(!responseMap.get("result")){
            return ResponseEntity.badRequest().body("{'message' : 'invalid face'}");
        }
        Attendant attendant = new Attendant(attendantRequest);
        attendant.setUser(currentUser);
        attendantService.createAttendant(attendant);
        return ResponseEntity.ok(new AttendantResponse(attendant));

    }
}
