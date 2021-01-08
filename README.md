# MinecraftServerStatus
MinecraftServerStatus is a Discord bot that can show the online status of your Minecraft server.

## Table of Contents
* [File structure](#File-structure)
* [Configuration](#Configuration)
* [Development notes](#Development-notes)
* [License](#License)

## File structure
* [`MCServerPing/`](./MCServerPing) contains a modified version of [@lucaazilim](https://github.com/lucaazalim) 's [minecraft-server-ping](https://github.com/lucaazalim/minecraft-server-ping).
* [`src/`](./src) contains the code for the Discord bot.

## Configuration
You'll need to set the following environment variables to run the bot. Use a `.env` file in development environments.

| Variable name | Description |
| --- | --- |
| `DISCORD_TOKEN` | The Discord bot token you want to use for your bot. Take a look at [this guide](https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token). |
| `OWNER_ID` | The user ID of the bot owners Discord account. |
| `SUPPORT_SERVER` | A Discord invitation URL to the bots support server. |
| `SUPPORT_EMAIL` | An email address to be used for contacting the owner of the bot. |

## Development notes
* tested with Java 8
* run `gradle run` in the root directory to run the bot
* you can put your environment variables into a `.env` file

## License
The code inside [`MCServerPing/`](./MCServerPing) is copyrighted by [@jamietech](https://github.com/jamietech) and used
with the license contained in the [`MCServerPing/LICENSE`](./MCServerPing/LICENSE) file.

The rest of the code is available with an [MIT License](./LICENSE).
