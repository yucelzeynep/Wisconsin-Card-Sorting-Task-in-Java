# WCST
Wisonsin Card Sorting Task

This program implements Wisconsin Card Sorting (WCS) task in Java. 

WCSk is a common neuropsychological test. Essentially, it presents  several cards (usually 4), one of which matches another given card, in terms of either color, number or shape. 

A single card query cards displayed at the bottom of the GUI, and four other cards, options are depicted over it. Each card involves a single shape (one of the four shapes of circle, triange, square or star), with a different number (1-4) and a different color (red, green, blue, yellow).

The game is based on the grounds of matching the query card to one of the options based on a 'rule', which can be 'rule=color' (i.e. find the card with matching color), or 'rule=number' (i.e. find the card with matching number of shapes) or 'rule=shape' (i.e. find the cadrd with a matching shape.)

Among the options, three match the query in respect to one of the possible three rules, whereas one card does not match for any rule. 

Here, we call the property to be matched  simply  as the 'rule' of the game.  In addition,  a single set of cards (one query and 4 options)  is termed as 'one round' of the game. The rule changes is at random steps, i.e. after a random number of rounds. However, once it changes, the same rule persists for at least  a minimum number rounds. The challenge of the game lies in the fact that there is no clue hinting the rule, and the user needs to find it by trial and error. 

For determining the 'number of rounds of the current rule', we draw a random integer between 6 and 10. This means that any rule persists  at least 5 rounds and no more than 10 rounds. For choosing the rule, we draw a random integer between 1 and 3, where 'rule=1' means 'match color',  'rule=2' means 'match number', and 'rule=3' means 'match shape'.

