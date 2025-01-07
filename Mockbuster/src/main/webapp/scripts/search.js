async function retrieveMovies(contextPath) {
    try {
        const title = document.getElementById('searchbar').value;  // Ottieni il titolo dalla search bar
        const url = `${contextPath}/browse/SearchMovieTitleServlet?title=${title}`;  // Costruisci l'URL

        // Effettua la richiesta asincrona usando Fetch API
        const response = await fetch(url, {
            method: 'GET',  // Metodo GET
            headers: {
                'Cache-Control': 'no-cache',  // Disabilita la cache
            },
        });

        // Controlla se la risposta è ok (status 200)
        if (response.ok) {
            const data = await response.json();  // Parsea la risposta come JSON
            printMovies(data.movies, contextPath);  // Chiama la funzione per stampare i film
        } else {
            // Se la risposta non è ok, mostra un errore
            alert(`Problemi nell'esecuzione della richiesta:\n${response.statusText}`);
        }
    } catch (error) {
        // Gestisce eventuali errori durante la richiesta
        console.error("Errore durante la richiesta:", error);
        alert("Si è verificato un errore durante la ricerca dei film.");
    }
}

function printMovies(movies, contextPath) {
    let containers = document.getElementById('containers');
    containers.innerHTML = '';
    movies.forEach(function (movie) {
        containers.innerHTML +=
                '<div class = "container">' +
                '<a href = "' + contextPath + '/browse/MoviePageServlet?id=' + movie.id + '">' +
                '<img src = "' + contextPath + '/images/posters/' + movie.posterpath + '" ' +
                'onerror="this.src=\'' + contextPath + '/images/posters/error.jpg\'" />' +
                '</a>' +
                '</div>';
    });
}