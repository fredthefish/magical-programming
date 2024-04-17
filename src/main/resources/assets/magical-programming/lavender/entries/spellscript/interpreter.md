```json
{
  "title": "Interpreter",
  "icon": "minecraft:redstone",
  "category": "magical-programming:spellscript_basics",
  "ordinal": 99
}
```
If you want to know the technicals of how SpellScript works, you can read this. But it's entirely optional.

;;;;;

First, SpellScript divides the spell into tokens that can be parsed. It seperates everything by commas and parenthesis, and marks all parenthesis.
- It marks anything before an open parenthesis as a function, and *anything* between quotation marks as a string. It ignores comments.
- It ignores all whitespace outside of strings. This means you can even add spaces into a function name or literal.

;;;;;

- Functions or literals can have an argument name (something and a colon preceding it). Multiple colons will result in only the last colon being looked at, with previous colons and things before them ignored.
- If you use named arguments, then non-named arguments will be ignored (you must use only named arguments).

;;;;;

After that, SpellScript parses the tokens, so they can be used by the interpreter. It recursively finds tokens and adds them as arguments to functions.
Too few closing parenthesis will cause the spell to not work, too many will not cause anything to happen. Any literals outside of functions or excess arguments are ignored.

;;;;;

After that, SpellScript will interpret the code. If a function has missing arguments, or invalid arguments (incorrect type), the function will return null. Invalid functions will also return null.