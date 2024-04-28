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