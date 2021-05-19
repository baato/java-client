package com.baato.baatolibrary.navigation;

import com.graphhopper.util.Helper;

import java.util.Locale;

public class InitialVoiceInstructionConfig extends VoiceInstructionConfig {

    // The instruction should not be spoken straight away, but wait until the user merged on the new road and can listen to instructions again
    private final int distanceDelay; // delay distance in meter
    private final int distanceForInitialStayInstruction; // min distance in meter for initial instruction
    private final DistanceUtils.Unit unit;
    private final BaatoTranslationMap translationMap;

    public InitialVoiceInstructionConfig(String key, BaatoTranslationMap translationMap, BaatoTranslationMap navigateResponseConverterTranslationMap, Locale locale, int distanceForInitialStayInstruction, int distanceDelay, DistanceUtils.Unit unit) {
        super(key, navigateResponseConverterTranslationMap, locale);
        this.distanceForInitialStayInstruction = distanceForInitialStayInstruction;
        this.distanceDelay = distanceDelay;
        this.unit = unit;
        this.translationMap = translationMap;
    }

    private int distanceAlongGeometry(double distanceMeter) {
        // Cast to full units
        int tmpDistance = (int) (distanceMeter - distanceDelay);
        if (unit == DistanceUtils.Unit.METRIC) {
            return (tmpDistance / 1000) * 1000;
        } else {
            tmpDistance = (int) (tmpDistance * meterToMiles);
            return (int) Math.ceil(tmpDistance / meterToMiles);
        }
    }

    private int distanceVoiceValue(double distanceInMeter) {
        if (unit == DistanceUtils.Unit.METRIC) {
            return (int) (distanceAlongGeometry(distanceInMeter) * meterToKilometer);
        } else {
            return (int) (distanceAlongGeometry(distanceInMeter) * meterToMiles);
        }
    }

    @Override
    public VoiceInstructionValue getConfigForDistance(double distance, String turnDescription, String thenVoiceInstruction) {
        if (distance > distanceForInitialStayInstruction) {
            int spokenDistance = distanceAlongGeometry(distance);
            int distanceVoiceValue = distanceVoiceValue(distance);
            String continueDescription = translationMap.getWithFallBack(locale).tr("continue") + " " + navigateResponseConverterTranslationMap.getWithFallBack(locale).tr(key, distanceVoiceValue);
            continueDescription = Helper.firstBig(continueDescription);
            return new VoiceInstructionValue(spokenDistance, continueDescription);
        }
        return null;
    }
}