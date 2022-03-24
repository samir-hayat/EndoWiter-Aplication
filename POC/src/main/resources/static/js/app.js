// camera view tabs on top
function openCity(cityName,elmnt,color) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].style.backgroundColor = "";
    }
    document.getElementById(cityName).style.display = "block";
    elmnt.style.backgroundColor = color;
  
  }
  // Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();
// camera view tab end

// image gallery tabs start
function openCityone(evt, cityName) {
    var i, tabcontentone, tablinks1;
    tabcontentone = document.getElementsByClassName("tabcontentone");
    for (i = 0; i < tabcontentone.length; i++) {
      tabcontentone[i].style.display = "none";
    }
    tablinksone = document.getElementsByClassName("tablinksone");
    for (i = 0; i < tablinksone.length; i++) {
      tablinksone[i].className = tablinksone[i].className.replace(" active", "");
    }
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
  }
  document.getElementById("defaultOpen").click();
// image gallery tabs end

// camera setting for capture images

var video = document.querySelector("#video");    
// Basic settings for the video to get from Webcam  
const constraints = {  
    audio: false,  
    video: {  
        width: 475, height: 475  
    }  
};
// This condition will ask permission to user for Webcam access  
if (navigator.mediaDevices.getUserMedia) {  
    navigator.mediaDevices.getUserMedia(constraints)  
        .then(function (stream) {  
            video.srcObject = stream;  
        })  
        .catch(function (err0r) {  
            console.log("Something went wrong!");  
        });  
} 
function stop(e) {  
    var stream = video.srcObject;  
    var tracks = stream.getTracks();  

    for (var i = 0; i < tracks.length; i++) {  
        var track = tracks[i];  
        track.stop();  
    }  
    video.srcObject = null;  
}  
// Below code to capture image from Video tag (Webcam streaming)  
$("#btnCapture").click(function () {  
    var canvas = document.getElementById('canvas');  
    var context = canvas.getContext('2d');  

    // Capture the image into canvas from Webcam streaming Video element  
    context.drawImage(video, 0, 0);  
}); 
// camera setting for capture images

//video element  start
let mediaRecorder;
let recordedBlobs;
const errorMsgElement = document.querySelector('span#errorMsg');
const recordedVideo = document.querySelector('video#recorded');
const recordButton = document.querySelector('button#record');
const playButton = document.querySelector('button#play');
const downloadButton = document.querySelector('a#download');
recordButton.addEventListener('click', () => {
if (recordButton.textContent === 'Record') {
    startRecording();
} else {
    stopRecording();
    recordButton.textContent = 'Record';
    playButton.disabled = false;
    downloadButton.disabled = false;
}
});

downloadButton.addEventListener('click', () => {
const blob = new Blob(recordedBlobs, {type: 'video/mp4'});
const url = window.URL.createObjectURL(blob);
const a = document.createElement('a');
a.style.display = 'none';
a.href = url;
a.download = 'w3-coder-recorder-test.mp4';
document.body.appendChild(a);
a.click();
setTimeout(() => {
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
}, 100);
});

function handleDataAvailable(event) {

console.log('handleDataAvailable', event);
if (event.data && event.data.size > 0) {
    recordedBlobs.push(event.data);
}
}

function startRecording() {
recordedBlobs = [];
let options = {mimeType: 'video/webm;codecs=vp9,opus'};
try {
    mediaRecorder = new MediaRecorder(window.stream, options);

} catch (e) {
    console.error('Exception while creating MediaRecorder:', e);
    errorMsgElement.innerHTML = `Exception while creating MediaRecorder: ${JSON.stringify(e)}`;
    return;
}
console.log('Created MediaRecorder', mediaRecorder, 'with options', options);
recordButton.textContent = 'Stop';
playButton.disabled = true;
downloadButton.disabled = true;
mediaRecorder.onstop = (event) => {
const superBuffer = new Blob(recordedBlobs, {type: 'video/webm'});
recordedVideo.src = null;
recordedVideo.srcObject = null;
recordedVideo.src = window.URL.createObjectURL(superBuffer);
recordedVideo.controls = true;
recordedVideo.play();
console.log('Recorder stopped: ', event);
console.log('Recorded Blobs: ', recordedBlobs);
};
mediaRecorder.ondataavailable = handleDataAvailable;
mediaRecorder.start();
recordedVideo.src = null;
recordedVideo.srcObject = null;
recordedVideo.controls = false;
recordedVideo.pause(true);
console.log('MediaRecorder started', mediaRecorder);
}

function stopRecording() {
mediaRecorder.stop();
}

function handleSuccess(stream) {
recordButton.disabled = false;
console.log('getUserMedia() got stream:', stream);
window.stream = stream;

const gumVideo = document.querySelector('video#gum');
gumVideo.srcObject = stream;
}

async function init(constraints) {
try {
    const stream = await navigator.mediaDevices.getUserMedia(constraints);
    handleSuccess(stream);
} catch (e) {
    console.error('navigator.getUserMedia error:', e);
    errorMsgElement.innerHTML = `navigator.getUserMedia error:${e.toString()}`;
}
}

document.querySelector('button#start').addEventListener('click', async () => {
const hasEchoCancellation = document.querySelector('#echoCancellation').checked;
const constraints = {
    audio: {
    echoCancellation: {exact: hasEchoCancellation}
    },
    video: {
    width: 475, height: 475,  
    }
};
console.log('Using media constraints:', constraints);
await init(constraints);
});

// get all camera 
navigator.mediaDevices.enumerateDevices().then((devices) => {
    let videoSourcesSelect = document.getElementById("video-source");
    // Iterate over all the list of devices (InputDeviceInfo and MediaDeviceInfo)
    devices.forEach((device) => {
        let option = new Option();
        option.value = device.deviceId;
        // According to the type of media device
        switch(device.kind){
            // Append device to list of Cameras
            case "videoinput":
                option.text = device.label || `Camera ${videoSourcesSelect.length + 1}`;
                videoSourcesSelect.appendChild(option);
                break;
        }
        console.log(device);
    });
}).catch(function (e) {
    console.log(e.name + ": " + e.message);
});
// $(document).ready(function() {
//   $('.video-gallery').magnificPopup({
//   delegate: 'a', 
//   type: 'iframe',
//   gallery:{
//     enabled:true
//   }
// });
// });

// $(document).ready(function() {
//   $('.image-gallery').magnificPopup({
//   delegate: 'a', 
//   gallery:{
//     enabled:true
//   }
// });
// });
