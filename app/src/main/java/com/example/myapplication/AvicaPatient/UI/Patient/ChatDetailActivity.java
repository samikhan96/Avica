package com.example.myapplication.AvicaPatient.UI.Patient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.AvicaPatient.Models.ChatResponse;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;
import com.example.myapplication.AvicaPatient.api.ApiService;
import com.example.myapplication.AvicaPatient.api.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity  {

    private LinearLayout messagesContainer;
    private EditText messageEditText;
    private TextView name;
    private ImageView sendButton;
    private ImageView prpfile_img;
    private ScrollView messagesScrollView;

    private String existingRoomId; // Store the room ID for fetching chats
    private String userId; // Store the room ID for fetching chats
    User user;
    private Socket mSocket;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatdetail); // Make sure this matches your XML layout file
        user=UserPrefs.getGetUser();
        authToken = user.token;

        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.reconnection = true;

            mSocket = IO.socket("https://avica.up.railway.app/", options);
            mSocket.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve the "type" and "measurement_id" from the intent
        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");
        String image_uri = getIntent().getStringExtra("uri");
        name=findViewById(R.id.name);
        prpfile_img=findViewById(R.id.prpfile_img);
        name.setText(firstName+" " +lastName);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon);
        Glide.with(ChatDetailActivity.this).load(image_uri).apply(options).into(prpfile_img);


        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Initialize views
        messagesContainer = findViewById(R.id.messagesContainer);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        messagesScrollView = findViewById(R.id.messagesScrollView);
        // Get user data from intent
        Intent intent = getIntent();
        if (intent != null) {
            existingRoomId = intent.getStringExtra("chatRoomId"); // Save the chat room ID
            userId = intent.getStringExtra("id"); // Save the chat room ID
            Log.d("ChatDetailActivity", "Received chatRoomId: " + existingRoomId);

            // Display previous messages
            ArrayList<String> previousMessages = intent.getStringArrayListExtra("PREVIOUS_MESSAGES");
            if (previousMessages != null) {
                for (String message : previousMessages) {
                    addMessage(message, false);
                }
            } else {
                Log.d("ChatDetailActivity", "No previous messages found.");
            }
        }

        // Send button click listener
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            if (!message.isEmpty()) {
                sendMessage();
            } else {
                Log.d("ChatDetailActivity", "Attempted to send an empty message.");
            }
        });


        mSocket.on(Socket.EVENT_CONNECT, args -> {
            Log.d("SOCKET", "Connected");

            // Join the room if needed by backend
            mSocket.emit("join", existingRoomId);

            // Start listening to room messages
            listenToRoomMessages();
        });
        fetchChats();
    }


    private void sendMessage() {
        try {
            String messageText = messageEditText.getText().toString();
            if (messageText.isEmpty()) return;

            JSONObject author = new JSONObject();
            author.put("first_name", "Ali");
            author.put("last_name", "Raza");
            author.put("middle_name", "K");
            author.put("uri", user.uri);
            author.put("id", userId);
            author.put("role", user.role);

            JSONObject message = new JSONObject();
            message.put("message", messageText);
            message.put("created_at", System.currentTimeMillis());
            message.put("author_id", user.id);
            message.put("author", author);
            message.put("room_id", existingRoomId);
            message.put("media", "");

            mSocket.emit("message", message);

            addMessage(messageText,true);
            messageEditText.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addMessage(String messageText, boolean isSent) {
        int layoutId = isSent ? R.layout.item_message_sent : R.layout.item_message_received;
        View messageView = LayoutInflater.from(this).inflate(layoutId, messagesContainer, false);
        TextView messageTextView = messageView.findViewById(R.id.messageText);
        messageTextView.setText(messageText);
        messagesContainer.addView(messageView);
        messagesScrollView.post(() -> messagesScrollView.fullScroll(View.FOCUS_DOWN));
        Log.d("ChatDetailActivity", "Added message: " + messageText + ", Sent: " + isSent);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    private void listenToRoomMessages() {
        // Remove existing listener for safety
        mSocket.off(existingRoomId);

        mSocket.on(existingRoomId, args -> {
            runOnUiThread(() -> {
                try {
                    Log.d("SOCKET_EVENT", "📥 Room message received for " + existingRoomId + ": " + args[0]);

                    JSONObject data = (JSONObject) args[0];
                    JSONArray messagesArray = data.getJSONArray(existingRoomId);
                    messagesContainer.removeAllViews();
                    String myUserId = user.id;
                    for (int i = 0; i < messagesArray.length(); i++) {
                        JSONObject messageDict = messagesArray.getJSONObject(i);
                        String messageText = messageDict.optString("message", "");
                        String authorID = messageDict.optString("author_id", "");
                        boolean isMe = authorID.equals(myUserId);
                        addMessage(messageText, isMe);
                    }
                    messagesScrollView.post(() -> messagesScrollView.fullScroll(View.FOCUS_DOWN));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
    private void fetchChats() {
        AppUtils.showProgressDialog(ChatDetailActivity.this);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Log.d("ChatDetailActivity", "Fetching chats for room ID: " + existingRoomId);
        Call<ChatResponse> call = apiService.getChats(existingRoomId, "Bearer " + authToken);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ChatResponse.Chat> chats = response.body().getChats();
                    Log.d("ChatDetailActivity", "Chats fetched successfully. Number of chats: " + chats.size());

                    // Display each chat message in the messagesContainer
                    for (ChatResponse.Chat chat : chats) {
                        Log.d("ChatDetailActivity", "Sender: " + chat.getSender() + ", Message: " + chat.getMessage());
                        addMessage(chat.getMessage(), chat.getSender().equals(user.id)); // Replace "your_user_id" with the actual sender ID of the logged-in user
                    }
                    AppUtils.dismisProgressDialog(ChatDetailActivity.this);

                    // Scroll to the bottom of the message container after loading messages
                    messagesScrollView.post(() -> messagesScrollView.fullScroll(View.FOCUS_DOWN));
                } else {
                    Log.e("ChatDetailActivity", "No chats found or response not successful. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                AppUtils.dismisProgressDialog(ChatDetailActivity.this);
                Log.e("ChatDetailActivity", "Failed to fetch chats: " + t.getMessage());
            }
        });
    }

}
