HiVoltsProject
==============

This is a repo for our HiVolts assignment in our AP Computer Science Class.

###Who are we?
We're a group of high school students taking the AP Computer Science Class.

###What is this assignment?
The mission is to recreate a 1970's computer game known as HiVolts, using our knowledge we've gained so far
and our previous knowledge from Conway's Game of Life and similar projects.

###How do I run this?
These points of data make a beautiful line, and we're out of beta, we're releasing on time! Version 1.0 has been released! You can run our game by downloading the jar executable file (located in the HiVolts folder above) and simply clicking it. 

###Can I fork this?
We're not sure why you'd want to do that, but feel free to send a request to DoctorRyuko.

###Other contributors/helpers:
* My father, Stephen Turnbull was very helpful in the completion of this project, he gave me pointers and helped me debug.
###Sources of code:
* In our first iteration, we used an example keylistener from [here](http://examples.javacodegeeks.com/desktop-java/awt/event/a-complete-keylistener-example/).
###Problems We Encountered:
* We had several issues with our collision detection, with several different fixes.
* Firstly, we had issues with our spawn collision detection bugging out. This was solved by adding a new argument to our isFence and isMho methods.
* We then had a mysterious exception thrown when trying to pass our collision detection to the Mho AI. It turned out a single "=" was causing the error.
* And most recently, when Mho AI had been fully implemented, we had an issue where our collision detection was again not working, even though we knew our collision detection methods were solid. We then realized that the error was caused by having two Mhos moving to the same place on the same turn, the second Mho to move would not be picked up by the killMhos method. The fix was to call killMhos after every mhoMovement check, and not do a full sweep at the end.
###Decisions We Made:
* We decided as a group to allow the player dictate the pace of the game, meaning the player can move as quickly or as slowly as they wish.
###Assumptions We Made in the Bleak Storm of Ambiguity:
* Honestly, the [documentation page](http://paleyontology.com/AP_CS/hivolts) (so kindly provided by Mr. Paley of Gunn), was not very ambiguous. It clearly spelled out the rules and requirements we needed to implement.
###Justifications for Choices We Made That Resulted in Fewer Features Than the Requirements Call For:
* When we decided to allow the player to dictate the pace of the game, we got rid of our TurnControl class, which in turn removes the requirement of printing out the turn state. We personally think having turn being indistinguishable to the computer is better, as it allows the player to learn on their own terms, rather than having a set time to wait after they move.