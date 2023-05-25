package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

/**
 * this abstract class is for the Melee attack. The attack goes into the direction, where the Mouse
 * is.
 */
public abstract class DamageMeleeSkill implements ISkillFunction {
    private final String pathToTexturesOfUpperAttack;
    private final String pathToTexturesOfLowerAttack;
    private final String pathToTexturesOfLeftAttack;
    private final String pathToTexturesOfRightAttack;
    private final Damage attackDamage;
    private final Point attackHitboxSize;
    private static int animationFrames;
    private static boolean active;
    private static Entity attack;

    /**
     * @param pathToTexturesOfUpperAttack Animation
     * @param pathToTexturesOfLowerAttack Animation
     * @param pathToTexturesOfLeftAttack Animation
     * @param pathToTexturesOfRightAttack Animation
     * @param attackDamage damageamount,damagetype,cause
     * @param attackHitboxSize size of the hitbox
     */
    public DamageMeleeSkill(
            String pathToTexturesOfUpperAttack,
            String pathToTexturesOfLowerAttack,
            String pathToTexturesOfLeftAttack,
            String pathToTexturesOfRightAttack,
            Damage attackDamage,
            Point attackHitboxSize) {
        this.pathToTexturesOfUpperAttack = pathToTexturesOfUpperAttack;
        this.pathToTexturesOfLowerAttack = pathToTexturesOfLowerAttack;
        this.pathToTexturesOfLeftAttack = pathToTexturesOfLeftAttack;
        this.pathToTexturesOfRightAttack = pathToTexturesOfRightAttack;
        this.attackDamage = attackDamage;
        this.attackHitboxSize = attackHitboxSize;
    }

    @Override
    public void execute(Entity entity) {
        active = true;
        attack = new Entity();
        float y;
        float x;

        PositionComponent pc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        AnimationComponent ani =
                (AnimationComponent)
                        entity.getComponent(AnimationComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("AnimationComponent"));

        y = pc.getPosition().y - SkillTools.getCursorPositionAsPoint().y;
        x = pc.getPosition().x - SkillTools.getCursorPositionAsPoint().x;
        boolean xory = Math.abs(x) > Math.abs(y);
        if (y < 0) {
            if (xory) {
                if (x < 0) {
                    right(entity, pc, ani);
                } else {
                    left(entity, pc, ani);
                }
            } else {
                up(entity, pc);
            }
        } else {
            if (xory) {
                if (x < 0) {
                    right(entity, pc, ani);
                } else {
                    left(entity, pc, ani);
                }
            } else {
                down(entity, pc);
            }
        }
    }

