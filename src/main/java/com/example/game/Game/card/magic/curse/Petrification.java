package com.example.game.Game.card.magic.curse;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class Petrification extends Card {
    public Petrification(Game game) {
        this.game = game;
        this.cardName = "Petrification";
        this.description = "마법사와 시비를 붙는걸 보면 돌머리가 의심됩니다. 돌은 돌로. 적을 돌로 만듭니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -3;
        this.petrifyDuration = 2;
        this.poisonDuration = 0;
        this.stunDuration = 0;
        this.sleepDuration = 0;
        this.muteDuration = 0;
        this.damageModifierDuration = 0;
        this.manaCostModifierDuration = 0;
    }
}
