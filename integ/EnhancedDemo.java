public class EnhancedDemo {
    public static void main(String[] args) {
        System.out.println("Enhanced Modular Media Streaming Suite - Demo");
        System.out.println("=============================================");

        // Test different media sources
        System.out.println("\n--- Testing Multiple Media Sources ---");
        
        // Local files
        MediaSource localSource = new LocalFileSource();
        PlaybackController localController = new PlaybackController(localSource, new SoftwareRenderer());
        localController.play("local_video");
        
        // HLS streams
        MediaSource hlsSource = new HLSStreamSource("https://stream.example.com");
        PlaybackController hlsController = new PlaybackController(hlsSource, new HardwareRenderer());
        hlsController.play("live_stream");
        
        // Remote API
        MediaSource apiSource = new RemoteAPISource("https://api.media.com");
        PlaybackController apiController = new PlaybackController(apiSource, new SoftwareRenderer());
        apiController.play("api_media_123");
        
        System.out.println("\n--- Testing On-the-fly Feature Plugins ---");
        
        // Audio Equalizer
        AudioEqualizerDecorator eqDecorator = new AudioEqualizerDecorator(new HardwareRenderer());
        eqDecorator.setEQLevel(0, 3);  // Boost bass
        eqDecorator.setEQLevel(4, -2); // Cut midrange
        eqDecorator.setEQLevel(8, 1);  // Slight treble boost
        
        PlaybackController eqController = new PlaybackController(localSource, eqDecorator);
        eqController.play("music_track");
        
        // Watermarking
        WatermarkingDecorator watermarkDecorator = new WatermarkingDecorator(
            new SoftwareRenderer(), "© 2024 MMS Demo", "bottom-right");
        PlaybackController watermarkController = new PlaybackController(localSource, watermarkDecorator);
        watermarkController.play("demo_video");
        
        System.out.println("\n--- Testing Composite Playlists ---");
        
        // Create sub-playlists
        CompositePlaylist rockPlaylist = new CompositePlaylist("Rock Collection");
        rockPlaylist.addMedia("rock_song_1");
        rockPlaylist.addMedia("rock_song_2");
        
        CompositePlaylist jazzPlaylist = new CompositePlaylist("Jazz Collection");
        jazzPlaylist.addMedia("jazz_song_1");
        jazzPlaylist.addMedia("jazz_song_2");
        
        // Create main playlist containing sub-playlists
        CompositePlaylist mainPlaylist = new CompositePlaylist("Music Library");
        mainPlaylist.addPlaylist(rockPlaylist);
        mainPlaylist.addPlaylist(jazzPlaylist);
        mainPlaylist.addMedia("classical_piece");
        
        System.out.println("Main playlist contains " + mainPlaylist.getTotalMediaCount() + " total media items");
        System.out.println("All media IDs: " + mainPlaylist.getAllMediaIds());
        
        // Play from composite playlist
        PlaybackController compositeController = new PlaybackController(localSource, new SoftwareRenderer());
        for (String mediaId : mainPlaylist.getAllMediaIds()) {
            compositeController.play(mediaId);
        }
        
        System.out.println("\n--- Testing Remote-proxying Cache Streams ---");
        
        // Create caching media source
        CachingMediaSource cachingSource = new CachingMediaSource(apiSource, 5);
        PlaybackController cacheController = new PlaybackController(cachingSource, new HardwareRenderer());
        
        // First play (cache miss)
        cacheController.play("remote_media_1");
        
        // Second play (cache hit)
        cacheController.play("remote_media_1");
        
        // Test cache with different media
        cacheController.play("remote_media_2");
        cacheController.play("remote_media_3");
        
        System.out.println("Cache size: " + cachingSource.getCacheSize());
        System.out.println("Is 'remote_media_1' cached? " + cachingSource.isCached("remote_media_1"));
        
        System.out.println("\n--- Testing Runtime Rendering Strategy Switching ---");
        
        // Start with software renderer
        PlaybackController switchController = new PlaybackController(localSource, new SoftwareRenderer());
        switchController.play("test_media");
        
        // Switch to hardware renderer with multiple decorators
        Renderer complexRenderer = new WatermarkingDecorator(
            new AudioEqualizerDecorator(
                new SubtitleDecorator(
                    new HardwareRenderer()
                )
            ), "Complex Demo", "top-left"
        );
        
        switchController.setRenderer(complexRenderer);
        switchController.play("test_media");
        
        System.out.println("\nEnhanced Demo Complete!");
        System.out.println("=============================================");
    }
}
