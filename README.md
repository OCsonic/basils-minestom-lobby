# Basil's Simple Minestom Lobby
## Functionality
- Everything is configured in Json
- Spawn and Bungee portals are configurable
- Basic combat with customizable knockback
- Anvil map file loading
- Simple automatic command registration using reflections
- Simple server console using stdin (will probably recieve more features soon)

## Work In Progress
- Reload command for reloading config file and map

## Setup
Dependencies:

	jq
	java 21
	gradle 8.3+

To run the lobby simpley edit config.json to provide a Java home and Gradle binary,

then run `./devstart` to compile and launch the server, and `./start` for subsequent launches if no code has been changed!

To enable velocity all you have to do is copy/link forwarding.secret from your velocity proxy in the same directory as the start script, then it will automatically enable and configure velocity to use it at the next server start!
