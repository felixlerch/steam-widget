Steam Profile Widget / Badge
==================

This is a small Spring Boot Service for getting a small Steam profile widget / badge for every public Steam profile.

## Generator Page

For easy use, you can use the generator page by putting in your SteamID64, customURL, CommunityID.

Generator Page:

https://steam-widget.com

## Usage

For the SteamId you can use the SteamID64, customURL, CommunityID.

### Image Version

Link:
```
https://steam-widget.com/widget/img?id=<SteamId>&purpose=<Purpose>
```

Example:

https://steam-widget.com/widget/img?id=76561198120613721&purpose=github

<img src="https://steam-widget.com/widget/img?id=76561198120613721&purpose=github" width="350" height="75">

### HTML Version

Link:
```
https://steam-widget.com/widget/html?id=<SteamId>&purpose=<Purpose>
```

Example:

https://steam-widget.com/widget/html?id=76561198120613721&purpose=github

## Embedded

HTML:
```HTML
<img src="https://steam-widget.com/widget/img?id=<SteamId>&purpose=<Purpose>" width="350" height="75">
```

```HTML
<iframe src="https://steam-widget.com/widget/html?id=<SteamId>&purpose=<Purpose>" style="border: 0" width="325" height="75"></iframe>
```

## Metrics

### Profile Metrics

Link:
```
https://steam-widget.com/metric?id=<Steam64Id>
```

Only use the Steam64Id for getting metrics about a profile.

There are formats for this endpoint:
- Accept: \*/\*
  - Outputs only the hits to this users widget
- Accept: application/json
  - Outputs the profile metric in json format

### Hit Metrics

Link:
```
https://steam-widget.com/metric/hits?id=<Steam64Id>&purpose=<Purpose>
```

Only use the Steam64Id for getting metrics about a profile.

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
