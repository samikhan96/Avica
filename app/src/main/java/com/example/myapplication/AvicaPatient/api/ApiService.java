package com.example.myapplication.AvicaPatient.api;



import com.example.myapplication.AvicaPatient.Models.ChatResponse;
import com.example.myapplication.AvicaPatient.Models.ChatRoom;
import com.example.myapplication.AvicaPatient.Models.SearchUserChat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/v1/web/chat/search/users")
    Call<List<SearchUserChat.Chat_User>> searchUsers(@Query("query") String query, @Header("Authorization") String token);

    // ApiService.java
    @GET("api/v1/web/chat/room")
    // Make sure this is the correct endpoint
    Call<List<ChatRoom>> getChatRooms(@Header("Authorization") String token);

    @GET("api/v1/web/chat/room/{roomId}")
    Call<ChatResponse> getChats(
            @Path("roomId") String roomId,
            @Header("Authorization") String token
    );
//
//
//    @GET("/api/v1/web/telemed/search")
//    Call<List<TeleMedSearch>> searchTeleMed(@Header("Authorization") String token);
//
//    @GET("/api/v1/web/telemed")
//    Call<List<TeleMedAppointment>> getTeleMedAppointment(@Header("Authorization") String token);
//
//
//    @GET("/api/v1/web/telemed/view/{id}")
//    Call<TeleMedAppointment> getAppointmentDetails(@Header("Authorization") String token, @Path("id") String appointmentId);
//
//    @GET("/join-meeting/{id}")
//    Call<JoinedMeetingProps> joinMeeting(@Path("id") String id);
//
//
//    @GET("api/v1/vl/appPatients")
//    Call<PatientListResponse> getPatientList(@Header("Authorization") String authToken);
//
//
//    // Define the GET request with the dynamic ECG record ID
//    @GET("api/v1/vl/patients/{ecgRecordId}")
//    Call<ECGResponse> getECGData(@Path("ecgRecordId") String ecgRecordId,@Header("Authorization") String token);

}
