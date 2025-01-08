Steam Profile Widget / Badge
==================

This is a small Spring Boot Service for getting a small Steam profile widget / badge for every public Steam profile.

## Generator Page

For easy use, you can use the generator page by putting in your SteamID64, customURL, CommunityID.

Generator Page:

https://steam-widget.com

## Usage

For the SteamId, you can use the SteamID64, customURL, or CommunityID. Below are the details on how to use the service to generate widgets.

To generate an image widget, use the following URL format:
```
https://steam-widget.com/widget/img?id=<SteamId>&gameList=<GameList>&gameListSize=<GameListSize>&playingRightNow=<playingRightNow>&purpose=<Purpose>&width=<Width>
```

### Parameters:

- **id**: The Steam ID of the user. This can be a SteamID64, customURL, or CommunityID.
- **gameList**: Show a list of games of the user in the widget
  - *TOP_GAMES_TOTAL*: Show the top games of the user, sorted by playtime
  - *TOP_GAMES_RECENT*: Show the top games of the user in the last 2 weeks, sorted by playtime in the last 2 weeks
  - *RECENT_GAMES*: Show the games the user played in the last 2 weeks, sorted by the last play time
- **gameListSize**: Limit the amount of games shown in the widget with maximum limit of 10 games
- **playingRightNow**: Show if the user is playing a game right now
  - TRUE: Show that the user is playing a game right now
  - FALSE: Don't show that the user is playing a game right now
- **purpose**: The purpose for which the widget is being generated. This is used for logging and analytics.
- **width**: The width of the generated image in pixels.

### Example:

This URL generates an image widget for the Steam user lizard_darksoul with a purpose of github_repo and a width of 350 pixels.

```
https://steam-widget.com/widget/img?id=lizard_darksoul&purpose=github_repo&width=350
```

<img src="https://steam-widget.com/widget/img?id=lizard_darksoul&purpose=github_repo&width=350">

### Embedded HTML:

```HTML
<img src="https://steam-widget.com/widget/img?id=lizard_darksoul&purpose=github_repo&width=350">
```

### Embedded Markdown:

```Markdown
![Steam Profile](https://steam-widget.com/widget/img?id=lizard_darksoul&purpose=github_repo&width=350)
```

### Embedded BBCode:

```BBCode
[img]https://steam-widget.com/widget/img?id=lizard_darksoul&purpose=github_repo&width=350[/img]
```

## Metrics

### Profile Metrics

To get metrics about a profile, use the following URL format:

```
https://steam-widget.com/metric?id=<Steam64Id>
```

- **id**: The Steam64Id of the user

#### Formats

There are formats for this endpoint:
- Accept: \*/\*
  - Outputs only the hits to this users widget
- Accept: application/json
  - Outputs the profile metric in json format

### Hit Metrics

To get hit metrics about a profile, use the following URL format:
```
https://steam-widget.com/metric/hits?id=<Steam64Id>&purpose=<Purpose>
```

- **id**: The Steam64Id of the user.
- **purpose**: The purpose for which the widget is being generated. This is used for logging and analytics.

#### Formats

There are formats for this endpoint:
- Accept: \*/\*
  - Outputs only the hits to this users widget

## Credits

- [Felix Lerch](https://github.com/felixlerch)

## License


    Copyright 2024 Felix Lerch
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
