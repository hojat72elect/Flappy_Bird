This is a 2D endless runner game (a clone of Flappy Bird) written in pure java.
Feel free to PR to this repository, I'm looking forward to all kinds of contributions :)

The original project was a tutorial at "https://youtu.be/UfjKpp_Wtic".

________________________________________________________________________

# Developer log 1 - January 28th 2022

I completed all the steps in the tutorial that described this game. But there are a couple of problems about the current state of the project and the tools I'm using for developing it:

1- android SDK isn't specifically designed to develop games in a visual and easy way. This isn't a good way for scaling this project into a bigger one without a huge hassle. I'm writing tons of code for just a tiny animation in the bird and so on.

2- There's not an easy way for debugging the game. For example, right now there's a bug when I play this game on Nexus 6p, the lower pipes don't show up on the screen. And also the frame rate for bird is so low. it doesn't look natural.

3- all of it sounds like a huge hassle for a game at this scale. Maybe it's better to spend this time for a high level game engine like Unity.

*** But I like to come back to this project in future. Some possible developments are:

1- I could have a look at "https://jmonkeyengine.org/", "https://korge.org/", "https://libgdx.com/" or any other JVM-related game frameworks for developing this game more easily. It'd be the best if I could add those frameworks via a simple Dependency Injection trick.

2- I could have a look at Android profilers to debug low speed issue of the game.

3- I could make use of 3rd party language-safe tools for more easily scaling this game. 

________________________________________________________________________
