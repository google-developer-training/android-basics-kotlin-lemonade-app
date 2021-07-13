Lemonade - Starter Code
==================================

Starter code for the first independent project for Android Basics in Kotlin

Introduction
------------

This is the starter code for the Lemonade app project. This project is an opportunity for you to
demonstrate the concepts you learned in Unit 1 of Android Basics in Kotlin.

Pre-requisites
--------------

- Complete Unit 1 of Android Basics in Kotlin

Getting Started
---------------

1. Download the starter code
2. Open the project in Android Studio
3. Complete the project in accordance with the app requirements

Tasks
-----

The logic used to implement the Lemonade app is referred to as a “state machine” or, more 
specifically a "finite state machine." Formally speaking, a finite state machine is a computational 
model often used to simulate sequential logic. If that definition seems slightly confusing, that's 
because it is! Not to worry, a state machine is an abstract concept, and you aren't expected to know
it at this point. In less abstract terms, a finite state machine has a finite number of "states," it 
can only be in one "state" at a time, input can cause changes within the state that can 
potentially transition the machine into another state. 
For example, say a light bulb has two states: off and on. When the bulb is not emitting light it is
in the "off" state. Flipping the light switch causes the bulb to emit light, therefore flipping 
the switch puts the bulb in the "on" state. Flipping the switch again puts the bulb back in the 
"off" state. The state of the machine (in this case, the light bulb circuit) is changed based on the 
input that is flipping the light switch. The Lemonade app will function under a similar set of 
conditions in the sense that user clicks (input) will change the state of the lemonade making 
process. 
In the Lemonade app there are 4 states. 

1. SELECT - in this state the user must select or “pick” a lemon from the lemon tree
2. SQUEEZE - in this state the user “squeezes” a lemon until it is juiced
3. DRINK - in this state, the lemonade is ready to drink
4. RESTART - in this state the glass of lemonade is empty

The progression of states is simple: SELECT to SQUEEZE to DRINK to RESTART to SELECT where we are 
back to the beginning of the state machine. 

This app contains only one `Activity` that displays a single `TextView` and a single `ImageView`. 
The `ImageView` will be clickable, and clicking the image drives the state machine forward. Here is 
a guide to writing the state machine logic:

* The code for the state machine should be written in the `clickLemonImage()` function found in the 
starter code.
* Use a conditional such as an `if` or `when` statement to determine the current state, i.e. 
the current value of the `lemonadeState` variable
    * When the image is clicked and the app is in the SELECT state (which is the default state):
a lemon must be “picked”, meaning the `lemonSize` variable should be set with the return value of 
the `pick()` function
    * The `squeezeCount` variable should be set to 0
    * The `lemonadeState` variable should be set to SQUEEZE
* When the image is clicked and the app is in the SQUEEZE state
    * The `squeezeCount` variable should be INCREASED by 1
    * The `lemonSize` variable should be DECREASED by 1
    * If the `lemonSize` variable is equal to 0, then the lemon has been juiced and the 
    `lemonadeState` variable should be set to DRINK
    * The `lemonSize` is no longer relevant and should be set to -1
* When the image is clicked and the app is in the DRINK state
    * The `lemonadeState` should be set to RESTART
* When the image is clicked and the app is in the RESTART state
    * The `lemonadeState` should be set to SELECT

Finally, after the state machine logic has been handled, the view must be updated to reflect the 
state transition. Before the `clickLemonImage()` function terminates, the `setViewElements()` 
function should be called.

Now that the logic for the `clickLemonImage()` method has been written, it must be called when the 
image is clicked. Call this method in the click listener for the image which has been set up in the 
`onCreate()` function in the starter code.

The logic to determine what information to display based on state must also be implemented. 
Here is guide to coding that logic:

* The code for determining the state and displaying the corresponding information should be written 
in the `setViewElements()` function found in the starter code.
* Create a conditional such as an `if` or `when` statement that determines the current state, i.e. 
the value of `lemonadeState`. For each state, set the `actionText` to the text corresponding to the 
state (this is referenced above in the App Requirements and in the code the string will be found in 
the `strings.xml` file in the starter code). Then set the `lemonImage` drawable to the drawable 
corresponding to the state (this is referenced above in the App Requirements and the drawable can 
be found in the `res/drawable` directory in the starter code).

Lastly, you need to display some information to help keep track of how many times a lemon has 
been squeezed. This logic has already been implemented, but the method needs to be called from the 
`setOnLongClick()` listener attached to the image. The listener can be found in the starter code in 
the `onCreate()` function. Replace `false` in this listener with the method that displays a Snackbar.

Tips
----

- Use the provided tests to ensure your app is running as expected
- DO NOT ALTER THE PROVIDED TESTS
