export class StopWatch {

    minutes = 0;
    seconds = 0;
    microseconds = 0;
    isInProgress = false;
    intervalId;

    constructor($stopwatch) {
        this.$stopwatch = $stopwatch;
        this.$minutes = $stopwatch.find("#minutes");
        this.$seconds = $stopwatch.find("#seconds");
        this.$microseconds = $stopwatch.find("#microseconds");
    }

    start() {
        this.intervalId = setInterval(this.count, 10);
    }

    count = () => {
        this.microseconds++;

        if (this.microseconds !== 100) {
            this.$microseconds.html(String(this.microseconds).padStart(2, '0'));
            return;
        }

        this.microseconds = 0;
        this.seconds++;

        if (this.seconds !== 60) {
            this.$microseconds.html(String(this.microseconds).padStart(2, '0'));
            this.$seconds.html(String(this.seconds).padStart(2, '0'));
            return;
        }

        this.seconds = 0;
        this.minutes++;

        this.$microseconds.html(String(this.microseconds).padStart(2, '0'));
        this.$seconds.html(String(this.seconds).padStart(2, '0'));
        this.$minutes.html(this.minutes);
    }

    end() {
        clearInterval(this.intervalId);
    }

    reset() {
        this.microseconds = 0;
        this.seconds = 0;
        this.minutes = 0;

        this.$microseconds.html(String(this.microseconds).padStart(2, '0'));
        this.$seconds.html(String(this.seconds).padStart(2, '0'));
        this.$minutes.html(this.minutes);

        this.showTime();
    }

    addTwoSeconds() {
        this.seconds += 2;
        this.$seconds.html(String(this.seconds).padStart(2, '0'));
    }

    subtractTwoSeconds() {
        this.seconds -= 2;
        this.$seconds.html(String(this.seconds).padStart(2, '0'));
    }

    showDNF() {
        this.$stopwatch.html("DNF");
    }

    showTime() {
        this.$stopwatch.html("");
        this.$stopwatch.append(this.$minutes, ":", this.$seconds, ".", this.$microseconds);
    }

    getTime() {
        return this.minutes + ":" + String(this.seconds).padStart(2, '0') + "." + String(this.microseconds).padStart(2, '0');
    }
}