package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.VelocityComponent;
import graphic.Animation;

/**
 * Abstract upper class for the Monsters.
 *
 * <p>This class extends of the class Entity and is used as an upper class for the Monsters.
 */
abstract class Monster extends Entity {

    protected float xSpeed;
    protected float ySpeed;
    protected int maximalHealthpoints;
    protected int currentHealthpoints;
    protected String pathToIdleRight;
    protected String pathToIdleLeft;
    protected String pathToRunRight;
    protected String pathToRunLeft;

    /**
     * @param xSpeed Movementspeed for the X-Axis
     * @param ySpeed Movementspeed for the Y-Axis
     * @param maximalHealthpoints Max Healthpoints
     * @param currentHealthpoints current Healthpoints
     * @param pathToIdleLeft path of the files for the animation
     * @param pathToIdleRight path of the files for the animation
     * @param pathToRunLeft path of the files for the animation
     * @param pathToRunRight path of the files for the animation
     */
    public Monster(
            float xSpeed,
            float ySpeed,
            int maximalHealthpoints,
            int currentHealthpoints,
            String pathToIdleLeft,
            String pathToIdleRight,
            String pathToRunLeft,
            String pathToRunRight) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.maximalHealthpoints = maximalHealthpoints;
        this.currentHealthpoints = currentHealthpoints;
        this.pathToIdleLeft = pathToIdleLeft;
        this.pathToIdleRight = pathToIdleRight;
        this.pathToRunLeft = pathToRunLeft;
        this.pathToRunRight = pathToRunRight;
    }

    /** Abstract method, so that every Monster can have his own AI */
    protected abstract void setupAIComponent();

    /** Method for the Velocity of the Monster */
    protected void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(this.pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(this.pathToRunLeft);
        new VelocityComponent(this, this.xSpeed, this.ySpeed, moveLeft, moveRight);
    }

    /** Method for the Health of the Monster */
    protected void setupHealthComponent() {
        HealthComponent hc = new HealthComponent(this);
        hc.setCurrentHealthpoints(this.currentHealthpoints);
        hc.setMaximalHealthpoints(this.maximalHealthpoints);
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
                (you, other, direction) -> System.out.println("Enter"),
                (you, other, direction) -> System.out.println("Leave"));
    }
}
