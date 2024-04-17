```json
{
  "title": "Functions and Literals",
  "icon": "magical-programming:wand",
  "category": "magical-programming:spellscript_basics",
  "ordinal": 1
}
```
SpellScript is made of two parts: **functions** and **literals**. Functions take in **arguments** and output a result. Literals take no aruments. Some examples of literals are **numbers** and **strings**. Strings are a piece of text surrounded by commas.

;;;;;

Functions are in the syntax {red}function(argument1, argument2){}. Different functions have different amounts of arguments.
An example function would be {red}print("Hello, World!"){}. This prints "Hello, World!" in the chat for you.
To do multiple functions, you seperate them with a comma, similar to how you seperate arguments. For example: {red}print("Hello,"),print("World!"){} would print Hello, and World! in seperate lines.

;;;;;

Arguments can also have their name specified, which can make spells more readable or be used to change the order of the arguments. However, if you do this, you must specify the name of all arguments in that function. For example:
{red}print(divide(dividend:12, divisor:3)){} would print 4, and would do the same even if the position of the arguments was swapped.

;;;;;

Finally, note that whitespace (spaces and new lines) is ignored (except in strings). This means you can format your spell however you want.
Furthermore, you can add comments with a hastag, which will cause everything up to the next new line to be ignored, allowing you to add notes to make your code more readable.
