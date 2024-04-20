```json
{
  "title": "Control Flow",
  "icon": "minecraft:book",
  "category": "magical-programming:general"
}
```
NULL if( BOOLEAN condition, STRING then, STRING else ). If the condition is true, casts the first spell. If not, casts the second spell. The else is optional.

;;;;;

ANY ternary ( BOOLEAN condition, ANY then, ANY else ). If the condition is true, returns the first argument. If not, returns the second argument.

;;;;;

NULL cast (STRING spell). This function casts the given spell. Don't make a spell cast itself infinitely, that won't work.