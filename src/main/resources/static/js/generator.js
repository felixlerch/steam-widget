document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const steamIdParam = urlParams.get('steamId');
    if (steamIdParam) {
        const sanitizedSteamId = escapeHtml(decodeURIComponent(steamIdParam));
        document.getElementById('steamId').value = sanitizedSteamId;
        // Optionally, automatically generate the widget
        generateWidget();
    }
});

function generateWidget() {
    let steamId = document.getElementById('steamId').value;
    const widgetContainer = document.getElementById('widgetContainer');
    widgetContainer.innerHTML = ''; // Clear previous content

    if (!steamId) {
        // Display error message if steamId is empty
        const errorMessage = document.createElement('p');
        errorMessage.textContent = 'Please enter a valid Steam ID.';
        widgetContainer.appendChild(errorMessage);

        //Remove steamId query parameter from URL
        const urlParams = new URLSearchParams(window.location.search);
        const steamIdParam = urlParams.get('steamId');
        if (steamIdParam) {
            urlParams.delete('steamId');
            window.history.replaceState(null, null, "?" + urlParams.toString());
        }

        return;
    }

    // Sanitize the steamId to remove any HTML tags or JavaScript code
    steamId = escapeHtml(steamId);

    // Update the window's location to include steamId as a query parameter
    const queryParams = new URLSearchParams(window.location.search);
    queryParams.set('steamId', steamId);
    window.history.replaceState(null, null, "?" + queryParams.toString());

    const imageUrl = constructSafeUrl(steamId);

    // Preview
    const previewLabel = document.createElement('div');
    previewLabel.className = 'label';
    previewLabel.textContent = 'Preview:';
    widgetContainer.appendChild(previewLabel);

    const previewImageBox = document.createElement('div');
    previewImageBox.className = 'code-box';
    widgetContainer.appendChild(previewImageBox);

    const previewImage = document.createElement('img');
    previewImage.src = `${imageUrl}&purpose=generator`;
    previewImage.width = 350;
    previewImage.height = 75;
    previewImageBox.appendChild(previewImage);

    // Link
    const linkLabel = document.createElement('div');
    linkLabel.className = 'label';
    linkLabel.textContent = 'Link:';
    widgetContainer.appendChild(linkLabel);

    const linkBox = document.createElement('div');
    linkBox.className = 'link-box';
    linkBox.textContent = imageUrl;
    widgetContainer.appendChild(linkBox);

    // HTML Code
    const htmlCodeLabel = document.createElement('div');
    htmlCodeLabel.className = 'label';
    htmlCodeLabel.textContent = 'HTML Code:';
    widgetContainer.appendChild(htmlCodeLabel);

    const htmlCodeBox = document.createElement('div');
    htmlCodeBox.className = 'code-box';
    htmlCodeBox.textContent = `<img src="${imageUrl}" width="350" height="75">`;
    widgetContainer.appendChild(htmlCodeBox);
}

// Function to escape special HTML characters to prevent XSS
function escapeHtml(input) {
    return input.replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

// Use encodeURIComponent for URL parameters
function constructSafeUrl(steamId) {
    return `https://steam-widget.com/widget/img?id=${encodeURIComponent(steamId)}`;
}