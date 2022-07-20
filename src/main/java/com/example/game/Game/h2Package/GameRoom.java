package com.example.game.Game.h2Package;

import com.example.game.model.Timestamped;
import com.example.game.model.user.User;
import com.example.game.security.UserDetailsImpl;
import com.example.game.websocket.GameRoomRequestDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gameroom")
public class GameRoom extends Timestamped implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String roomId;

    @Column
    private String roomName;

    @Builder
    public GameRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public GameRoom(GameRoomRequestDto requestDto) {
        this.roomId = UUID.randomUUID().toString();
        this.roomName = requestDto.getRoomName();
    }
}