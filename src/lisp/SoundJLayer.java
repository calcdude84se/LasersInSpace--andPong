package lisp;
import javazoom.jl.player.advanced.*;
/*
 * HEY YOU!!
 * HEY!
 * 
 * 
 * You have to add the included .jar to the build path!  Sound requires jl1.0.1.jar
 */

class SoundJLayer extends PlaybackListener implements Runnable
{
    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;    

    public SoundJLayer(String filePath)
    {
        this.filePath = filePath;
    }

    public void play()
    {
        try
        {
            String urlAsString = this.filePath;

            this.player = new AdvancedPlayer
            (
                this.getClass().getResourceAsStream(urlAsString),
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent)
    {

    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {

    }    

    // Runnable members

    public void run()
    {
        try
        {
            this.player.play();
        }
        catch (javazoom.jl.decoder.JavaLayerException ex)
        {
            ex.printStackTrace();
        }

    }
}