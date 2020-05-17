# Backgammon Dice
Simple two dice app.

Some concepts that is used in this app:
**Animation**
Shake animation of the in the introduction dialogue.
Rotation animaton of the dices while rolling the dices.

**MediaPlayer**
MediaPlayer is used to play dice sound file.

**SensorManager**
SensorManager is used to detect the shake movement.


**Ktlint** and **detekt** used to follow best coding practices. 

**SharedPreferences** is implemented as a seperate file and instantiated at Application file to provide easy app-wide access.

**Tags** for the activities are created as following example:
`private val tag: String = MainActivity::class.java.getName()`

<img src=bg1.png width="250"> <img src=bg2.png width="250"> <img src=bg3.png width="250">