    /**
     * To execute the attack, when the mouse is up.
     *
     * @param entity Entity
     * @param pc PositionComponent of the Entity
     */
    private void up(Entity entity, PositionComponent pc) {
        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfUpperAttack);
        new PositionComponent(attack, pc.getPosition().x, pc.getPosition().y + 1.1f);
        new AnimationComponent(attack, animation);
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(hc -> ((HealthComponent) hc).receiveHit(attackDamage));
                        for (Tile tile : Game.currentLevel.getFloorTiles()) {
                            if (Game.currentLevel.getTileAt(
                                            new Point(pc.getPosition().x, pc.getPosition().y + 2f)
                                                    .toCoordinate())
                                    == tile) {
                                b.getComponent(PositionComponent.class)
                                        .ifPresent(
                                                pos ->
                                                        ((PositionComponent) pos)
                                                                .setPosition(
                                                                        new Point(
                                                                                pc.getPosition().x,
                                                                                pc.getPosition().y
                                                                                        + 2f)));
                            }
                        }
                    }
                };
        new HitboxComponent(attack, new Point(0.25f, -0.5f), attackHitboxSize, collide, null);
    }

    /**
     * To execute the attack, when the mouse is on the right side.
     *
     * @param entity Enitity
     * @param pc PositionComponent for the Entity
     * @param ani AnimationComponent for the Entity
     */
    private void right(Entity entity, PositionComponent pc, AnimationComponent ani) {
        ani.setCurrentAnimation(ani.getIdleRight());
        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfRightAttack);
        new AnimationComponent(attack, animation);
        new PositionComponent(attack, pc.getPosition().x + 0.9f, pc.getPosition().y - 0.09f);
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(hc -> ((HealthComponent) hc).receiveHit(attackDamage));
                        for (Tile tile : Game.currentLevel.getFloorTiles()) {
                            if (Game.currentLevel.getTileAt(
                                            new Point(pc.getPosition().x + 2f, pc.getPosition().y)
                                                    .toCoordinate())
                                    == tile) {
                                b.getComponent(PositionComponent.class)
                                        .ifPresent(
                                                pos ->
                                                        ((PositionComponent) pos)
                                                                .setPosition(
                                                                        new Point(
                                                                                pc.getPosition().x
                                                                                        + 2f,
                                                                                pc.getPosition()
                                                                                        .y)));
                            }
                        }
                    }
                };
        new HitboxComponent(attack, new Point(-0.5f, 0.25f), attackHitboxSize, collide, null);
    }

    /**
     * To execute the attack, when the mouse is down.
     *
     * @param entity Entity
     * @param pc PositionComponent for the Entity
     */
    private void down(Entity entity, PositionComponent pc) {
        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfLowerAttack);
        new AnimationComponent(attack, animation);
        new PositionComponent(attack, pc.getPosition().x, pc.getPosition().y - 1.1f);
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(hc -> ((HealthComponent) hc).receiveHit(attackDamage));
                        for (Tile tile : Game.currentLevel.getFloorTiles()) {
                            if (Game.currentLevel.getTileAt(
                                            new Point(pc.getPosition().x, pc.getPosition().y - 2f)
                                                    .toCoordinate())
                                    == tile) {
                                b.getComponent(PositionComponent.class)
                                        .ifPresent(
                                                pos ->
                                                        ((PositionComponent) pos)
                                                                .setPosition(
                                                                        new Point(
                                                                                pc.getPosition().x,
                                                                                pc.getPosition().y
                                                                                        - 2f)));
                            }
                        }
                    }
                };
        new HitboxComponent(attack, new Point(0.25f, +0.5f), attackHitboxSize, collide, null);
    }

    /**
     * To execute the attack, when the mouse is on the left side.
     *
     * @param entity Entity
     * @param pc for the PositionComponent of the Entity
     * @param ani for the AnimationComponent of the Entity
     */
    private void left(Entity entity, PositionComponent pc, AnimationComponent ani) {
        ani.setCurrentAnimation(ani.getIdleLeft());
        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfLeftAttack);
        new AnimationComponent(attack, animation);
        new PositionComponent(attack, pc.getPosition().x - 0.9f, pc.getPosition().y - 0.09f);
        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(hc -> ((HealthComponent) hc).receiveHit(attackDamage));
                        for (Tile tile : Game.currentLevel.getFloorTiles()) {
                            if (Game.currentLevel.getTileAt(
                                            new Point(pc.getPosition().x - 2f, pc.getPosition().y)
                                                    .toCoordinate())
                                    == tile) {
                                b.getComponent(PositionComponent.class)
                                        .ifPresent(
                                                pos ->
                                                        ((PositionComponent) pos)
                                                                .setPosition(
                                                                        new Point(
                                                                                pc.getPosition().x
                                                                                        - 2f,
                                                                                pc.getPosition()
                                                                                        .y)));
                            }
                        }
                    }
                };
        new HitboxComponent(attack, new Point(+0.5f, 0.25f), attackHitboxSize, collide, null);
    }

    /** An update method, so that the sword disappears from the level after the animation. */
    public static void update() {
        if (active) {
            if (animationFrames >= 14) {
                for (Entity a : Game.getEntities()) {
                    if (a == attack) {
                        Game.removeEntity(attack);
                    }
                    animationFrames = 0;
                    active = false;
                }
            }
            animationFrames++;
        }
    }
}
