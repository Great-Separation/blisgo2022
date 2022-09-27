$(loadImg());
function loadImg() {
  const host = "https://source.unsplash.com/";
  const size = "1000x1000/";
  const period = "daily";
  const query = "waste,garbage,trash,recycling";
  const options = "&blur=5";

  const url = host + size + period + "?" + query;
  const imageDiv = document.querySelector('#highlight');

  fetch(url)
    .then(response => {
      return response;
    })
    .then(data => {
      var url = data.url;
      url = url.replace("fm=jpg", "fm=webp");
      url = url.replace("q=80", "q=20");
      
      imageDiv.style.background = "url('" + url + "')center / cover no-repeat";
    });
}

// function loadImg() {
//   const host = "https://api.unsplash.com/photos/random";
//   const query = "waste,garbage,trash,recycling"
//   const options = "&fm=webp&w=1500&q=10&blur=50"
//   const clientId = "CTW7rq3n5wwaqHphLTlv47RsPHweBqy4QWe7_YVCvk8";

//   const url = host + "?query=" + query + "&client_id=" + clientId;
//   const imageDiv = document.querySelector('#highlight');

//   fetch(url)
//     .then(response => {
//       return response.json();
//     })
//     .then(data => {
//       imageDiv.style.background = "url('" + data.urls.raw + options + "')center / cover no-repeat";
//     });
// }