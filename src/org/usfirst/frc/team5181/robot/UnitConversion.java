package org.usfirst.frc.team5181.robot;

public class UnitConversion {

    private double tc;
    private double c;

    /**
     * @param originalUnit  The original unit. Can be(not-case-sensitive): inches, feet,
     *                      yards, milimeters, centimeters, decimeters, meters
     * @param destUnit      The unit to convert to. Can be(not-case-sensitive): inches,
     *                      feet, yards, milimeters, centimeters, decimeters, meters
     * @param tobeConverted (double) the value to be converted
     * @return tc Converted double
     */

    public double unitConversion(String originalUnit, String destUnit, double tobeConverted) {
        switch (originalUnit.toLowerCase()) {
            case "inches":
                tc = inchesToFannggovitchUnits(tobeConverted);
                break;
            case "feet":
                tc = feetToFannggovitchUnits(tobeConverted);
                break;
            case "yards":
                tc = yardsToFannggovitchUnits(tobeConverted);
                break;
            case "milimeters":
                tc = mmToFannggovitchUnit(tobeConverted);
                break;
            case "centimeters":
                tc = cmToFannggovitchUnit(tobeConverted);
                break;
            case "decimeters":
                tc = dmToFannggovitchUnit(tobeConverted);
                break;
            case "meters":
                tc = mToFannggovitchUnit(tobeConverted);
                break;
            default:
                // DO NOTHING
        }

        switch (destUnit.toLowerCase()) {
            case "inches":
                c = fusToInches(tc);
                break;
            case "feet":
                c = fusToFeet(tc);
                break;
            case "yards":
                c = fusToYards(tc);
                break;
            case "milimeters":
                c = fuToMm(tc);
                break;
            case "centimeters":
                c = fuToCm(tc);
                break;
            case "decimeters":
                c = fuToDm(tc);
                break;
            case "meters":
                c = fuToM(tc);
                break;
            default:
                // DO NOTHING
        }

        return c;

    }

    private double inchesToFannggovitchUnits(double in) {
        return in * 25.4;
    }

    private double feetToFannggovitchUnits(double ft) {
        return ft * 304.8;
    }

    private double yardsToFannggovitchUnits(double yd) {
        return yd * 914.4;
    }

    private double mmToFannggovitchUnit(double milimeter) {
        return milimeter * 1;
    }

    private double cmToFannggovitchUnit(double centimeter) {
        return centimeter * 10;
    }

    private double dmToFannggovitchUnit(double decimeter) {
        return decimeter * 100;
    }

    private double mToFannggovitchUnit(double meter) {
        return meter * 1000;
    }

    // reverse

    private double fusToInches(double fu) {
        return fu / 25.4;
    }

    private double fusToFeet(double fu) {
        return fu / 304.8;
    }

    private double fusToYards(double fu) {
        return fu / 914.4;
    }

    private double fuToMm(double fu) {
        return fu / 1;
    }

    private double fuToCm(double fu) {
        return fu / 10;
    }

    private double fuToDm(double fu) {
        return fu / 100;
    }

    private double fuToM(double fu) {
        return fu / 1000;
    }
}
