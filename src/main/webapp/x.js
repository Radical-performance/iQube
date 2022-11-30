// if (el.parentNode.id === "tracklistTab") {
//     ul.lastChild.lastChild.innerText = "remove from tracklist";
//     //remove from0 tracklist button CLICK
//     ul.children[3].addEventListener("click",)
//
//     let remove = function () {
//         tracklist_tracks_identificarors = JSON.parse(localStorage.getItem("tracks_identificators"));
//         tracklist = JSON.parse(localStorage.getItem("tracklist"));
//
//         let id = ul.getAttribute("id");
//         let index = tracklist_tracks_identificarors.identificators.indexOf(parseInt(id, 10)); //<----- 10 - десятичная система
//
//         if (tracklist_tracks_identificarors.identificators.includes(parseInt(id, 10))) {
//             tracklist_tracks_identificarors.identificators.splice(index, 1);
//             tracklist.tracks.splice(index, 1);
//             localStorage.setItem("tracks_identificators", JSON.stringify(tracklist_tracks_identificarors));
//             localStorage.setItem("tracklist", JSON.stringify(tracklist));
//             setTimeout(function () {tracklistTab.removeChild(el)}, 920);
//             /////
//             let p = document.querySelector('[id'+id+']');
//             p.children[3].children[0].style.transform = "rotate(0deg)";
//             p.children[3].children[2].innerText = "ADD TO TRACKS";
//             this.removeEventListener("click",remove);
//             p.setAttribute("id",id);
//             ///
//
//             console.log('removing track html element has been removed from  TracklistTab');
//
//             el.style.transition = ".9s linear";
//             el.style.height = "0";
//
//             remove_from_tracklist_request({track_id: id});
//             console.log('TRACK SUCCESSFULY  REMOVED..');
//             // index = null;
//             // id = null;
//         } else {console.log('ERROR DURING TRACK FROM TRACKLIST REMOVING');}
//
//     });
// } else if (el.parentElement.id === "searchedTracksTab") {
//     searchTabChildNodes = searchedTracksTab.children;
//     for (let x = 0; x < searchTabChildNodes.length; x++) {
//         let ul = searchTabChildNodes[x].children[0].children[1].children[0];
//         searchTabChildNodes[x].children[1].innerText = search_data.data[x].artist.name;
//         searchTabChildNodes[x].children[2].innerText = search_data.data[x].title;
//         ul.setAttribute("number", x.toString());
//         ul.setAttribute("id",search_data.data[x].id);
//
//
//         let addToTracklist = function (e) {
//
//             let val = ul.getAttribute("number");
//             let id = ul.getAttribute("id");
//
//             //check if localstorage(tracklist) contains track id!!!!!!
//             let trackObject = {
//                 id: search_data.data[val].id,
//                 artist: {name: search_data.data[val].artist.name},
//                 track_id: search_data.data[val].id,
//                 artist_id: search_data.data[val].artist.id,
//                 artist_name: search_data.data[val].artist.name,
//                 artist_picture: search_data.data[val].artist.picture_big,
//                 artist_tracklist: search_data.data[val].artist.tracklist,
//                 album_id: search_data.data[val].album.id,
//                 title: search_data.data[val].title,
//                 url: search_data.data[val].preview,
//                 duration: search_data.data[val].duration,
//                 isLiked: false,
//                 timeMarks: []
//             }
//
//             tracklist = JSON.parse(localStorage.getItem("tracklist"));
//             tracklist_tracks_identificarors = JSON.parse(localStorage.getItem("tracks_identificators"));
//
//             ul.children[3].children[0].style.transform = "rotate(45deg)";
//             ul.children[3].children[0].style.color = "red";
//
//             // let t = document.getElementById(id.toString());
//
//             ul.children[3].children[2].innerText = 'remove from tracks';
//             ul.removeAttribute("id");
//             ul.setAttribute('id' + id, 'x');
//
//             if (tracklist_tracks_identificarors.identificators.includes(trackObject.track_id) === false) {
//                 tracklist_tracks_identificarors.identificators.push(trackObject.track_id);
//                 tracklist.tracks.push(trackObject);
//                 localStorage.setItem("tracklist", JSON.stringify(tracklist));
//                 localStorage.setItem("tracks_identificators", JSON.stringify(tracklist_tracks_identificarors));
//                 // console.log('track object with id: _' + id.toString() + '_ successfully added to localstorage(tracklist + identificators)...FROM SearchTab!!!')
//                 add_to_tracklist_request(trackObject);
//                 let ele = createEl(search_data.data[x]);
//                 tracklistTab.appendChild(ele);
//                 initListeners(ele, search_data.data[x]);
//             } else {console.log("track already in tracklist")}
//             ul.children[3].removeEventListener('click', add);
//
//         };
//
//
//     }
// }