let countdown;
const timerDisplay = document.querySelector('.display__time-left');
const endTime = document.querySelector('.display__end-time');
const buttons = document.querySelectorAll('[data-time]');
let interval = 0;
let counter = 0;
var longbreak = false;
const mins = document.getElementById('minutes').innerHTML;
let breakSwitch = 'break';
let endSwitch = 'run';
const minsS = document.getElementById('shortBreak').innerHTML;
const minsL = document.getElementById('longBreak').innerHTML;
/*const minsS = 3;
const minsL = 3;*/





function timer(seconds) {

    // clear any existing timers
    clearInterval(countdown);

    const now = Date.now();
    const then = now + seconds * 1000;
    displayTimeLeft(seconds);
    displayEndTime(then);

    countdown = setInterval(() => {
        const secondsLeft = Math.round((then - Date.now()) / 1000);
        // check if we should stop it!
        if(secondsLeft <= 0) {
            if(endSwitch==='end'){
                endTime.textContent= `End !`;
                displayTimeLeft(0);
                clearInterval(countdown);
                return;
            }

            if(counter===3){
                longbreak= true;
                timer(minsL*60);
                endTime.textContent = 'Long Break!';
                endSwitch = 'end';

        }else {
                //SHORT BREAK
                if(breakSwitch==='Study'){
                    counter++;
                    timer(mins*60);
                    displayEndTime(then);
                    breakSwitch='break';
                }
                else{

                    timer(minsS*60);
                    endTime.textContent = `Time for a short break!`;
                    breakSwitch='Study';
                }



            }

        } /*clearInterval(countdown);
        endTime.textContent= `End !`;
        displayTimeLeft(0);
        return;*/


        // display it
        displayTimeLeft(secondsLeft);
    }, 1000);
}


function displayTimeLeft(seconds) {
    const minutes = Math.floor(seconds / 60);
    const remainderSeconds = seconds % 60;
    const display = `${minutes}:${remainderSeconds < 10 ? '0' : '' }${remainderSeconds}`;
    document.title = display;
    timerDisplay.textContent = display;
}

function displayEndTime(timestamp) {
    const end = new Date(timestamp);
    const hour = end.getHours();
    const minutes = end.getMinutes();
    endTime.textContent= `Study time. Ends at ${hour}:${minutes < 10 ? '0' : '' }${minutes}`};


function startTimer () {
    if (!interval) {
        const seconds = parseInt(this.dataset.time);
        timer(seconds);
    }
}

function stop () {
    clearInterval(countdown);
    interval = 0;
}


buttons.forEach(button => button.addEventListener('click',startTimer));


//button
document.getElementById('test').addEventListener('click', function(e) {
    /////HERE
    counter=0;
    endSwitch='run';
    timer(mins *60);
});
