package ecs.entities;

import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.*;
import ecs.components.ai.transition.RangeTransition;

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
                0.08f,
                0.08f,
                4,
                4,
                "character/monster/chort/idleLeft",
                "character/monster/chort/idleRight",
                "character/monster/chort/runLeft",
                "character/monster/chort/runRight");
        new PositionComponent(this);
        setupHitboxComponent();
        setupAnimationComponent();
        setupVelocityComponent();
        setupAIComponent();
        setupHealthComponent();
        setupAIComponent();
    }

    @Override
    protected void setupAIComponent() {
        new AIComponent(this, new CollideAI(2f), new RadiusWalk(5f, 3), new RangeTransition(7f));
    }
}
