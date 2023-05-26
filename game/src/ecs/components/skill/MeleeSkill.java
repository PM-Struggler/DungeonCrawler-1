package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

/** This meleeskill class is for the basic-attack+ */
public class MeleeSkill extends DamageMeleeSkill {
    public MeleeSkill() {
        super(
                "character/knight/attackUp",
                "character/knight/attackDown",
                "character/knight/attackLeft",
                "character/knight/attackRight",
                new Damage(1, DamageType.PHYSICAL, null),
                new Point(2f, 2f));
    }
}
