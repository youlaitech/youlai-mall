// AMD support (Thanks to @FagnerMartinsBrack)
;(function(factory) {
    'use strict';

    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else {
        factory(jQuery);
    }
})(function($){
    'use strict';

    var instances = [],
        matchers  = [],
        defaultOptions  = {
            precision: 100, // 0.1 seconds, used to update the DOM
            elapse: false,
            defer: false
        };
    // Miliseconds
    matchers.push(/^[0-9]*$/.source);
    // Month/Day/Year [hours:minutes:seconds]
    matchers.push(/([0-9]{1,2}\/){2}[0-9]{4}( [0-9]{1,2}(:[0-9]{2}){2})?/
        .source);
    // Year/Day/Month [hours:minutes:seconds] and
    // Year-Day-Month [hours:minutes:seconds]
    matchers.push(/[0-9]{4}([\/\-][0-9]{1,2}){2}( [0-9]{1,2}(:[0-9]{2}){2})?/
        .source);
    // Cast the matchers to a regular expression object
    matchers = new RegExp(matchers.join('|'));
    // Parse a Date formatted has String to a native object
    function parseDateString(dateString) {
        // Pass through when a native object is sent
        if(dateString instanceof Date) {
            return dateString;
        }
        // Caste string to date object
        if(String(dateString).match(matchers)) {
            // If looks like a milisecond value cast to number before
            // final casting (Thanks to @msigley)
            if(String(dateString).match(/^[0-9]*$/)) {
                dateString = Number(dateString);
            }
            // Replace dashes to slashes
            if(String(dateString).match(/\-/)) {
                dateString = String(dateString).replace(/\-/g, '/');
            }
            return new Date(dateString);
        } else {
            throw new Error('Couldn\'t cast `' + dateString +
                '` to a date object.');
        }
    }
    // Map to convert from a directive to offset object property
    var DIRECTIVE_KEY_MAP = {
        'Y': 'years',
        'm': 'months',
        'n': 'daysToMonth',
        'd': 'daysToWeek',
        'w': 'weeks',
        'W': 'weeksToMonth',
        'H': 'hours',
        'M': 'minutes',
        'S': 'seconds',
        'D': 'totalDays',
        'I': 'totalHours',
        'N': 'totalMinutes',
        'T': 'totalSeconds'
    };
    // Returns an escaped regexp from the string
    function escapedRegExp(str) {
        var sanitize = str.toString().replace(/([.?*+^$[\]\\(){}|-])/g, '\\$1');
        return new RegExp(sanitize);
    }
    // Time string formatter
    function strftime(offsetObject) {
        return function(format) {
            var directives = format.match(/%(-|!)?[A-Z]{1}(:[^;]+;)?/gi);
            if(directives) {
                for(var i = 0, len = directives.length; i < len; ++i) {
                    var directive   = directives[i]
                            .match(/%(-|!)?([a-zA-Z]{1})(:[^;]+;)?/),
                        regexp    = escapedRegExp(directive[0]),
                        modifier  = directive[1] || '',
                        plural    = directive[3] || '',
                        value     = null;
                    // Get the key
                    directive = directive[2];
                    // Swap shot-versions directives
                    if(DIRECTIVE_KEY_MAP.hasOwnProperty(directive)) {
                        value = DIRECTIVE_KEY_MAP[directive];
                        value = Number(offsetObject[value]);
                    }
                    if(value !== null) {
                        // Pluralize
                        if(modifier === '!') {
                            value = pluralize(plural, value);
                        }
                        // Add zero-padding
                        if(modifier === '') {
                            if(value < 10) {
                                value = '0' + value.toString();
                            }
                        }
                        // Replace the directive
                        format = format.replace(regexp, value.toString());
                    }
                }
            }
            format = format.replace(/%%/, '%');
            return format;
        };
    }
    // Pluralize
    function pluralize(format, count) {
        var plural = 's', singular = '';
        if(format) {
            format = format.replace(/(:|;|\s)/gi, '').split(/\,/);
            if(format.length === 1) {
                plural = format[0];
            } else {
                singular = format[0];
                plural = format[1];
            }
        }
        // Fix #187
        if(Math.abs(count) > 1) {
            return plural;
        } else {
            return singular;
        }
    }
    // The Final Countdown
    var Countdown = function(el, finalDate, options) {
        this.el       = el;
        this.$el      = $(el);
        this.interval = null;
        this.offset   = {};
        this.options  = $.extend({}, defaultOptions);
        // console.log(this.options);
        // This helper variable is necessary to mimick the previous check for an
        // event listener on this.$el. Because of the event loop there might not
        // be a registered event listener during the first tick. In order to work
        // as expected a second tick is necessary, so that the events can be fired
        // and handled properly.
        this.firstTick = true;
        // Register this instance
        this.instanceNumber = instances.length;
        instances.push(this);
        // Save the reference
        this.$el.data('countdown-instance', this.instanceNumber);
        // Handle options or callback
        if (options) {
            // Register the callbacks when supplied
            if(typeof options === 'function') {
                this.$el.on('update.countdown', options);
                this.$el.on('stoped.countdown', options);
                this.$el.on('finish.countdown', options);
            } else {
                this.options = $.extend({}, defaultOptions, options);
            }
        }
        // Set the final date and start
        this.setFinalDate(finalDate);
        // Starts the countdown automatically unless it's defered,
        // Issue #198
        if (this.options.defer === false) {
            this.start();
        }
    };
    $.extend(Countdown.prototype, {
        start: function() {
            if(this.interval !== null) {
                clearInterval(this.interval);
            }
            var self = this;
            this.update();
            this.interval = setInterval(function() {
                self.update.call(self);
            }, this.options.precision);
        },
        stop: function() {
            clearInterval(this.interval);
            this.interval = null;
            this.dispatchEvent('stoped');
        },
        toggle: function() {
            if (this.interval) {
                this.stop();
            } else {
                this.start();
            }
        },
        pause: function() {
            this.stop();
        },
        resume: function() {
            this.start();
        },
        remove: function() {
            this.stop.call(this);
            instances[this.instanceNumber] = null;
            // Reset the countdown instance under data attr (Thanks to @assiotis)
            delete this.$el.data().countdownInstance;
        },
        setFinalDate: function(value) {
            this.finalDate = parseDateString(value); // Cast the given date
        },
        update: function() {
            // Stop if dom is not in the html (Thanks to @dleavitt)
            if(this.$el.closest('html').length === 0) {
                this.remove();
                return;
            }
            var now = new Date(),
                newTotalSecsLeft;
            // Create an offset date object
            newTotalSecsLeft = this.finalDate.getTime() - now.getTime(); // Millisecs
            // Calculate the remaining time
            newTotalSecsLeft = Math.ceil(newTotalSecsLeft / 1000); // Secs
            // If is not have to elapse set the finish
            newTotalSecsLeft = !this.options.elapse && newTotalSecsLeft < 0 ? 0 :
                Math.abs(newTotalSecsLeft);
            // Do not proceed to calculation if the seconds have not changed or
            // during the first tick
            if (this.totalSecsLeft === newTotalSecsLeft || this.firstTick) {
                this.firstTick = false;
                return;
            } else {
                this.totalSecsLeft = newTotalSecsLeft;
            }
            // Check if the countdown has elapsed
            this.elapsed = (now >= this.finalDate);
            // Calculate the offsets
            this.offset = {
                seconds     : this.totalSecsLeft % 60,
                minutes     : Math.floor(this.totalSecsLeft / 60) % 60,
                hours       : Math.floor(this.totalSecsLeft / 60 / 60) % 24,
                days        : Math.floor(this.totalSecsLeft / 60 / 60 / 24) % 7,
                daysToWeek  : Math.floor(this.totalSecsLeft / 60 / 60 / 24) % 7,
                daysToMonth : Math.floor(this.totalSecsLeft / 60 / 60 / 24 % 30.4368),
                weeks       : Math.floor(this.totalSecsLeft / 60 / 60 / 24 / 7),
                weeksToMonth: Math.floor(this.totalSecsLeft / 60 / 60 / 24 / 7) % 4,
                months      : Math.floor(this.totalSecsLeft / 60 / 60 / 24 / 30.4368),
                years       : Math.abs(this.finalDate.getFullYear()-now.getFullYear()),
                totalDays   : Math.floor(this.totalSecsLeft / 60 / 60 / 24),
                totalHours  : Math.floor(this.totalSecsLeft / 60 / 60),
                totalMinutes: Math.floor(this.totalSecsLeft / 60),
                totalSeconds: this.totalSecsLeft
            };
            // Dispatch an event
            if(!this.options.elapse && this.totalSecsLeft === 0) {
                this.stop();
                this.dispatchEvent('finish');
            } else {
                this.dispatchEvent('update');
            }
        },
        dispatchEvent: function(eventName) {
            var event = $.Event(eventName + '.countdown');
            event.finalDate     = this.finalDate;
            event.elapsed       = this.elapsed;
            event.offset        = $.extend({}, this.offset);
            event.strftime      = strftime(this.offset);
            this.$el.trigger(event);
        }
    });
    // Register the jQuery selector actions
    $.fn.countdown = function() {
        var argumentsArray = Array.prototype.slice.call(arguments, 0);
        return this.each(function() {
            // If no data was set, jQuery.data returns undefined
            var instanceNumber = $(this).data('countdown-instance');
            // Verify if we already have a countdown for this node ...
            // Fix issue #22 (Thanks to @romanbsd)
            if (instanceNumber !== undefined) {
                var instance = instances[instanceNumber],
                    method = argumentsArray[0];
                // If method exists in the prototype execute
                if(Countdown.prototype.hasOwnProperty(method)) {
                    instance[method].apply(instance, argumentsArray.slice(1));
                    // If method look like a date try to set a new final date
                } else if(String(method).match(/^[$A-Z_][0-9A-Z_$]*$/i) === null) {
                    instance.setFinalDate.call(instance, method);
                    // Allow plugin to restart after finished
                    // Fix issue #38 (thanks to @yaoazhen)
                    instance.start();
                } else {
                    $.error('Method %s does not exist on jQuery.countdown'
                        .replace(/\%s/gi, method));
                }
            } else {
                // ... if not we create an instance
                new Countdown(this, argumentsArray[0], argumentsArray[1]);
            }
        });
    };
});