## Connect4
Connect Four (also known as Captain's Mistress, Four Up, Plot Four, Find Four, Four in a Row, Four in a Line and Gravitrips (in Soviet Union)) is a two-player connection game in which the players first choose a color and then take turns dropping one colored disc from the top into a seven-column, six-row vertically suspended grid. 
The pieces fall straight down, occupying the next available space within the column. The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own discs. Connect Four is a solved game. The first player can always win by playing the right moves.

## Design

## Strategies

Each player is assigned a strategy in the dagger initialization, Following are the supported strategies

### Sequential

The player moves from column 0 -> 6 filling it

### Random

The player picks any random available column and fills it

## Dependecies

Thanks to the following Libraries
```
<orderEntry type="library" name="org.projectlombok:lombok:1.18.2" level="project" />
<orderEntry type="library" name="com.google.guava:guava:25.1-jre" level="project" />
<orderEntry type="library" name="org.slf4j:slf4j-log4j12:1.7.12" level="project" />
<orderEntry type="library" name="com.googlecode.lanterna:lanterna:3.0.1" level="project" />
<orderEntry type="library" name="com.google.dagger:dagger:2.11" level="project" />
<orderEntry type="library" scope="PROVIDED" name="com.google.dagger:dagger-compiler:2.11" level="project" />

```
