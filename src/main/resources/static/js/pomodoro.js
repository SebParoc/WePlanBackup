var minutes = 25;
var seconds = "00";
seconds= localStorage.getItem("secondsvalue");
var click = new Audio("src/main/resources/static/sounds/click.mp3");
var bell = new Audio("static/sounds/bell.mp3");
var totalTime = document.getElementById("totalTime");

var minutes_interval;
var seconds_interval;

var totalSeconds = 0;
var totalMinutes = 0;
var totalHours = 0;

var pause;
var counter;
var timerOn;
var htmlResets;
var totalMills = 0;

function template(){
    document.getElementById("minutes").innerHTML = minutes;
   seconds = localStorage.getItem("secondsvalue");


}

function start() {
    document.getElementById('pause').removeAttribute('disabled');

    timerOn = 1;
    htmlResets = 0;

    click.play();

    minutes = minutes - 1;
    seconds = 59;

    document.getElementById("minutes").innerHTML = minutes;
    seconds = localStorage.getItem("secondsvalue") ;

    minutes_interval = setInterval(minutesTimer, 60000);
    seconds_interval = setInterval(secondsTimer, 1000);

    function minutesTimer() {

        minutes = minutes - 1;
        document.getElementById("minutes").innerHTML = minutes;
    }

    function secondsTimer() {
        seconds = seconds - 1;
        document.getElementById("minutes").innerHTML = localStorage.getItem("secondsvalue");

        if (seconds <= 0) {
            if (minutes <= 0) {
                minutes = 5;
                document.getElementById("minutes").innerHTML = minutes;
                seconds = 60;

                document.getElementById("done").classList.add("show_message");


                document.getElementById("done").innerHTML =
                    "5 minutes break!";


            }
        }
    }

            seconds = 60;
    end();
    }


function pause() {
    document.getElementById("start").setAttribute('disabled', 'disabled');

    document.getElementById('resume').removeAttribute('disabled');
    pause = true;
    if (timerOn === 1) {
        clearInterval(minutes_interval);
        clearInterval(seconds_interval);
        timerOn = 0;
    }
    if (htmlResets === 1) {
        minutesLabel.innerHTML = "00";
        secondsLabel.innerHTML = "00";
        totalSeconds = 0;
        totalMinutes = 0;
    } else {
        htmlResets = 1;
    }

}


    function start2() {
        pause = true;
        timerOn = 1;
        htmlResets = 0;
        seconds = seconds - 1;
        document.getElementById("seconds").innerHTML = seconds;

        minutes_interval = setInterval(minutesTimer, 60000);

        seconds_interval = setInterval(secondsTimer, 1000);

        function minutesTimer() {

            minutes = minutes - 1;
            document.getElementById("minutes").innerHTML = minutes;
        }

        function secondsTimer() {

            seconds = seconds - 1;
            document.getElementById("seconds").innerHTML = seconds;

            if(seconds <= 0){
                if(minutes <=0){
                    clearInterval(minutes_interval);
                    clearInterval(seconds_interval);

                    document.getElementById("done").innerHTML =
                        "Session Completed! Take a break.";

                    document.getElementById("done").classList.add("show_message");
                    bell.play();

                }
                minutes = 5;
                seconds = 60;

            }

        }
    }
    
    //TODO
//buttons
    function end() {
        setInterval(function ()
        {
            clearInterval(minutes_interval);
            clearInterval(seconds_interval);

            // Add the message to the html
            document.getElementById("done").innerHTML =
                "Session Completed!! Take a Break";

            // Make the html message div visible
            document.getElementById("done").classList.add("show_message");
        },120000);
    }

