package ecs.entities;

import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.*;
import ecs.components.ai.transition.SelfDefendTransition;

/**
 * Monster for the dungeon
 *
 * <p>This Monster is designed to be a little different from the others. It is a bit Stronger and
 * chases you around. That's why in the method "spawnMonster" (Game.java) the possibility of this
 * Monster spawning is very low
 */
public class Chort extends Monster {

    public Chort() {
        super(
                new CollideAI(2f),
                new SelfDefendTransition(),
                new RadiusWalk(3f, 3),
                0.08f,
                0.08f,
                4,
                4,
                "character/monster/chort/idleLeft",
                "character/monster/chort/idleRight",
                "character/monster/chort/runLeft",
                "character/monster/chort/runRight",
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
