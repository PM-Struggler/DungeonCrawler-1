package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class MeleeSkill extends DamageMeleeSkill {
    public MeleeSkill() {
        super(
                "character/knight/attackUp",
                "character/knight/attackDown",
                "character/knight/attackLeft",
                "character/knight/attackRight",
                new Damage(1, DamageType.PHYSICAL, null),
                new Point(1, 1));
    }
}
