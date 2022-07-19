package com.example.game.Game.card.magic.curse;

import com.example.game.Game.h2Package.Game;
import com.example.game.Game.h2Package.Card;
import com.example.game.Game.card.Target;

import static com.example.game.Game.card.CardType.CURSE;

public class WeaknessExposure extends Card {

    public WeaknessExposure(Game game) {
        this.game = game;
        this.cardName = "Weakness Exposure";
        this.description = "열손가락 깨물어 안아픈 손가락 없지만 특히 더 아픈 손가락은 있습니다. 대상은 약점이 드러나 더 큰 데미지를 입습니다.";
        this.cardType = CURSE;
        this.target = Target.SELECT;
        this.manaCost = -2;
        this.weakDuration = 2;
    }
}
