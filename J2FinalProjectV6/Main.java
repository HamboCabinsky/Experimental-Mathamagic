import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.TextField;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Begins program execution
 * 
 * Display needs to have its own class separate from Main to take advantage
 * of the Keylistener interface I'm implementing to read text input from the JFrame
 *
 * @author Christian Bonin
 * @version 5/6/2019
 */
public class Main //implements KeyListener
{
    private static Display display;
    private static String title = "Experimental Mathamagic";
    private static int width = 1400;
    private static int height = 800;
    
    public static void main(String[] args)
    {
        display = new Display(title, width, height);
    }
    
}
