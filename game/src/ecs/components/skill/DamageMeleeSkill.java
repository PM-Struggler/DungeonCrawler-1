package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

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
                    System.out.println("Rechts1");
                    ani.setCurrentAnimation(ani.getIdleRight());
                    Animation animation =
                            AnimationBuilder.buildAnimation(pathToTexturesOfRightAttack);
                    new AnimationComponent(attack, animation);
                    new PositionComponent(
                            attack, pc.getPosition().x + 0.9f, pc.getPosition().y - 0.09f);
                    ICollide collide =
                        (a, b, from) -> {
                            if (b != entity) {
                                b.getComponent(HealthComponent.class)
                                    .ifPresent(
                                        hc ->
                                            ((HealthComponent) hc).receiveHit(attackDamage));
                                        ;
                            }
                        };
                    new HitboxComponent(
                        attack, new Point(0.25f, 0.25f), attackHitboxSize, collide, null);
                } else {
                    System.out.println("links");
                    ani.setCurrentAnimation(ani.getIdleLeft());
                    Animation animation =
                            AnimationBuilder.buildAnimation(pathToTexturesOfLeftAttack);
                    new AnimationComponent(attack, animation);
                    new PositionComponent(
                            attack, pc.getPosition().x - 0.9f, pc.getPosition().y - 0.09f);
                    ICollide collide =
                        (a, b, from) -> {
                            if (b != entity) {
                                b.getComponent(HealthComponent.class)
                                    .ifPresent(
                                        hc ->
                                            ((HealthComponent) hc).receiveHit(attackDamage));
                            }
                        };
                    new HitboxComponent(
                        attack, new Point(0.25f, 0.25f), attackHitboxSize, collide, null);
                }
            } else {
                System.out.println("Oben");
                Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfUpperAttack);
                new PositionComponent(attack, pc.getPosition().x, pc.getPosition().y + 1.1f);
                new AnimationComponent(attack, animation);
                ICollide collide =
                    (a, b, from) -> {
                        if (b != entity) {
                            b.getComponent(HealthComponent.class)
                                .ifPresent(
                                    hc ->
                                        ((HealthComponent) hc).receiveHit(attackDamage));
                        }
                    };
                new HitboxComponent(
                    attack, new Point(0.25f, 0.25f), attackHitboxSize, collide, null);
            }
        } else {
            if (xory) {
                if (x < 0) {
                    System.out.println("Rechts2");
                    ani.setCurrentAnimation(ani.getIdleRight());
                    Animation animation =
                            AnimationBuilder.buildAnimation(pathToTexturesOfRightAttack);
                    new AnimationComponent(attack, animation);
                    new PositionComponent(
                            attack, pc.getPosition().x + 0.9f, pc.getPosition().y - 0.09f);
                    ICollide collide =
                        (a, b, from) -> {
                            if (b != entity) {
                                b.getComponent(HealthComponent.class)
                                    .ifPresent(
                                        hc ->
                                            ((HealthComponent) hc).receiveHit(attackDamage));
                            }
                        };
                    new HitboxComponent(
                        attack, new Point(0.25f, 0.25f), attackHitboxSize, collide, null);
                } else {
                    System.out.println("links");
                    ani.setCurrentAnimation(ani.getIdleLeft());
                    Animation animation =
                            AnimationBuilder.buildAnimation(pathToTexturesOfLeftAttack);
                    new AnimationComponent(attack, animation);
                    new PositionComponent(
                            attack, pc.getPosition().x - 0.9f, pc.getPosition().y - 0.09f);
                    ICollide collide =
                        (a, b, from) -> {
                            if (b != entity) {
                                b.getComponent(HealthComponent.class)
                                    .ifPresent(
                                        hc ->
                                            ((HealthComponent) hc).receiveHit(attackDamage));
                            }
                        };
                    new HitboxComponent(
                        attack, new Point(0.25f, 0.25f), attackHitboxSize, collide, null);
                }
            } else {
                System.out.println("Unten");
                Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfLowerAttack);
                new AnimationComponent(attack, animation);
                new PositionComponent(attack, pc.getPosition().x, pc.getPosition().y - 1.1f);
                ICollide collide =
                    (a, b, from) -> {
                        if (b != entity) {
                            b.getComponent(HealthComponent.class)
                                .ifPresent(
                                    hc ->
                                        ((HealthComponent) hc).receiveHit(attackDamage));
                        }
                    };
                new HitboxComponent(
                    attack, new Point(0.25f, 0.25f), attackHitboxSize, collide, null);
            }
        }
    }

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
