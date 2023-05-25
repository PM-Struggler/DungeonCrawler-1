package ecs.entities;

import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.*;
import ecs.components.ai.transition.RangeTransition;

/**
 * Monster for the dungeon
 *
 * <p>This Monster is designed to be a very Basic Monster, he switches to attackmode if you are
 * really close.
 */
public class Orc extends Monster {
    public Orc() {
        super(
                new CollideAI(2f),
                new RangeTransition(2f),
                new StaticRadiusWalk(3f, 3),
                0.10f,
                0.10f,
                2,
                2,
                "character/monster/orc/idleLeft",
                "character/monster/orc/idleRight",
                "character/monster/orc/runLeft",
                "character/monster/orc/runRight",
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
