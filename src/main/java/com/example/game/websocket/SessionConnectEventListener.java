package com.example.game.websocket;

import com.example.game.Game.gameDataDto.JsonStringBuilder;
import com.example.game.Game.h2Package.GameRoom;
import com.example.game.Game.repository.GameRoomRepository;
import com.example.game.model.user.User;
import com.example.game.repository.user.UserRepository;
import com.example.game.security.jwt.JwtDecoder;
import com.example.game.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class SessionConnectEventListener {

    private final JwtDecoder jwtDecoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final GameRoomRepository gameRoomRepository;
    private final JsonStringBuilder jsonStringBuilder;
    private final SimpMessageSendingOperations sendingOperations;
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

////     SessionConnect시 username을 전달하여 사용자 목록에 추가
//    @EventListener
//    public List<String> handleWebSocketConnectListener(SessionConnectedEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//        String username = jwtDecoder.decodeUsername(headers.getFirstNativeHeader("token"));
//
//        if (StompCommand.CONNECTED == headers.getCommand()) {
//            System.out.println(username + " 님이 WebSocket에 연결되었습니다.");
//
//            if (username != null) {
//                userList.add(username);
//                System.out.println(userList + "접속유저 리스트에서 " + username + " 유저를 추가하였습니다." + userList.size() + " 명 접속 중");
//            }
//            return userList;
//        }
//        return userList;
//    }

    // SessionDisconnect시 username을 전달하여 사용자 목록에서 삭제
//    @EventListener
//    public List<String> handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//        String username = jwtDecoder.decodeUsername(headers.getFirstNativeHeader("token"));
//
//        if (StompCommand.DISCONNECT == headers.getCommand()) {
//            System.out.println(username + " 님이 WebSocket에서 연결을 끊었습니다.");
//            if (username != null) {
//                ResponseEntity.ok("User Disconnected : " + username);
//
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setType(ChatMessage.MessageType.LEAVE);
//                chatMessage.setSender(username);
//
//                System.out.println("User Disconnected : " + username);
//
//                userList.remove(username);
//                System.out.println(userList + "접속유저 리스트에서 " + username + " 유저를 삭제하였습니다." + userList.size() + " 명 접속 중");
//
//                messagingTemplate.convertAndSend("/sub/public", chatMessage);
//            }
//        }
//        return userList;
//    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws JsonProcessingException {
        System.out.println("디스커넥트 리스너 발동");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        User user = userRepository.findBySessionId(headerAccessor.getSessionId());
        System.out.println("DisconnectListener에서 SessionId로 찾은 유저 : " + user);
        ChatMessage chatMessage = new ChatMessage();
            if (user != null) {
                System.out.println("디스커넥트 리스너에서 조회된 유저 " + user.getUsername());
                String roomId = user.getRoomId();
                if (roomId != null) {
                    Long userId = user.getId();
                    GameRoom gameRoom = gameRoomRepository.findByRoomId(user.getRoomId());
                    if (gameRoom.getPlayer1() != null){
                        if (
                                Objects.equals(gameRoom.getPlayer1(), userId) ||
                                        Objects.equals(gameRoom.getPlayer1() * -1, userId)
                        ) {
                            gameRoom.setPlayer1(null);
                            System.out.println("플레이어1 슬롯에서 제거됨");
                        }}
                    if (gameRoom.getPlayer2() != null){
                        if (
                                Objects.equals(gameRoom.getPlayer2(), userId) ||
                                        Objects.equals(gameRoom.getPlayer2() * -1, userId)
                        ) {
                            gameRoom.setPlayer2(null);
                            System.out.println("플레이어2 슬롯에서 제거됨");
                        }}
                    if (gameRoom.getPlayer3() != null){
                        if (
                                Objects.equals(gameRoom.getPlayer3(), userId) ||
                                        Objects.equals(gameRoom.getPlayer3() * -1, userId)
                        ) {
                            gameRoom.setPlayer3(null);
                            System.out.println("플레이어3 슬롯에서 제거됨");
                        }}
                    if (gameRoom.getPlayer4() != null){
                        if (
                                Objects.equals(gameRoom.getPlayer4(), userId) ||
                                        Objects.equals(gameRoom.getPlayer4() * -1, userId)
                        ) {
                            gameRoom.setPlayer4(null);
                            System.out.println("플레이어4 슬롯에서 제거됨");
                        }}
                    gameRoomRepository.save(gameRoom);

                    String userListMessage = jsonStringBuilder.gameRoomResponseDtoJsonBuilder(gameRoom);
                    GameMessage message = new GameMessage();
                    message.setRoomId(roomId);
                    message.setContent(userListMessage);
                    message.setType(GameMessage.MessageType.UPDATE);
                    messagingTemplate.convertAndSend("/sub/wroom/" + roomId, message);

                    if (gameRoom.getPlayer1() == null &&
                            gameRoom.getPlayer2() == null &&
                            gameRoom.getPlayer3() == null &&
                            gameRoom.getPlayer4() == null) {
                        gameRoomRepository.delete(gameRoom);
                    }
                    System.out.println(user.getSessionId()+" 삭제처리 전 유저아이디");
                    user.setRoomId(null);
                }
                user.setSessionId(null);
                System.out.println(user.getUsername() + "삭제 한 세션아이디");
                userRepository.save(user);
                System.out.println(userRepository.findBySessionIdIsNotNull().size() + "디스커넥트 후 리스트에 남은 유저 수");
                chatMessage.setSender(user.getId());
                chatMessage.setNickname(user.getNickname());
                chatMessage.setMessage(user.getNickname() + " 님이 접속을 끊었습니다.");
                chatMessage.setConnectedUsers(userService.getConnectedUsers());
                chatMessage.setType(ChatMessage.MessageType.LEAVE);
                sendingOperations.convertAndSend("/sub/public", chatMessage);
            }
        System.out.println("웹소켓 연결해제가 감지됨");
    }

}
