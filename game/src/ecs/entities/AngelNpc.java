package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.*;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;
import semanticAnalysis.types.DSLTypeMember;
import starter.Game;
import tools.Point;


public class AngelNpc extends Entity{

    private @DSLTypeMember (name = "idleAi") RadiusWalk idleAI;

    private @DSLTypeMember  (name = "fight_ai") CollideAI fightAI;

    private @DSLTypeMember (name = "transition_ai") RangeTransition transitionAI;

    private final float xSpeed = 0.08f;

    private final float ySpeed = 0.08f;

    private final String pathToIdleRight = "character/npc/idleRight";
    private final String pathToIdleLeft = "character/npc/idleLeft";

    private final String pathToRunRight = "character/npc/runRight";

    private final String pathToRunLeft = "character/npc/runLeft";

    public AngelNpc(){
        super();
        new PositionComponent(this,setupPosition());
        setupAnimationComponent();
        setupVelocityComponent();
        setupAIComponent();
    }
    private void setupAIComponent(){
        idleAI = new RadiusWalk(5f,3);
        transitionAI = new RangeTransition(7f);
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
