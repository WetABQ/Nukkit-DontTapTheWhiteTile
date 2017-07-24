# DontTapTheWhiteTile
DontTapTheWhiteTile  - Nukkit plugin

--------

## How to use?

- **Install plugin**
  1. Download 'DontTapTheWhiteTile' plugin.
  2. Put the plugin in the plugins folder.
  3. Restart your server.

- **Set the game** 
  1. Enter '/dset' in the game.
  2. According tips to build a 5 (high) * 4 (wide) frame, and then put four signs next to.
  
- **Play the game**
  1. Click already set the start button.
  2. Click on black wool, do not click on white wool.
    
- **Delete the game setting**
  1. Enter '/ddel' in the game

## Config:(No changes are recommended)
```
status: 0 #Set the state
top: false #Top1 sign location
top2: false #Top2 sign location
top3: false #Top3 sign location
start: false #Start button position
line: {} #Game area
first: 999 # First time used
second: 999 # Second time used
third: 999 # Third time used
```

--------

## Command:
|    Command    |      Args     |          Describe              |                     Permission                     |
|:-------------:|:--------------|:------------------------------:|:--------------------------------------------------:|
|      dset     |      [b]      |          Set the game          |        DontTapTheWhiteTile.Command.SetGame         |
|      ddel     |      None     |        Delete the game         |        DontTapTheWhiteTile.Command.DelGame         |

## Command Permission:
|             PermissionName                |               Describe               |    Default owner     |
|:-----------------------------------------:|:------------------------------------:|:--------------------:|
|    DontTapTheWhiteTile.Command.SetGame    |    Set the game command permission   |         OP           |
|    DontTapTheWhiteTile.Command.DelGame    |  Delete the game command permission  |         OP           |

--------

### Open source:

- [GitHub(DontTapTheWhiteTile)](https://github.com/WetABQ/DontTapTheWhiteTile)

### Author:

- [WetABQ](https://github.com/WetABQ)
