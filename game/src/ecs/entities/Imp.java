package ecs.entities;

import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.*;
import ecs.components.ai.transition.SelfDefendTransition;

/**
 * Monster for the dungeon
 *
 * <p>This Monster is designed to be very annoying if you don't pay attention. If you dont kill it
 * on the first try, it will run around really fast!
 */
public class Imp extends Monster {

    public Imp() {
        super(
                new CollideAI(2f),
                new SelfDefendTransition(),
                new PanicWalk(3f, 1),
                0.30f,
                0.30f,
                1,
                1,
                "character/monster/imp/idleLeft",
                "character/monster/imp/idleRight",
                "character/monster/imp/runLeft",
                "character/monster/imp/runRight",
                "knight/idleRight",
                "knight/idleRight",
                entity -> System.out.println("Tot"));
        setupAIComponent();
        setupHitboxComponent();
        setupAnimationComponent();
        setupVelocityComponent();
        setupHealthComponent();
        setupPositionComponent();
    }
}
