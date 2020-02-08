# Windmill Simulation

***Basics***
This is a simulation of the interactive windmill problem, showcased in [3B1B's video](https://www.youtube.com/watch?v=M64HUIJFTZM). Initially, a natural number of total points is required. When the simulation starts, the line starts horizontally, at the middle; it rotates clockwise. If "Yes" was selected as a limitation option, the line will use each point as a rotation point an infinite amount of times. A number appears above each point, representing how many times the line has rotated around that point. 

***Limitations***
The limitations that are applied to achieve this result are two:
* No 3 points are collinear; this means that there is never an arbitrary choice, as to which point the line should rotate around.
* Points aren't too close to each other; without this limitation, the points that are being picked randomly, because of the first limitation, are too close to each other. This leads to potential situations where the program is unable to detect some specific points (because they are too close to others), therefore never passing from them. This, however, is not a real limitation; the actual proof does not take it into account.  

When the "No" option is selected for limitations, the number of points that can be simulated increases dramatically. However, it is very likely that some points will never have the line rotate around them, or that the line will only rotate around them a finite amount of times and reach an eventual state where it never touches them again.

***Controls***
The controls are incredibly simple:
* P: pause the simulation;
* -> (Right arrow): speed up the simulation;
* <- (Left arrow): slow down the simulation;
* ESC: exit the simulation;
