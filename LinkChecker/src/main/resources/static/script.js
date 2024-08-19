function checkLinks() {
    var url = document.getElementById('urlInput').value;
    var button = document.getElementById('checkButton');

    // Add the 'clicked' class to change the button color
    button.classList.add('clicked');

    fetch(`/link/check?url=${url}`)
        .then(response => response.json())
        .then(data => {
            displayResult(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

  function goToHomePage() {
            // Replace 'index.html' with your home page URL
            window.location.href = 'history.html';
        }
           function openLinkAutomatically(url1) {
            // Replace 'https://www.example.com' with the desired URL
            window.open(url1, '_blank');
            // The '_blank' parameter opens the link in a new tab/window
        }
function displayResult(result) {
	console.log(result);
    var resultDiv = document.getElementById('result');
    resultDiv.innerHTML = '';

    if (result.valid=='true') {
       resultDiv.innerHTML = `<p style="color: green;">link is valid!</p>`;
       openLinkAutomatically(result.url);
       console.log(result);
    } else {
        resultDiv.innerHTML = `<p style="color: red;"> link is invalid:</p>`;
        resultDiv.innerHTML += `<p>${result.url}</p>`;
    }
}

// Add event listener to reset the button color on transition end
document.getElementById('checkButton').addEventListener('transitionend', function () {
    this.classList.remove('clicked');
});
