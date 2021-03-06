package com.example.game.Game.card.magic.enchantment;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.ENCHANTMENT;

public class ChannelingMana extends Card {

    public ChannelingMana(Game game) {
        this.game = game;
        this.cardName = "Channeling Mana";
        this.description = "마법사가 마법때문에 건강을 망치는건 딱히 드문 일이 아닙니다. 체력을 마나로 전환합니다";
        this.cardType = ENCHANTMENT;
        this.target = Target.ME;
        this.healthModifier = -3;
        this.manaCost = 3;
    }
}
