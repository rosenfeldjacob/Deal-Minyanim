package com.tbdev.teaneckminyanim.admin.structure.minyan;
import com.kosherjava.zmanim.util.Time;
import com.tbdev.teaneckminyanim.global.Zman;

import java.time.LocalDate;

public class MinyanTime {
    private Time time;
    private TimeRule rule; 

    public MinyanTime() {
        return;
    }

    public MinyanTime(String rawTime) {
        if (rawTime == null || rawTime.isEmpty()) {
            return;
        } else if (rawTime.equals("INVALID")) {
            throw new IllegalArgumentException("Invalid time");
        } else if (rawTime.startsWith("T")) {
            String[] parts = rawTime.substring(1).split(":");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid time");
            }
            time = new Time(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        } else if (rawTime.startsWith("R")) { //Example RSHEKIYA:10 - this is Dynamic, for shekiya zman, with offset of 10
            String[] parts = rawTime.substring(1).split(":"); 
//          TODO: FIX valueOf
            if (parts.length == 2) {
                rule = new TimeRule(Zman.fromString(parts[0]), Integer.parseInt(parts[1]));
            } else {
                System.out.println("Invalid time rule: " + rawTime);
                return;
            }
        } else if (rawTime.startsWith("Q")) {
            String[] parts = rawTime.substring(1).split(":");
//          TODO: FIX valueOf
            if (parts.length == 2) {
                rule = new TimeRule(Zman.fromString(parts[0]), Integer.parseInt(parts[1]), true);
            } else {
                System.out.println("Invalid time rule: " + rawTime);
                return;
            }
        } else if (rawTime.equalsIgnoreCase("NM")) {
            return;
        } else {
            System.out.println("Invalid time: " + rawTime);
            return;
        }
    }

    public MinyanTime(Time time) {
        this.time = time;
        this.rule = null;
    }

    public MinyanTime(TimeRule rule) {
        this.rule = rule;
        this.time = null;
    }

//    computed property
    public TimeType type() {
        if (time != null) {
            return TimeType.FIXED;
        } else if (rule != null && !rule.rounded) { //made TimeRule.rounded protected instead of private as a "hack" here- not sure it need to be private though
            return TimeType.DYNAMIC;
        } else if (rule != null) {
            return TimeType.ROUNDED; 
        } else {
            return TimeType.NONE;
        }
    }

//    functions used for HTML/thymeleaf
    public boolean isFixed() {
        return type() == TimeType.FIXED;
    }

    public boolean isDynamic() {
        return type() == TimeType.DYNAMIC;
    }

    public boolean isRounded() {
        return type() == TimeType.ROUNDED;
    }

    public boolean isNone() {
        return type() == TimeType.NONE;
    }

    enum TimeType {
        NONE,
        FIXED,
        DYNAMIC,
        ROUNDED;

        public static TimeType fromString(String s) {
            if (s == null) {
                return null;
            }
            switch (s.toLowerCase()) {
                case "fixed":
                    return FIXED;
                case "dynamic":
                    return DYNAMIC;
                case "rounded":
                    return ROUNDED;
                case "nm":
                    return NONE;
                default:
                    return null;
            }
        }
    }

    public static MinyanTime fromFormData(String timeTypeString, String fixedTimeString, String zmanString, Integer zmanOffset) {
        TimeType timeType = TimeType.fromString(timeTypeString);

        switch (timeType) {
            case FIXED:
                if (fixedTimeString == null || fixedTimeString.isEmpty()) {
                    return null;
                } else {
                    String[] components = fixedTimeString.split(":");

                    if (components.length != 2) {
                        return null;
                    }
                    return new MinyanTime(new Time(Integer.parseInt(components[0]), Integer.parseInt(components[1]), 0, 0));
                }
            case DYNAMIC:
//                confirm zmanString and zmanOffsetString are not null
                if (zmanString == null || zmanString.isEmpty()) {
                    return null;
                } else {
                    Zman zman = Zman.fromString(zmanString);
                    TimeRule rule = new TimeRule(zman, zmanOffset);
                    return new MinyanTime(rule);
                }
            case ROUNDED: 
                if (zmanString == null || zmanString.isEmpty()) {
                    return null;
                } else {
                    Zman zman = Zman.fromString(zmanString);
                    Boolean rounded= true; 
                    TimeRule rule = new TimeRule(zman, zmanOffset, rounded);
                    return new MinyanTime(rule);
                }
            case NONE:
                return new MinyanTime();
            default:
                return null;
        }
    }





    @Override
    public String toString() {
        TimeType t = type();
        if (time != null && rule != null) {
            return "INVALID";
        } else if (t == TimeType.FIXED) {
            return String.format("T%d:%d:%d:%d", time.getHours(), time.getMinutes(), time.getSeconds(), time.getMilliseconds());
        } else if (t == TimeType.DYNAMIC) {
            return String.format("R%s:%d", rule.getZman(), rule.getOffsetMinutes());
        } else if (t == TimeType.ROUNDED) {
            return String.format("Q%s:%d", rule.getZman(), rule.getOffsetMinutes());
        } else if (t == TimeType.NONE) {
            return "NM";
        } else {
            return "INVALID";
        }
    }

