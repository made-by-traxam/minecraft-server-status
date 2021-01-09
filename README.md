# MinecraftServerStatus
MinecraftServerStatus is a Discord bot that can show the online status of your Minecraft server.

## Table of Contents
* [File structure](#File-structure)
* [Configuration](#Configuration)
* [Development notes](#Development-notes)
* [Deployment](#Deployment)
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

## Deployment
I am using [dokku](https://github.com/dokku/dokku) to deploy the bot but [Heroku](https://heroku.com) should work too.

1. Create a dokku app:
    ```bash
    # on the dokku server
    $ dokku apps:create minecraft-server-status
    ```
2. Configure process scaling:
    ```bash
    # on the dokku server
    $ dokku ps:scale web=0 worker=1
    ```
3. Configure [required environment variables](#Configuration):
    ```bash
    # on the dokku server
    # do the following for each environment variable
    $ dokku config:set DISCORD_TOKEN=y0ur_d15c0rd_b0t_t0k3n
    ```
4. Add dokku remote:
    ```bash
    # on your local machine, inside the repo
    $ git remote add dokku dokku@your.dokku.host:minecraft-server-status
    ```
5. Deploy:
    ```bash
    # on your local machine, inside the repo
    $ git push dokku
    ```

## License
The code inside [`MCServerPing/`](./MCServerPing) is copyrighted by [@jamietech](https://github.com/jamietech) and used
with the license contained in the [`MCServerPing/LICENSE`](./MCServerPing/LICENSE) file.

The rest of the code is available with an [MIT License](./LICENSE).
