# Isekai [![License](https://img.shields.io/badge/license-MIT-red)](LICENSE)

A sokoban editor and game.

# 'skb' file format

A skb file is a zip file. His tree is represented below.

* *.skb
    * levels (contains level data)
        * 1
        * 2
        * ...
        * 42
    * sprites (contains all sprites used by the game)
        * a.png
        * b.png
        * ...
        * 43.png
    * settings.json
        * name
        * author
        * version
        * game_width
        * game_height
        * tile_width
        * tile_height
    * map.json
        * wall (sprite)
        * floor (sprite)
        * crate (sprite)
        * crate_on_target (sprite)
        * target (sprite)
    * player.json
        * left
            * static (sprite)
            * walk (sprite)
        * right
            * static (sprite)
            * walk (sprite)
        * up
            * static (sprite)
            * walk (sprite)
        * down
            * static (sprite)
            * walk (sprite)