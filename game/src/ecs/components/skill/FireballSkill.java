package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

/**
 * This fireball class is for the fireball attack. If pierce is true, the projectile will pierce
 * through the entities.
 */
public class FireballSkill extends DamageProjectileSkill {
    public FireballSkill(ITargetSelection targetSelection) {
        super(
                "skills/fireball/fireBall_Down/",
                0.5f,
                new Damage(1, DamageType.FIRE, null),
                new Point(1, 1),
                targetSelection,
                100f,
                true);
    }
}
