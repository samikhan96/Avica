package com.example.myapplication.AvicaPatient.UI.Patient.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.AvicaPatient.Models.Chat.ChatResponse;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;
import com.example.myapplication.AvicaPatient.api.ApiService;
import com.example.myapplication.AvicaPatient.api.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity {

    private LinearLayout messagesContainer;
    private EditText messageEditText;
    private TextView name;
    private ImageView sendButton, prpfile_img;
    private ScrollView messagesScrollView;

    private String existingRoomId;
    private String userId;
    private User user;
    private Socket mSocket;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdetail);

        user = UserPrefs.getGetUser();
        if (user == null) {
            Log.e("ChatDetailActivity", "User object is null. Exiting activity.");
            finish();
            return;
        }

        authToken = user.token;
        if (authToken == null) {
            Log.e("ChatDetailActivity", "authToken is null. Exiting activity.");
            finish();
            return;
        }

        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.reconnection = true;
            mSocket = IO.socket("https://avica.up.railway.app/", options);
            mSocket.connect();
        } catch (Exception e) {
            Log.e("SocketInit", "Socket initialization error", e);
        }

        // Retrieve intent data
        Intent intent = getIntent();
        if (intent != null) {
            existingRoomId = intent.getStringExtra("chatRoomId");
            userId = intent.getStringExtra("id");

            String firstName = intent.getStringExtra("firstName");
            String lastName = intent.getStringExtra("lastName");
            String image_uri = intent.getStringExtra("uri");

            name = findViewById(R.id.name);
            prpfile_img = findViewById(R.id.prpfile_img);
            name.setText(firstName + " " + lastName);

            Glide.with(this)
                    .load(image_uri)
                    .apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.app_icon)
                            .error(R.drawable.app_icon))
                    .into(prpfile_img);

            List<String> previousMessages = intent.getStringArrayListExtra("PREVIOUS_MESSAGES");
            if (previousMessages != null) {
                for (String message : previousMessages) {
                    addMessage(message, false);
                }
            }
        }

        // Initialize views
        messagesContainer = findViewById(R.id.messagesContainer);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        messagesScrollView = findViewById(R.id.messagesScrollView);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(view -> finish());

        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage();
            } else {
                Log.d("ChatDetailActivity", "Attempted to send empty message.");
            }
        });

        if (existingRoomId != null) {
            mSocket.on(Socket.EVENT_CONNECT, args -> {
                Log.d("SOCKET", "Connected to socket server");
                mSocket.emit("join", existingRoomId);
                listenToRoomMessages();
            });
            fetchChats();
        } else {
            Log.e("ChatDetailActivity", "existingRoomId is null. Skipping socket operations.");
        }
    }

    private void sendMessage() {
        try {
            String messageText = messageEditText.getText().toString().trim();
            if (messageText.isEmpty()) return;

            JSONObject author = new JSONObject();
            author.put("first_name", user.first_name);
            author.put("last_name", user.last_name);
            author.put("middle_name", user.middle_name != null ? user.middle_name : "");
            author.put("uri", user.uri != null ? user.uri : "");
            author.put("id", userId != null ? userId : "");
            author.put("role", user.role);

            JSONObject message = new JSONObject();
            message.put("message", messageText);
            message.put("created_at", System.currentTimeMillis());
            message.put("author_id", user.id);
            message.put("author", author);
            message.put("room_id", existingRoomId);
            message.put("media", "");

            mSocket.emit("message", message);
            addMessage(messageText, true);
            messageEditText.setText("");
        } catch (Exception e) {
            Log.e("sendMessage", "Error sending message", e);
        }
    }

    private void addMessage(String messageText, boolean isSent) {
        int layoutId = isSent ? R.layout.item_message_sent : R.layout.item_message_received;
        View messageView = LayoutInflater.from(this).inflate(layoutId, messagesContainer, false);
        TextView messageTextView = messageView.findViewById(R.id.messageText);
        messageTextView.setText(messageText);
        messagesContainer.addView(messageView);
        messagesScrollView.post(() -> messagesScrollView.fullScroll(View.FOCUS_DOWN));
    }

    private void listenToRoomMessages() {
        if (existingRoomId == null) return;

        mSocket.off(existingRoomId);
        mSocket.on(existingRoomId, args -> {
            runOnUiThread(() -> {
                try {
                    Log.d("SOCKET_EVENT", "Room message received: " + args[0]);
                    JSONObject data = (JSONObject) args[0];
                    JSONArray messagesArray = data.getJSONArray(existingRoomId);

                    if (messagesArray.length() > 0) {
                        JSONObject lastMessage = messagesArray.getJSONObject(messagesArray.length() - 1);
                        String messageText = lastMessage.optString("message", "");
                        String authorID = lastMessage.optString("author_id", "");
                        boolean isMe = authorID.equals(user.id);
                        addMessage(messageText, isMe);
                        messagesScrollView.post(() -> messagesScrollView.fullScroll(View.FOCUS_DOWN));
                    }
                } catch (Exception e) {
                    Log.e("listenToRoomMessages", "Error parsing message", e);
                }
            });
        });
    }

    private void fetchChats() {
        AppUtils.showProgressDialog(this);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Log.d("ChatDetailActivity", "Fetching chats for room: " + existingRoomId);

        Call<ChatResponse> call = apiService.getChats(existingRoomId, "Bearer " + authToken);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                AppUtils.dismisProgressDialog(ChatDetailActivity.this);
                if (response.isSuccessful() && response.body() != null) {
                    List<ChatResponse.Chat> chats = response.body().getChats();
                    for (ChatResponse.Chat chat : chats) {
                        boolean isMe = chat.getSender().equals(user.id);
                        addMessage(chat.getMessage(), isMe);
                    }
                    messagesScrollView.post(() -> messagesScrollView.fullScroll(View.FOCUS_DOWN));
                } else {
                    Log.e("ChatDetailActivity", "No chats or response failed. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                AppUtils.dismisProgressDialog(ChatDetailActivity.this);
                Log.e("ChatDetailActivity", "Failed to fetch chats: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mSocket != null && mSocket.connected()) {
            mSocket.disconnect();
        }
        super.onDestroy();
    }
}
