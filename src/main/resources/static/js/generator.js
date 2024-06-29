function generateWidget() {
    const steamId = document.getElementById('steamId').value;
    const widgetContainer = document.getElementById('widgetContainer');
    if (steamId) {
        const imageUrl = `https://steam-widget.com/widget/img?id=${steamId}`;
        const htmlCode = `<img src="${imageUrl}" width="350" height="75">`;
        widgetContainer.innerHTML = `
                    <div class="label">Preview:</div>
                    <div class="code-box">${htmlCode}</div>
                    <div class="label">Link:</div>
                    <div class="link-box">${imageUrl}</div>
                    <div class="label">HTML Code:</div>
                    <div class="code-box">${htmlCode.replace(/</g, '&lt').replace(/>/g, '&gt')}</div>
                `;
    } else {
        widgetContainer.innerHTML = '<p>Please enter a valid Steam ID.</p>';
    }
}