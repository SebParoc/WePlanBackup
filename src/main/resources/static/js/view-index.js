var hoursLabel = document.getElementById("hours");
var minutesLabel = document.getElementById("minutes");
var secondsLabel = document.getElementById("seconds");
var totalTime = document.getElementById("totalTime");

var totalSeconds = 0;
var totalMinutes = 0;
var totalHours = 0;

var counter;
var timerOn;
var htmlResets;
var totalMills = 0;

function startTimer(){
    if(timerOn ==1){
        return;
    }
    else{
        counter = setInterval(setTime,10);
        timerOn = 1;
        htmlResets = 0;
    }
}

function pauseTimer(){
    if(timerOn == 1){
        clearInterval(counter);
        timerOn = 0;
    }

    if(htmlResets == 1){
        hoursLabel.innerHTML ="00";
        minutesLabel.innerHTML = "00";
        secondsLabel.innerHTML = "00";
        totalMills = 0;
        totalSeconds = 0;
        totalMinutes = 0;
        totalHours = 0;
    }
    else{
        htmlResets = 1;
    }
}

function stopTimer(){
    totalTime.innerHTML = "Time Recorded :" +  hoursLabel.innerHTML + ":" + minutesLabel.innerHTML + ":" + secondsLabel.innerHTML;
    hoursLabel.innerHTML= "00";
    minutesLabel.innerHTML = "00";
    secondsLabel.innerHTML = "00";
    totalMills = 0;
    totalSeconds = 0;
    totalHours = 0;
    clearInterval(counter);
    timerOn = 0 ;

}

function setTime(){
    ++totalMills;
    if (totalHours == 99 & totalMinutes == 59 & totalSeconds == 60) {

        totalHours = 0;

        totalMinutes = 0;

        totalSeconds = 0;

        hoursLabel.innerHTML = "00";

        minutesLabel.innerHTML = "00";

        secondsLabel.innerHTML ="00";

        clearInterval(counter);

    }
    if (totalMills == 100){
        totalSeconds++;
        secondsLabel.innerHTML = pad(totalSeconds % 60);
        totalMills = 0;
    }
    if (totalSeconds == 60) {

        totalMinutes++;

        minutesLabel.innerHTML = pad(totalMinutes % 60);

        totalSeconds = 0;

    }
    if (totalMinutes == 60) {

        totalHours++;

        hoursLabel.innerHTML = pad(totalHours);

        totalMinutes = 0;

    }

    function pad(val) {

        var valString = val + "";

        if (valString.length < 2) {

            return "0" + valString;

        }

        else {

            return valString;

        }

    }


}