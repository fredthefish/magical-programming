```json
{
  "title": "Actions",
  "icon": "minecraft:command_block",
  "category": "magical-programming:spellscript_basics",
  "ordinal": 2
}
```
Before we cast spells that actually impact the world, we must understand **vectors**. Vectors are a combination of three numbers, that can represent, for example, the position of something, or the direction something is facing.
Vectors have a direction and a magnitude. A vector can be created with the vector(x, y, z) function.

;;;;;

Our first spell will use the function addforce(), which takes an entity and a vector. In order to get an entity, we will use the literal {light_purple}self{}, which gets the caster of the spell (you).
Combining these, a simple starter spell is {red}addforce(self, vector(0,2,0)){}, which adds a force with a positive y onto you. Effectively, it launches you into the air.

;;;;;

For a more complicated and useful spell, we will use lookdirection(), which gets the direction an entity is looking as a vector.
With that, we can do {red}addforce(self, multiply(lookdirection(self), 3)){}. This takes the direction you are looking at (magnitude of 1) and multiplies it by 3 (changing the magnitude to 3), and launches you. This has the effect of launching you in the direction you are looking.

;;;;;

Here's another powerful spell:
{red}addforce(summonarrow(eyeposition(self)), multiply(lookdirection(self), 5))){}.
summon() spawns an arrow (owned by you) at the position of your eyes, and returns the arrow as an entity. Then addforce() adds a force to that arrow, launching it in the direction you are looking.
This is effectively a railgun.