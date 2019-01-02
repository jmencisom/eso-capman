package view.components;

import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author edisonarango
 */
public class ProgressBarTask extends SwingWorker<Void, Void> {
        
    private int progress = 0;
    private JProgressBar progressBar;

    public ProgressBarTask(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void updateProgress(int progress) {
        this.progress = progress;
    }

    public void finish(){
        this.progress = 100;
    }
    /*
     * Main task. Executed in background thread.
     */
    @Override
    public Void doInBackground() {
        //Initialize progress property.
        if (progress == 0){
            progressBar.setIndeterminate(true);
        }
        setProgress(progress);
        while (progress < 100) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {}
            setProgress(Math.min(progress, 100));
        }
        return null;
    }

    /*
     * Executed in event dispatch thread
     */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
                progressBar.setValue(0);
            }
        }).start();
    }
}
