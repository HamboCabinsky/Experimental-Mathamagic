import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException; 

/**
 * Handles mostly displaying graphics, contains some paint methods
 * Also handles keyboard input before passing it off to InputProcessor
 * Mostly boring boilerplate stuff
 *
 * @author Christian Bonin
 * @version 5/6/2019
 */
public class Display implements KeyListener
{
    private JFrame frame;
    private Canvas canvas;
    private TextField field;
    private BufferStrategy buffStrat;
    private Graphics g;
    private String title;
    private String input;
    private int width, height;
    private Color color;
    
    private InputProcessor processor;
    private MemoryQueue<String> commandMemory;
    private int memoryIndex = 0;
    
    public BufferStrategy getBuffStrat()
    {
        return buffStrat;
    }
    
    public Display(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
        
        color = Color.black;
        createDisplay();
        buffStrat = canvas.getBufferStrategy();
        if(buffStrat == null){
            canvas.createBufferStrategy(3); //2 for double buffering
                                   //3 for triple buffering
        }
        processor = new InputProcessor();
        commandMemory = new MemoryQueue<String>();
    }
    
    /**
     *  sets us up for painting to the canvas
     *  bufferstrategy will make for smoother animation if desired
     *  double buffering = smooth
     *  triple buffering = very smooth
     *  setColor dips the paintbrush in the display class's  
     *  currently specified color
     */
    public Graphics drawPrep()
    {
        buffStrat = canvas.getBufferStrategy();
        if(buffStrat == null){
            canvas.createBufferStrategy(3); //2 for double buffering
            return null;                         //3 for triple buffering
        }
        g = buffStrat.getDrawGraphics();    
        g.setColor(color);
        return g;                           //return the Graphics object
    }
    
    /**
     *  The nested do whiles are recommended by the Oracle docs
     *  The outer one is meant to catch if any image content
     *  has been lost (common b/c BS uses volatile images)
     *  Inner loop is to catch and redraw if image comes back
     *  (could come back in unexpected order)
     */
    public void paintOval(int x, int y, int width, int height)
    {   
        do{
            do{
                g = drawPrep();
                g.fillOval(x, y, width, height);
            }while(buffStrat.contentsRestored());
            buffStrat.show();   //displays what has been stored in buffer
            g.dispose();        //frees memory graphics object is holding onto
        }while(buffStrat.contentsLost());
    }
    
    public void line(int x1, int y1, int x2, int y2)
    {
        do{
            do{
                g = drawPrep();
                g.drawLine(x1, y1, x2, y2);
            }while(buffStrat.contentsRestored());
            buffStrat.show();   //displays what has been stored in buffer
            g.dispose();        //frees memory graphics object is holding onto
        }while(buffStrat.contentsLost());
    }
    
    public void paint(String input, int x, int y)
    {
        do{
            do{
                g = drawPrep();
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.drawString(input, x, y);
            }while(buffStrat.contentsRestored());
            buffStrat.show();
            g.dispose();
        }while(buffStrat.contentsLost());
    }
    
    public void paint(String input, int x, int y, int size)
    {
        do{
            do{
                g = drawPrep();
                g.setFont(new Font("Helvetica", Font.BOLD, size));
                g.drawString(input, x, y);
            }while(buffStrat.contentsRestored());
            buffStrat.show();
            g.dispose();
        }while(buffStrat.contentsLost());
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public void clear()
    {
        g = drawPrep();
        for(int i=0; i<2; i++){
            g.clearRect(0, 0, width, height);
            buffStrat.show();
            g.dispose();
        }
    }
    
    public void fill()
    {
        do{
            do{
                g = drawPrep();
                g.fillRect(0, 0, width, height);
            }while(buffStrat.contentsRestored());
            buffStrat.show();
            g.dispose();
        }while(buffStrat.contentsLost());
    }
    
    public Canvas getCanvas(){
        return canvas;
    }
    
    /**
     * Boilerplate code to set up a basic JFrame and canvas to display graphics,
     * taken from my Java I final project (which was heavily guided by this Youtube
     * tutorial = https://www.youtube.com/watch?v=dEKs-3GhVKQ&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ)
     * Following Oracle's documentation, I added a textfield and made it respond to
     * user input using the KeyListener interface
     */
    private void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height-100));
        canvas.setMaximumSize(new Dimension(width, height-100));
        canvas.setMinimumSize(new Dimension(width, height-100));
        canvas.setFocusable(false);
        
        field = new TextField("help");
        field.addKeyListener(this);
        
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(field, BorderLayout.PAGE_END);
        frame.pack();
    }
    
    public void keyPressed(KeyEvent e)
    {
        int k = e.getKeyCode();
        if(k == KeyEvent.VK_ENTER){
            input = field.getText().toLowerCase();
            commandMemory.add(input);
            field.setText("");
            try{
                processor.processToDisplay(input, this);
            }catch(Exception ex){
                
            }
        }
        if(k == KeyEvent.VK_UP){
            if(memoryIndex < commandMemory.size()){
                field.setText(commandMemory.recall(memoryIndex));
                memoryIndex++;
            }
        }
        if(k == KeyEvent.VK_DOWN){
            if(memoryIndex > 0){
                memoryIndex--;
                field.setText(commandMemory.recall(memoryIndex));
            }else{
                field.setText("");
            }
        }
    }
    
    public void keyReleased(KeyEvent e)
    {
    }
    
    public void keyTyped(KeyEvent e)
    {
    }
}
