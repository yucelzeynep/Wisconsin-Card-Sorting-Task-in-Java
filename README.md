# WCST

This program is an implementation of Wisconsin Card Sorting (WCS) task in Java. 

WCS is a common neuropsychological test. Essentially, it presents several cards (usually 4), three of which match a certain -given- card, in terms of either color, number or shape, and the last one does not match in any way. 

![wcst_gui](https://github.com/yucelzeynep/WCST/blob/master/wcst_gui_v0.png)

In our implementation, a single query card is displayed at the bottom of the user interface, and four other cards, i.e. options, are depicted over it. Any of these cards involve a single sort of shape (either circle, triange, square or star), at a different number (1-4) and with a different color (either red, green, blue, or yellow).

The cards are saved as jpg files under "image/" folder. Each image in the folder has a file name composed of a 3 character long integer string (e.g. 322.jpg, 411.jpg etc). Each digit represents a different property of the cards. Specifically, the first digit codes shape, the second digit codes color, and the last digit codes number. The codes for shape are 1 for circle, 
2 for triangle, 3 for square and 4 for star. The codes for color are 1 for red, 2 for green, 3 for blue and 4 for yellow. The number codes are directly the number of shapes on the cards. 

The game is based on the grounds of matching the query card to one of the options involving either the same shape, color, or number. Here, we call that property to be matched simply as the 'rule' of the game.  In addition, a single set of cards (one query and four options) is termed as 'one round' of the game. 

The rule changes at random steps, i.e. after being in effect for a random number of rounds. However, once it changes, the same rule persists for at least a minimum number rounds. The challenge of the game lies in the fact that there is no clue hinting the rule, or how long it will persist and the user needs to find it by trial and error. 

For determining the 'number of rounds of the current rule', we draw a random integer between 6 and 10. This means that any rule persists for at least 5 rounds, but no more than 10 rounds. 

For choosing the rule, we draw a random integer between 1 and 3, where 'rule=1' means 'match the shape',  'rule=2' means 'match the color', and 'rule=3' means 'match the number'. Simply, the rule defines which digit of the query card needs to be matched with the chosen option card. For instance, in case of the above examle the option cards are named as 114.jpg, 321.jpg, 243.jpg, 422.jpg, respectively; and the query card is 211.jpg. For instance, if 'rule=2' (i.e. 'match the color'), we need to match the cards at second digit, which represents color. In that case, the correct answer is the card that matches the query '211.jpg' at its first digit, which is '114.jpg' (left most).

Upon quitting the game, a user log is saved as txt file in the current directory. There is a single line for each round of the game (or each attempt) with 14 columns. The columns involve the following information

01: Time that the round starts (as UNIX time stamp in msec)

02: Time of answer (as UNIX time stamp in msec)

03: Number of rounds for the current rule

04: Rule in effect (1 for shape, 2 for color, 3 for number)

05: 1 for fresh rule, 0 for continuing rule

06: Code for option card no.1 (integer code representing shape-color-number)

07: Code for option card no.2 (integer code representing shape-color-number)

08: Code for option card no.3 (integer code representing shape-color-number)

09: Code for option card no.4 (integer code representing shape-color-number)

10: Code for query card (integer code representing shape-color-number)

11: 0 for wrong answer, 1 for correct answer

12: Code for answer chosen (integer code representing the combination of shape-color-number)

13: Click location x-coordinate

14: Click location y-coordinate


