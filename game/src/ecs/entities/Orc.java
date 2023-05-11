package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.transition.SelfDefendTransition;
import graphic.Animation;
import semanticAnalysis.types.DSLTypeMember;
import starter.Game;
import tools.Point;


public class Orc extends Entity{

    private @DSLTypeMember (name = "idleAi") RadiusWalk idleAI;

    private @DSLTypeMember  (name = "fight_ai") CollideAI fightAI;

    private @DSLTypeMember (name = "transition_ai") SelfDefendTransition transitionAI;

    private final float xSpeed = 0.31f;

    private final float ySpeed = 0.31f;

    private int maximalHealthpoints = 2;

    private int currentHealthpoints = 2;

    private final String onDeath = "on_death_function";

    private final String getHitAnimation = "get_hit_animation";

    private final String dieAnimation = "die_animation";

    private final String pathToIdleRight = "character/monster/orc/idleRight";
    private final String pathToIdleLeft = "character/monster/orc/idleLeft";

    private final String pathToRunRight = "character/monster/orc/runRight";

    private final String pathToRunLeft = "character/monster/orc/runLeft";

    public Orc(){
        super();
        new PositionComponent(this,setupPosition());
        setupHitboxComponent();
        setupAnimationComponent();
        setupVelocityComponent();
        setupAIComponent();
        setupHealthComponent();
    }
    private void setupHealthComponent() {
        this.maximalHealthpoints = maximalHealthpoints;
        this.currentHealthpoints = currentHealthpoints;
        //noch keine Animationen deswegen idleRight und so
        Animation idleRight = AnimationBuilder.buildAnimation(onDeath);
        Animation idleLeft = AnimationBuilder.buildAnimation(getHitAnimation);
        Animation moveRight = AnimationBuilder.buildAnimation(dieAnimation);
        new HealthComponent(this);
    }
    private void setupAIComponent(){
        idleAI = new RadiusWalk(3,3);
        transitionAI = new SelfDefendTransition();
        fightAI = new CollideAI(2f);
        new AIComponent(this,fightAI,idleAI,transitionAI);
    }

    public Point setupPosition() {
        Point position = null;
        if (Game.currentLevel != null) {
            position = Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint();
            //so that the trap doesn't spawn on the same Tile as the Hero
            if (position == Game.currentLevel.getStartTile().getCoordinateAsPoint()){
                setupPosition();
            }
        }
        return position;
    }

    private void setupHitboxComponent() {
        // Nutzung der Components
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println(),
            (you, other, direction) -> System.out.println("Leave"));
    }
    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }
    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this,xSpeed, ySpeed,moveLeft,moveRight);
    }
}
