package com.example.myapplication.AvicaPatient.api;



import com.example.myapplication.AvicaPatient.Models.Chat.ChatResponse;
import com.example.myapplication.AvicaPatient.Models.Chat.ChatRoom;
import com.example.myapplication.AvicaPatient.Models.Chat.SearchUserChat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

}
