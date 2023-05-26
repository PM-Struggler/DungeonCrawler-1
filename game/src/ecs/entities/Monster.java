package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import graphic.Animation;
import starter.Game;
import tools.Point;

/**
 * Abstract upper class for the Monsters.
 *
 * <p>This class extends of the class Entity and is used as an upper class for the Monsters.
 */
abstract class Monster extends Entity {

    private final IFightAI fightAI;
    private final ITransition transitionAI;
    private final IIdleAI idleAI;
    protected float xSpeed;
    protected float ySpeed;
    protected int maximalHealthpoints;
    protected int currentHealthpoints;
    protected String pathToIdleRight;
    protected String pathToIdleLeft;
    protected String pathToRunRight;
    protected String pathToRunLeft;
    protected String pathToisHit;
    protected String pathToDeath;
    private final IOnDeathFunction deathFunction;

    /**
     * @param fightAI for combat behavior of an AI controlled entity
     * @param transitionAI for the idle behavior of an AI controlled entity
     * @param idleAI when an ai switches between idle and fight
     * @param ySpeed Movementspeed for the Y-Axis
     * @param maximalHealthpoints Max Healthpoints
     * @param currentHealthpoints current Healthpoints
     * @param pathToIdleLeft path of the files for the animation
     * @param pathToIdleRight path of the files for the animation
     * @param pathToRunLeft path of the files for the animation
     * @param pathToRunRight path of the files for the animation
     */
    public Monster(
            IFightAI fightAI,
            ITransition transitionAI,
            IIdleAI idleAI,
            float xSpeed,
            float ySpeed,
            int maximalHealthpoints,
            int currentHealthpoints,
            String pathToIdleLeft,
            String pathToIdleRight,
            String pathToRunLeft,
            String pathToRunRight,
            String pathToisHit,
            String pathToDeath,
            IOnDeathFunction deathFunction) {
        this.fightAI = fightAI;
        this.transitionAI = transitionAI;
        this.idleAI = idleAI;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.maximalHealthpoints = maximalHealthpoints;
        this.currentHealthpoints = currentHealthpoints;
        this.pathToIdleLeft = pathToIdleLeft;
        this.pathToIdleRight = pathToIdleRight;
        this.pathToRunLeft = pathToRunLeft;
        this.pathToRunRight = pathToRunRight;
        this.pathToisHit = pathToisHit;
        this.pathToDeath = pathToDeath;
        this.deathFunction = deathFunction;
    }

    /** Method for the AI of the Monster */
    protected void setupAIComponent() {
        AIComponent ai = new AIComponent(this);
        ai.setFightAI(fightAI);
        ai.setTransitionAI(transitionAI);
        ai.setIdleAI(idleAI);
    }

    /** Method so that the Monster doesn't spawn on the Hero */
    protected void setupPositionComponent() {
        Point position;
        if (Game.currentLevel != null) {
            position = Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint();
            // so that the trap doesn't spawn on the same Tile as the Hero
            if (position == Game.currentLevel.getStartTile().getCoordinateAsPoint()) {
                setupPositionComponent();
            }
            new PositionComponent(this, position);
        }
    }

    /** Method for the Velocity of the Monster */
    protected void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(this.pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToRunLeft);
        new VelocityComponent(this, this.xSpeed, this.ySpeed, moveLeft, moveRight);
    }

    /** Method for the Health of the Monster */
    protected void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        Animation isHit = AnimationBuilder.buildAnimation(this.pathToisHit);
        Animation death = AnimationBuilder.buildAnimation(this.pathToDeath);
        hc.setCurrentHealthpoints(this.currentHealthpoints);
        hc.setMaximalHealthpoints(this.maximalHealthpoints);
        hc.setGetHitAnimation(isHit);
        hc.setDeathAnimation(death);
        hc.setOnDeath(deathFunction);
    }

    /** Method for the Animation of the Monster */
    protected void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(this.pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(this.pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /** Method for the Hitbox of the Monster. */
    protected void setupHitboxComponent() {
        // Printline to check if a collision happened
        new HitboxComponent(
                this,
                (you, other, direction) -> System.out.println(currentHealthpoints),
                (you, other, direction) -> System.out.println(currentHealthpoints));
    }
}
