```json
{
  "title": "Vectors",
  "icon": "minecraft:book",
  "category": "magical-programming:math"
}
```
VECTOR vector(x:NUMBER, y:NUMBER, z:NUMBER). The vector function takes three numbers and converts them into a vector.

;;;;;

NUMBER getx(vector:VECTOR). Gets the x part of the vector.

;;;;;

NUMBER gety(vector:VECTOR). Gets the y part of the vector.

;;;;;

NUMBER getz(vector:VECTOR). Gets the z part of the vector.

;;;;;

VECTOR direction(vector:VECTOR). Normalizes the vector (sets its magnitude to 1), the equivalent of getting its direction.

;;;;;

NUMBER magnitude(vector:VECTOR). Gets the magnitude/length of the vector.

;;;;;

VECTOR setx(vector:VECTOR, number:NUMBER). Sets the x of the vector to the number.

;;;;;

VECTOR sety(vector:VECTOR, number:NUMBER). Sets the y of the vector to the number.

;;;;;

VECTOR setz(vector:VECTOR, number:NUMBER). Sets the z of the vector to the number.