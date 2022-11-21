

function initMap() {
    function getFunction() {
      let result = new XMLHttpRequest()
      result.open("GET", "http://localhost:8080/mapa", false)
      result.setRequestHeader(
        "Authorization", 
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzaGluaWdhbWkiLCJqdGkiOiI0IiwiQ0FSR08iOlsiUk9MRV9BTkFMSVNUQSJdLCJpYXQiOjE2Njg5MTMyMDAsImV4cCI6MTY2ODk5OTYwMH0.sSwIkLzZQrO5G31N2Kvb-HZM2SjdvQQqQBMN3NxtC98")
      result.send()
      return result.responseText

    }

    data = JSON.parse(getFunction())
    
    const map = new google.maps.Map(document.getElementById("map"), {
      zoom: 4,
      center: {lat: +data[0].latitude, lng: +data[0].longitude},
    });

  
    data.forEach(data => {
      geraMarker(data.latitude, data.longitude)
    });

    function geraMarker(lat, long){
      return new google.maps.Marker({
        position: {lat: +lat, lng: +long},
        map
      })
    } 
}

window.initMap = initMap;