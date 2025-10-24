class SubtitleDecorator extends RendererDecorator {
    public SubtitleDecorator(Renderer wrapped) {
        super(wrapped);
    }

    @Override
    public void render(Media media) {
        System.out.println("[SubtitleDecorator] Initializing subtitle track for " + media.getId());
        // Pre-processing
        wrapped.render(media);
        // Post-processing (simulated)
        System.out.println("[SubtitleDecorator] Displaying final subtitle credits for " + media.getId());
    }
}