    public String displayTime() {
        TimeType t = type();
        if (t == TimeType.NONE) {
            return "";
        } else if (time != null && rule != null) {
            return "INVALID";
        } else if (t == TimeType.FIXED) {
            String timeString = time.toString();
            String[] parts = timeString.split(":");

            if (parts.length != 3) {
                return "INVALID";
            }

            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            double seconds = Double.parseDouble(parts[2]);
            boolean isPM = hours >= 12;

            if (seconds > 30) {
                minutes += 1;
            }

            if (minutes == 60) {
                hours += 1;
                minutes -= 60;
            }

            if (isPM) {
                hours -= 12;
            }

            String hoursString = String.valueOf(hours);
            String minutesString = minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
            String amPmString = isPM ? "PM" : "AM";

            return hoursString + ":" + minutesString + " " + amPmString;

//            int indexOfDot = timeString.indexOf(".");
//            return timeString.substring(0, indexOfDot);
//            return String.format("%s:%s:%s", time.getHours(), time.getMinutes(), time.getSeconds());
        } else if (t == TimeType.DYNAMIC) {
//            return "Dynamic";
            if (rule.getOffsetMinutes() < 0) {
                return String.format("%s minus %d minutes", rule.getZman().displayName(), Math.abs(rule.getOffsetMinutes()));
            } else if (rule.getOffsetMinutes() == 0) {
                return rule.getZman().displayName();
            }  else if (rule.getOffsetMinutes() > 0) {
                return String.format("%s plus %d minutes", rule.getZman().displayName(), rule.getOffsetMinutes());
            } else {
                return "INVALID";
            }
        } else if (t == TimeType.ROUNDED) {
            //            return "Rounded";
                        if (rule.getOffsetMinutes() < 0) {
                            return String.format("Approx. %s minus %d minutes", rule.getZman().displayName(), Math.abs(rule.getOffsetMinutes()));
                        } else if (rule.getOffsetMinutes() == 0) {
                            return String.format("Approx. %s", rule.getZman().displayName());
                        }  else if (rule.getOffsetMinutes() > 0) {
                            return String.format("Approx. %s plus %d minutes", rule.getZman().displayName(), rule.getOffsetMinutes());
                        } else {
                            return "INVALID";
                        }
                    }
                    else {
            return "INVALID";
        }
    }

    /**
     * @return Expression of the fixed time, if there is one, in 24 hours:60 minutes format
     */
    public String getFixedTimeShort() {
        if (type() == TimeType.FIXED) {
//            get 24 hour time
            int hours = time.getHours();
            int minutes = time.getMinutes();
            String hoursString = hours < 10 ? "0" + String.valueOf(hours) : String.valueOf(hours);
            String minutesString = minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes);

            return hoursString + ":" + minutesString;
        } else {
            return null;
        }
    }

    public String dynamicDisplayName() {
        if (isDynamic()) {
            if (rule.getOffsetMinutes() < 0) {
                return String.format("%d minutes before %s", Math.abs(rule.getOffsetMinutes()), rule.getZman().displayName().toLowerCase());
            } else if (rule.getOffsetMinutes() == 0) {
                return rule.getZman().displayName();
            }  else if (rule.getOffsetMinutes() > 0) {
                return String.format("%d minutes after %s", rule.getOffsetMinutes(), rule.getZman().displayName().toLowerCase());
            } else {
                return "INVALID";
            }
        } else {
            return null;
        }
    }
    public String roundedDisplayName() {
        if (isRounded()) {
            if (rule.getOffsetMinutes() < 0) {
                return String.format("Approx. %d minutes before %s", Math.abs(rule.getOffsetMinutes()), rule.getZman().displayName().toLowerCase());
            } else if (rule.getOffsetMinutes() == 0) {
                return String.format("%s rounded", rule.getZman().displayName());
            }  else if (rule.getOffsetMinutes() > 0) {
                return String.format("Approx. %d minutes after %s", rule.getOffsetMinutes(), rule.getZman().displayName().toLowerCase());
            } else {
                return "INVALID";
            }
        } else {
            return null;
        }
    }

    public TimeRule getRule() {
        return rule;
    }

    public Time getTime() {
        return time;
    }

    public Time getTime(LocalDate date) {
        if (type() == TimeType.FIXED) {
            return time;
        } else if (type() == TimeType.DYNAMIC) {
            return rule.getTime(date);
        } else if (type() == TimeType.ROUNDED) {
            return rule.getTime(date);
        } else {
            return null;
        }
    }
}
