document.addEventListener("DOMContentLoaded", function() {
    const getInspirationButton = document.getElementById("getInspiration");
    const inspirationImageDiv = document.getElementById("inspirationImage");

    getInspirationButton.addEventListener("click", function() {
        const width = document.getElementById("width").value;
        const height = document.getElementById("height").value;
        const grayscale = document.getElementById("grayscale").checked;
        const blur = document.getElementById("blur").value;

        let apiUrl = `https://picsum.photos/${width}/${height}`;

        if (grayscale) {
            apiUrl += "?grayscale";
        }

        if (blur > 1 && blur <= 10) {
            apiUrl += `&blur=${blur}`;
        }

        getInspirationButton.onclick=

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.blob();
            })
            .then(blob => {
                const imageUrl = URL.createObjectURL(blob);
                inspirationImageDiv.innerHTML = `<img src="${imageUrl}" alt="Inspiration">`;
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});
