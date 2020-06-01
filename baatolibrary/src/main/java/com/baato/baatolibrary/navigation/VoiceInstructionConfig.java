package com.baato.baatolibrary.navigation;

import com.graphhopper.util.TranslationMap;

import java.util.Locale;

abstract class VoiceInstructionConfig extends DistanceUtils {
    protected final String key; // TranslationMap key
    protected final TranslationMap navigateResponseConverterTranslationMap;
    protected Locale locale = Locale.ENGLISH;

    public VoiceInstructionConfig(String key, TranslationMap navigateResponseConverterTranslationMap, Locale locale) {
        this.key = key;
        this.navigateResponseConverterTranslationMap = navigateResponseConverterTranslationMap;
        this.locale = locale;
    }

    class VoiceInstructionValue {
        final int spokenDistance;
        final String turnDescription;

        public VoiceInstructionValue(int spokenDistance, String turnDescription) {
            this.spokenDistance = spokenDistance;
            this.turnDescription = turnDescription;
        }
    }

    public abstract VoiceInstructionValue getConfigForDistance(
            double distance,
            String turnDescription,
            String thenVoiceInstruction);
}


