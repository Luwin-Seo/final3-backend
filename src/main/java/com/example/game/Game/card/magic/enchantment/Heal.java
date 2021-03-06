package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class Heal extends Card {
    public Heal(Game game) {
        this.game = game;
        this.cardName = "Heal";
        this.description = "무시무시한 결투장 위에서도 가끔은 박애주의를 기대할 수 있을지도요. 대상을 치료합니다.";
        this.cardType = ENCHANTMENT;
        this.target = Target.SELECT;
        this.healthModifier = 3;
        this.manaCost = -4;
        this.poisonDuration = 0;
    }
}
