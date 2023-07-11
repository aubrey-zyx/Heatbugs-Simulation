# Heatbugs-Simulation  
_Dalian University of Technology_ 2020 fall _Simulation of Management System_ course project  
## Introduction of the Heatbugs Model  
### What is it and how it works
Heatbugs is an abstract model of the behavior of biologically-inspired agents that attempt to maintain an optimum temperature around themselves. It demonstrates how simple rules defining the behavior of agents can produce several different kinds of emergent behavior.  
The bugs move around on a grid of square "patches". A bug may not move to a patch that already has another bug on it.  
Each bug radiates a small amount of heat. Heat gradually diffuses through the world; some heat is lost to cooling.  
Each bug has an "ideal" temperature it wants to be. The bigger the difference between the temperature of the patch where the bug is and the bug's ideal temperature, the more "unhappy" the bug is. When a bug is unhappy, it moves. If it is too hot, it moves to the coolest adjacent empty patch. Conversely, if a bug is too cold, it moves to the warmest adjacent empty patch. (Note that these bugs aren't smart enough to always move to the best available patch.)  
### Rules set in our simulation experiment
The temperature through the whole world is 0 initially. After the bugs are randomly generated, their ideal temperature and released heat will also be randomly generated in a preset range.  
The bugs will release heat towards four adjacent patches (up, down, left, and right.) And they will move to one of these four patches whose temperature is the closest to their ideal temperature.  
Every patch loses a certain amount of heat due to evaporation.  
## How to Use It
After choosing the number of bugs to create, and setting the model variables, press the "start" button to set the heatbugs into motion.  
_numBugs_: The number of bugs that will inhabit the model.  
_minIdealTemp/maxIdealTemp_: The minimum and maximum ideal temperatures for heatbugs. Each bug is given an _idealTemp_ between the min and max ideal temperature.  
_minOutputHeat/maxOutputHeat_: The minimum and maximum heat that heatbugs generate each cycle. Each bug is given a _outputHeat_ value between the min and max output heat.   
_time_: The number of rounds of the experiment.  
After pressing "start", the image at the bottom left will display the real-time motion of heatbugs, and the graph at the bottom right will show how _unhappiness_ change with time.  
