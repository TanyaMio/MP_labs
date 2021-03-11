package ua.kpi.comsys.lab1_2;

public class CoordinateTD {

    private Direction direction;
    private int degrees;
    private int minutes;
    private int seconds;

    public CoordinateTD (Direction d) {
        this.direction = d;
        this.degrees = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    public CoordinateTD (Direction d, int deg, int min, int sec) throws Exception {
        if (d == Direction.LATITUDE) {
            if (deg < -90 || deg > 90) throw new Exception("Invalid degrees value. Should be within [-90, 90] range.");
        } else if (deg < -180 || deg > 180) throw new Exception("Invalid degrees value. Should be within [-180, 180] range.");
        if (min < 0 || min > 59) throw new Exception("Invalid minutes value. Should be within [0, 59] range.");
        if (sec < 0 || sec > 59) throw new Exception("Invalid seconds value. Should be within [0, 59] range.");

        this.direction = d;
        this.degrees = deg;
        this.minutes = min;
        this.seconds = sec;
    }

    public String degreeStr() {
        String degStr = this.degrees + "°" + this.minutes + "'" + this.seconds + "\" ";
        if (this.direction == Direction.LATITUDE) {
            if (this.degrees > 0) degStr += "N";
            else if (this.degrees < 0) degStr += "S";
        } else {
            if (this.degrees > 0) degStr += "E";
            else if (this.degrees < 0) degStr += "W";
        }
        return degStr;
    }

    public String decimalString() {
        double decVal;
        if (this.degrees >= 0) decVal = this.degrees + (double)this.minutes / 60 + (double)this.seconds / 3600;
        else decVal = this.degrees - (double)this.minutes / 60 - (double)this.seconds / 3600;
        String decStr = decVal + "° ";
        if (this.direction == Direction.LATITUDE) {
            if (this.degrees > 0) decStr += "N";
            else if (this.degrees < 0) decStr += "S";
            else decStr += "N-S";
        } else {
            if (this.degrees > 0) decStr += "E";
            else if (this.degrees < 0) decStr += "W";
            else decStr += "E-W";
        }
        return decStr;
    }

    public CoordinateTD middleWith(CoordinateTD coordTD) {
        return middleTwo(this, coordTD);
    }

    public static CoordinateTD middleTwo(CoordinateTD coord1, CoordinateTD coord2) {
        CoordinateTD res = null;
        if (coord1.direction == coord2.direction) {
            double decVal1;
            if (coord1.degrees >= 0) decVal1 = coord1.degrees + (double)coord1.minutes / 60 + (double)coord1.seconds / 3600;
            else decVal1 = coord1.degrees - (double)coord1.minutes / 60 - (double)coord1.seconds / 3600;
            double decVal2;
            if (coord2.degrees >= 0) decVal2 = coord2.degrees + (double) (coord2.minutes / 60) + (double) (coord2.seconds / 3600);
            else decVal2 = coord2.degrees - (double) (coord2.minutes / 60) - (double) (coord2.seconds / 3600);
            CoordinateTD maxCoord, minCoord;
            if (decVal1 >= decVal2) {
                maxCoord = coord1;
                minCoord = coord2;
            } else {
                maxCoord = coord2;
                minCoord = coord1;
            }
            int newDeg = (maxCoord.degrees + minCoord.degrees)/2;
            int newMin = 0;
            int newSec = 0;
            if (minCoord.degrees < 0) {
                newSec = Math.abs(maxCoord.seconds - minCoord.seconds)/2 + Math.abs((maxCoord.minutes - minCoord.minutes)%2)*30;
                newMin = Math.abs(maxCoord.minutes - minCoord.minutes)/2 + Math.abs((maxCoord.degrees + minCoord.degrees)%2)*30;
            } else {
                newSec += (maxCoord.seconds + minCoord.seconds)/2 + ((maxCoord.minutes + minCoord.minutes)%2)*30;
                newMin += (maxCoord.minutes + minCoord.minutes)/2 + ((maxCoord.degrees + minCoord.degrees)%2)*30;
            }
            while (newSec > 59) {
                newMin += 1;
                newSec -= 60;
            }
            while (newMin > 59) {
                if (newDeg>=0) newDeg += 1;
                else newDeg -= 1;
                newMin -= 60;
            }
            try {
                res = new CoordinateTD(coord1.direction, newDeg, newMin, newSec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}

