class AudioEqualizerDecorator extends RendererDecorator {
    private final String[] frequencyBands = {"60Hz", "170Hz", "310Hz", "600Hz", "1kHz", "3kHz", "6kHz", "12kHz", "14kHz", "16kHz"};
    private final int[] eqLevels = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Default flat response
    
    public AudioEqualizerDecorator(Renderer wrapped) {
        super(wrapped);
    }
    
    public void setEQLevel(int band, int level) {
        if (band >= 0 && band < eqLevels.length) {
            eqLevels[band] = Math.max(-12, Math.min(12, level)); // Clamp between -12 and +12 dB
        }
    }
    
    public int getEQLevel(int band) {
        return (band >= 0 && band < eqLevels.length) ? eqLevels[band] : 0;
    }

    @Override
    public void render(Media media) {
        System.out.println("[AudioEqualizerDecorator] Applying EQ settings for " + media.getId());
        System.out.println("[AudioEqualizerDecorator] EQ Levels: " + java.util.Arrays.toString(eqLevels));
        
        // Pre-processing: Apply equalizer
        wrapped.render(media);
        
        // Post-processing: Reset EQ
        System.out.println("[AudioEqualizerDecorator] EQ processing complete for " + media.getId());
    }
}
