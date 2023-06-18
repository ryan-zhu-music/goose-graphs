Requirements:

4+ classes

- Vector.java - a 2D vector object with vector-related methods, e.g. dot()
- Line.java - a line defined by two points that handles collisions and has line-related methods, e.g. shortestDistance()
- Equation.java - responsible for the user input of the equation and evaluating/graphing the function
- Goose.java - an instance for each goose fired (each level has 5); each goose has its own randomness in movement and collisions/bounces

Data structures

- ArrayLists of buttons, geese
- Queue of most recent velocities of each geese, used for physics calculations
- Arrays of levels, points, lines, buttons
- Maps of strings to their respective Math functions, colors to their Color objects

Arrays.sort() and Comparable interface

- Sorts the level select buttons to display in order of difficulty, with completed levels at the end (MenuButton.java, LevelButton.java)

---

Responsibilities:

Delin:

- All UI design (background, buttons, sprites, colors)
- All screen and button functionalities (e.g. switching screens)
- Audio and animations
  Ryan:
- Collision detection and bounce calculations
- Position and velocity calculations
- Inputting and graphing functions

---

Known issues:

- Very rarely, the geese will pass through the line, although efforts have been made to fix it (Goose.move())
- Sometimes, asymptotes or segments with extremely large/small slopes are not drawn accurately, or stop on the screen (e.g. log(x)), but Desmos has trouble handling similar cases too because the detail is too minute
- Pinholes may not be displayed correctly, e.g. sin(x)/x at x = 0
- Sometimes, geese may stop at a somewhat unusual position, e.g. slightly around the bottom of a curve instead of at the very bottom. A Queue of its previous recent velocities is stored and used to help improve the stopping calculation.

Missing features:

- 3-star level system (doesn’t really fit the game, since different levels have different # of bow ties)
- Functions bucket list (evaluating expressions was already hard enough…)
- Obstacles (fixing the error where geese are too close to a line required ensuring the bounce has a Y component going upwards (shouldn’t go down through the line). If obstacles are introduced, hitting the line from below could raise a lot of other bugs)

---

Possible solutions for each level:

Trivial
1. 0.5x (linear)
    A simple line.
2. x^2-3 (quadratic)
    A quadratic lets the geese slide back up to reach the bowties.
3. 1.4*sin(x) (sinusoidal)
    A sinusoid lets the geese go up and down.
Easy
4. 5^x (exponential)
    An exponential allows the geese to fall smoothly to the bowties.
5. ln(x+8)+1 (logarithmic)
    A logarithmic slowly guides the geese to the bowties.
6. -2/x^2 (rational)
    The even rational function with an asymptote funnels the geese down.
Medium
7. -0.7*abs(x) (absolute value)
    An absolute value function reflected across the x-axis will split the geese down the middle.
8. (x-4.5)^4-4 (quartic)
    A quartic function outlines the shape of the bowties best.
9. -9/(x^2+5) (rational)
    A rational with no asymptotes provides a nice dip in the middle.
Hard
10. -0.5x+cos(x) 
    A staircase-like pattern can be formed from the sum of a linear and sinusoidal function.
11. 0.01*(x-7)*(x+7)*x^2
    An even quartic function will split the geese down the middle and provide a path to roll up the sides.
12. x^2/(2x+6)-5
    The function should approach an oblique asymptote towards the right.
Extreme
13: 0.8*(x-5)^(x-5)-5
    Transformations of x^x will form a hook-like function that will launch the geese into the air!
14: -cos(4\*sqrt(x+9))/log(x+11)
    Some form of a dampened sinusoidal function will probably work.
15: -3(2^x-2)/(cos(x)+2)-5
    ???

