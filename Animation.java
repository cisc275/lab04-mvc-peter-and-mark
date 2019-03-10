//T Harvey
//based loosely on http://www.java2s.com/Code/JavaAPI/java.awt/GraphicsdrawImageImageimgintxintyImageObserverob.htm
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Animation extends JPanel {

    final int frameCount = 10;
    int picNum = 0;
    BufferedImage[][] pics;
    String direction = "";
    int xloc = 0;
    int yloc = 0;
    int xChg = 1;
    int yChg = 1;
    int dir = 3;
    final int xIncr = 8;
    final int yIncr = 2;
    final static int frameWidth = 500;
    final static int frameHeight = 300;
    final static int imgWidth = 165;
    final static int imgHeight = 165;

    //Override this JPanel's paint method to cycle through picture array and draw images
    public void paint(Graphics g) {
        picNum = (picNum + 1) % frameCount;
        if(xloc > (frameWidth - imgWidth) || xloc < 0) {
        	switch(dir) {
	        	case 1:
	        		dir = 7;
	        		break;
	        	case 3:
	        		dir = 5;
	        		break;
	        	case 5:
	        		dir = 3;
	        		break;
	        	case 7:
	        		dir = 1;
	        		break;
        	}
        	xChg = -1*xChg;
        	
        }
        if(yloc > (frameHeight - imgHeight) || yloc < 0) {

        	yChg = -1*yChg;
        	switch(dir) {
        		case 1:
        			dir = 3;
        			break;
        		case 3:
        			dir = 1;
        			break;
        		case 5:
        			dir = 7;
        			break;
        		case 7:
        			dir = 5;
        			break;
        	}
        }
        imageDraw(g,dir,xChg,yChg);
           
        // TODO: Keep the orc from walking off-screen, turn around when bouncing off walls.
        //Be sure that animation picture direction matches what is happening on screen.
    }
    
    public void imageDraw(Graphics g,int dir,int xNeg,int yNeg) {
    	g.drawImage(pics[picNum][dir], xloc+=xNeg*xIncr, yloc+=yNeg*yIncr, Color.gray, this);
    }

    //Make frame, loop on repaint and wait
    public static void main(String[] args) {
    	Controller c = new Controller();
    	c.start();
        JFrame frame = new JFrame();
        frame.getContentPane().add(new View());
        frame.setBackground(Color.gray);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);
        for(int i = 0; i < 1000; i++){
            frame.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Constructor: get image, segment and store in array
    public Animation(){
        String[] arrOfStr = {"forward_north", "forward_northeast", "forward_east", "forward_southeast",
                "forward_south", "forward_southwest", "forward_west", "forward_northwest"};
        BufferedImage[] img = createImage(arrOfStr);
        pics = new BufferedImage[10][arrOfStr.length];
        //System.out.println(img.length);
        int count = 0;
        for (BufferedImage curImg : img) {
            for(int i = 0; i < frameCount; i++) {
                pics[i][count] = curImg.getSubimage(imgWidth*i, 0, imgWidth, imgHeight);
            }
            count ++;
        }
        
            
        // TODO: Change this constructor so that at least eight orc animation pngs are loaded
    }  
    
    //Read image from file and return
    private BufferedImage[] createImage(String[] strArr){
        //for (String str : strArr) System.out.println(str);
        BufferedImage[] bufferedImage = new BufferedImage[strArr.length];
        String path = "images/orc/orc_";
        int count = 0;
        for (String str : strArr) {
            //System.out.println(path.concat(str).concat(".png"));
            //System.out.println(strArr.length);
            try {
                
                bufferedImage[count] = ImageIO.read(new File(path.concat(str).concat(".png")));
                count ++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedImage;
        
        // TODO: Change this method so you can load other orc animation bitmaps
    }
}