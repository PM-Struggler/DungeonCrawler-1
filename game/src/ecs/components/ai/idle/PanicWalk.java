package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Constants;

public class PanicWalk implements IIdleAI {

    private boolean touch = false;
    private final float radius;
    private GraphPath<Tile> path;
    private final int breakTime;
    private int currentBreak = 0;

    /**
     * Starts his walk only after it has been touched. After being touched by an other Entity, the
     * Entity finds a point in the radius and then moves there. When the point has been reached, a
     * new point in the radius is searched for from there.
     *
     * @param radius Radius in which a target point is to be searched for
     * @param breakTimeInSeconds how long to wait (in seconds) before searching a new goal
     */
    public PanicWalk(float radius, int breakTimeInSeconds) {
        this.radius = radius;
        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
    }

    /**
     * Method to check if the Hero has been touched
     *
     * @param entity Entity
     */
    public void touched(Entity entity) {
        if (AITools.playerInRange(entity, 1)) {
            touch = true;
        }
    }

    @Override
    public void idle(Entity entity) {
        touched(entity);
        if (touch) {
            if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
                if (currentBreak >= breakTime) {
                    currentBreak = 0;
                    path = AITools.calculatePathToRandomTileInRange(entity, radius);
                    idle(entity);
                }

                currentBreak++;

            } else AITools.move(entity, path);
        }
    }
}
