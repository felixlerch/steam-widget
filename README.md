Steam Profile Widget / Badge
==================

This is a small Spring Boot Service for getting a small Steam profile widget / badge for every public Steam profile.

## Usage

### URL Structure

Link:
```
https://steam-widget.com/widget?id=<Steam64Id>
```

Example:

https://steam-widget.com/widget?id=76561198120613721

### Embedded

HTML:
```HTML
<iframe src="https://steam-widget.com/widget?id=<Steam64Id>" style="border: 0" width="325" height="75"></iframe>
```

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
