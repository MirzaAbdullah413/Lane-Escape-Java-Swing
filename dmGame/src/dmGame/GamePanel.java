package dmGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;



public class GamePanel extends JPanel implements ActionListener, KeyListener{
    private final Image carImage;
    private final Image carImage1;
    private final Image carImage2;
    private final Image carImage3;
    private final Image carImage4;
    private final Image carImage5;
    private final Image carImage6;
    private Image truckImage1;
    private Image truckImage2;
    private Image truckImage3;
    private Image truckImage4;
    private Image truckImage5;
    private Image truckImage6;
    private Image truckImage7;
    private Image truckImage8;
    private Image gameOverImage;
    private Image leftSide;
    private Image rightSide;
    private int dashOffset = 0;
    private int carX = 310;
    private int carY = 580;
    private Timer animationTimer;
    private int score = 0;
    private Image introImage = new ImageIcon("Intro.png").getImage();
    private Image optionscreenbg = new ImageIcon("OptionScreenimg.png").getImage();
    private Clip backgroundClip;
    private boolean crashSoundPlayed = false;
    private Clip introClip;
    // Creating instances of buttons
    private JButton playButton;
    private JButton choosecar;
    private JButton exitb;
    private JButton replayButton;

    //Creating a label to Show text on the otion screen
    private JLabel opscreenlabel;
    //Creating an array of images for car drawing
    private final Image[] carImages;
    //Creating an integer to specify the index of the car being drawn
    private int i = 0;
    //Creating buttons to show images
    JButton imagebutton1;
    JButton imagebutton2;
    JButton imagebutton3;
    JButton imagebutton4;

    // making the array of the enemy cars
    private List<EnemyCar> enemyCars = new ArrayList<>();
    private boolean gameOver = false;
    
    private final int carWidth = 45;
    private final int carHeight = 90;

    private int sideImageOffsetY = 0;
    private final int sideImageHeight = 700; // Must match actual image height

    // Define fixed lane X positions (adjust to match your road design)
    private final int[] lanePositions = {130, 230, 330, 430};

    enum GameState {
    INTRO,OtptionScreen,
    PLAYING
}
private GameState gameState = GameState.INTRO;

    // this is the consturctor of Game Panel
    public GamePanel() {
        setPreferredSize(new Dimension(600, 700));
        setBackground(Color.DARK_GRAY);
        
        

        carImage = new ImageIcon("car.png").getImage().getScaledInstance(45, 90, Image.SCALE_SMOOTH);
        carImage1 = new ImageIcon("car1.png").getImage().getScaledInstance(45, 90, Image.SCALE_SMOOTH);
        carImage2 = new ImageIcon("car2.png").getImage().getScaledInstance(45, 90, Image.SCALE_SMOOTH);
        carImage3 = new ImageIcon("car3.png").getImage().getScaledInstance(45, 90, Image.SCALE_SMOOTH);
        carImage4 = new ImageIcon("car4.png").getImage().getScaledInstance(45, 90, Image.SCALE_SMOOTH);
        carImage5 = new ImageIcon("car5.png").getImage().getScaledInstance(45, 90, Image.SCALE_SMOOTH);
        carImage6 = new ImageIcon("car6.png").getImage().getScaledInstance(45, 90, Image.SCALE_SMOOTH);
        gameOverImage = new ImageIcon("gameOver.jpg").getImage().getScaledInstance(600,700,Image.SCALE_SMOOTH);
        truckImage1 = new ImageIcon("Truck1.png").getImage().getScaledInstance(50,130, Image.SCALE_SMOOTH);
        truckImage2 = new ImageIcon("Truck2.png").getImage().getScaledInstance(50,110, Image.SCALE_SMOOTH);
        truckImage3 = new ImageIcon("Truck3.png").getImage().getScaledInstance(50,110, Image.SCALE_SMOOTH);
        truckImage4 = new ImageIcon("Truck4.png").getImage().getScaledInstance(50,110, Image.SCALE_SMOOTH);
        truckImage5 = new ImageIcon("Truck5.png").getImage().getScaledInstance(50,130, Image.SCALE_SMOOTH);
        truckImage6 = new ImageIcon("Truck6.png").getImage().getScaledInstance(50,110, Image.SCALE_SMOOTH);
        truckImage7 = new ImageIcon("Truck7.png").getImage().getScaledInstance(50,110, Image.SCALE_SMOOTH);
        truckImage8 = new ImageIcon("Truck8.png").getImage().getScaledInstance(50,110, Image.SCALE_SMOOTH);
        leftSide = new ImageIcon("leftSide.png").getImage().getScaledInstance(100, 700, Image.SCALE_SMOOTH);
        rightSide = new ImageIcon("rightSide.png").getImage().getScaledInstance(100, 700, Image.SCALE_SMOOTH);

        //instantiating button to make the caar visiblein chosse car option
        imagebutton1 = new JButton();
        imagebutton1.setIcon(new ImageIcon(carImage));
        imagebutton1.setBackground(null);
        imagebutton1.setForeground(null);
        imagebutton1.setBounds(190,180,80,100);
        imagebutton1.setFocusable(false);
        imagebutton1.setVisible(false);

        imagebutton2 = new JButton();
        imagebutton2.setIcon(new ImageIcon(carImage1));
        imagebutton2.setBackground(null);
        imagebutton2.setForeground(null);
        imagebutton2.setBounds(310,180,80,100);
        imagebutton2.setFocusable(false);
        imagebutton2.setVisible(false);

        imagebutton3 = new JButton();
        imagebutton3.setIcon(new ImageIcon(carImage2));
        imagebutton3.setBackground(null);
        imagebutton3.setForeground(null);
        imagebutton3.setBounds(190,320,80,100);
        imagebutton3.setFocusable(false);
        imagebutton3.setVisible(false);

        imagebutton4 = new JButton();
        imagebutton4.setIcon(new ImageIcon(carImage3));
        imagebutton4.setBackground(null);
        imagebutton4.setForeground(null);
        imagebutton4.setBounds(310,320,80,100);
        imagebutton4.setFocusable(false);
        imagebutton4.setVisible(false);

        //Instantiating array of cars
        carImages = new Image[]{carImage, carImage1, carImage2, carImage3};

        //Instantiating label
        opscreenlabel = new JLabel();
        opscreenlabel.setFont(new Font("DejaVu Sans", Font.ITALIC, 45));//sets the new font for the button
        opscreenlabel.setVisible(false);
        opscreenlabel.setText("Select the option");
        opscreenlabel.setForeground(new Color(0,255,215)); // Sets the colour of the font
        opscreenlabel.setHorizontalAlignment(JLabel.CENTER);
        opscreenlabel.setBounds(110, 150, 400, 50);

        //Instantiating buttons
        playButton = new JButton();
        playButton.setText("Play Game");
        playButton.setBounds(200,280,200,50);
        playButton.setVisible(false);
        playButton.setFocusable(false);
        playButton.setBackground(new Color(0, 206, 209));
        playButton.setForeground(new Color(0, 0, 0)); //sets colur of the text
        playButton.setFont(new Font("DejaVu Sans", Font.BOLD, 15)); //sets the new font for the button

        choosecar = new JButton();
        choosecar.setText("Choose Car");
        choosecar.setBounds(200,380,200,50);
        choosecar.setVisible(false);
        choosecar.setFocusable(false);
        choosecar.setBackground(new Color(0, 206, 209));
        choosecar.setForeground(new Color(0, 0, 0)); //sets colur of the text
        choosecar.setFont(new Font("DejaVu Sans", Font.BOLD, 15)); //sets the new font for the button

        exitb = new JButton();
        exitb.setText("Exit");
        exitb.setBounds(200,480,200,50);
        exitb.setVisible(false);
        exitb.setFocusable(false);
        exitb.setBackground(new Color(0, 206, 209));
        exitb.setForeground(new Color(0, 0, 0)); //sets colur of the text
        exitb.setFont(new Font("DejaVu Sans", Font.BOLD, 15)); //sets the new font for the button
        
        replayButton = new JButton("Replay");
        replayButton.setBounds(200, 610, 200, 50);  // Adjust position as needed
        replayButton.setVisible(false);
        replayButton.setFocusable(false);
        replayButton.setBackground(new Color(0, 206, 209));
        replayButton.setForeground(Color.BLACK);
        replayButton.setFont(new Font("DejaVu Sans", Font.BOLD, 15));
        replayButton.addActionListener(e -> resetGameToOptionScreen());
        add(replayButton);


        //Adding action listener to Playgamebutton
        playButton.addActionListener(e -> {
            gameState = GameState.PLAYING;

            // Stop intro voice if still playing
            if (introClip != null && introClip.isRunning()) {
                introClip.stop();
                introClip.close();
            }

            playBackgroundMusic("carRacing.wav");

            opscreenlabel.setVisible(false);
            imagebutton1.setVisible(false);
            imagebutton2.setVisible(false);
            imagebutton3.setVisible(false);
            imagebutton4.setVisible(false);
            playButton.setVisible(false);
            choosecar.setVisible(false);
            exitb.setVisible(false);
            repaint();
        });


        //Adding action listener to choose car button
        choosecar.addActionListener(e ->{
            opscreenlabel.setText("Select your car");//Changes the text of the label
            opscreenlabel.setBounds(55, 40, 500, 100);//Cahnges the vertical position
            opscreenlabel.setFont(new Font("DejaVu Sans", Font.ITALIC, 72));//Changes the size of the font
            opscreenlabel.setVisible(true);
            imagebutton1.setVisible(true);
            imagebutton2.setVisible(true);
            imagebutton3.setVisible(true);
            imagebutton4.setVisible(true);
            playButton.setBounds(200,500,200,50); // Changes the vertical pos of the playgame button
            playButton.setVisible(true);
            choosecar.setVisible(false);
            exitb.setVisible(false);
        });

        //Adding action listener to exit Button
        exitb.addActionListener(e -> {System.exit(0);});

        //Adding action listener to car icon buttons
        imagebutton1.addActionListener(e -> {i=0;});
        imagebutton2.addActionListener(e -> {i=1;});
        imagebutton3.addActionListener(e -> {i=2;});
        imagebutton4.addActionListener(e -> {i=3;});

        //Adding buttons
        add(playButton);
        add(choosecar);
        add(exitb);

        //Adding car icons as buttons
        add(imagebutton1);
        add(imagebutton2);
        add(imagebutton3);
        add(imagebutton4);

        //Adding label
        add(opscreenlabel);

        addKeyListener(this);
        setFocusable(true);
        setLayout(null);   //sets the layout of panel to null to show buttons in the center
        requestFocusInWindow();
        //
        enemyCars.add(new EnemyCar(130, 460, carImage1));
        enemyCars.add(new EnemyCar(380, 380, carImage2));
        enemyCars.add(new EnemyCar(255, 140, carImage3));
        
        playIntroVoice("intro.wav"); // Replace with your actual filename




        // Animation timer to move road
        animationTimer = new Timer(30, e -> {
            sideImageOffsetY += 5;
            if (sideImageOffsetY >= sideImageHeight) {
             sideImageOffsetY = 0;
            }

        if (!gameOver) {
        	dashOffset += 5;
        	if (dashOffset >= 40) dashOffset = 0;
        	score++; // Increase score over time
        	repaint();
    	}
        });

        animationTimer.start();
        
     // Timer to add new car every 5 seconds
        new Timer(2000, e -> addRandomEnemyCar()).start();

    	}
    
    private void addRandomEnemyCar() {
        Random rand = new Random();
        Image[] carImages = {carImage1, carImage2, carImage3, carImage4, carImage5, carImage6, truckImage1, truckImage2, truckImage3, truckImage4, truckImage5, truckImage6, truckImage7, truckImage8};
        int verticalBuffer = carHeight + 20;

        // Step 1: Track which lanes are currently occupied near the top
        List<Integer> blockedLanes = new ArrayList<>();
        for (EnemyCar car : enemyCars) {
            for (int i = 0; i < lanePositions.length; i++) {
                if (Math.abs(car.x - lanePositions[i]) < 10 && car.y < verticalBuffer * 2) {
                    blockedLanes.add(i);
                }
            }
        }

        // Step 2: Create a list of available lanes
        List<Integer> availableLaneIndices = new ArrayList<>();
        for (int i = 0; i < lanePositions.length; i++) {
            if (!blockedLanes.contains(i)) {
                availableLaneIndices.add(i);
            }
        }

        // Step 3: Ensure at least one lane stays free
        if (availableLaneIndices.size() <= 1) return; // Skip adding a new enemy this time

        // Step 4: Randomly choose a lane to spawn an enemy in (but leave at least 1 free)
        Collections.shuffle(availableLaneIndices);
        int lanesToSpawn = rand.nextInt(availableLaneIndices.size() - 1) + 1;  // Leave 1 lane free
        for (int i = 0; i < lanesToSpawn; i++) {
            int laneIndex = availableLaneIndices.get(i);
            int spawnX = lanePositions[laneIndex];
            Image chosenImage = carImages[rand.nextInt(carImages.length)];
            enemyCars.add(new EnemyCar(spawnX, -carHeight, chosenImage));
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
    if (gameState == GameState.INTRO) {
        g.drawImage(introImage, 0, 0, getWidth(), getHeight(), this);
        return;  // Skip drawing the rest
    } else if (gameState == GameState.OtptionScreen) {
        g.drawImage(optionscreenbg,0,0, getWidth() , getHeight() ,this); // Will draw option screen image
        return;
    }




    	// draw road, player car, enemy cars etc.

        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        // ===== Draw Left and Right Side Images Scrolling Downward =====
        for (int y = -sideImageHeight + sideImageOffsetY; y < getHeight(); y += sideImageHeight) {
        	g.drawImage(leftSide, 0, y, this); // Adjust X = 0 for left
        	g.drawImage(rightSide, getWidth() - rightSide.getWidth(this), y, this); // Right side
        }



        // ----- DRAW ROAD DASHES -----
        Graphics2D g2d = (Graphics2D) g;

        // White side lines
        g2d.setColor(Color.WHITE);
        for (int y = -40 + dashOffset; y < h; y += 40) {
            g2d.fillRect(100, y, 10, 30); // Left side line
            g2d.fillRect(w - 110, y, 10, 30); // Right side line
        }
         //---Drawing different lanes ----
        for (int y = -40 + dashOffset; y < h; y += 40) {
            g2d.fillRect(230, y, 2, 20); // Left side line
            g2d.fillRect(w - 230, y-10, 2, 20); // Right side line
        }
        

        // ----- DRAW CAR -----
        g.drawImage(carImages[i], carX, carY, this);

        for (EnemyCar car : enemyCars) {
        	if(!gameOver){
            car.move();
            enemyCars.removeIf(cr -> cr.y > getHeight());

            }
            g.drawImage(car.image, (int)car.x, (int)car.y, this);
        }
        

        // ======Contolling the car Collision
        Rectangle playerBounds = new Rectangle(carX, carY, carImage.getWidth(this), carImage.getHeight(this));

        for(EnemyCar Ecars : enemyCars){
            if(playerBounds.intersects(Ecars.getBounds())){
                gameOver = true;
                animationTimer.stop();
                
                stopBackgroundMusic();
                
                if (!crashSoundPlayed) {
                	try {
                	    Thread.sleep(100); // wait 100 milliseconds
                	} catch (InterruptedException ex) {
                	    ex.printStackTrace();
                	}
                	playSound("crash.wav");
                	crashSoundPlayed = true;
                }
                
                    break;
                    
            }
         }
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score / 10, 20, 30); // Divide by 10 to slow it down a bit

            if (gameOver) {
                g.drawImage(gameOverImage, 0, 0, this);
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.drawString("Final Score: " + score / 10, 200, 70);

                Timer showReplayButtonTimer = new Timer(2500, evt -> {
                    replayButton.setVisible(true);
                });
                showReplayButtonTimer.setRepeats(false);
                showReplayButtonTimer.start();
                return;
            }




    }

        //moving the car
        @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == GameState.INTRO) {
        	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        	    gameState = GameState.OtptionScreen;

        	    // Restart intro voice when transitioning to OptionScreen
        	    if (introClip != null && introClip.isRunning()) {
        	        introClip.stop();
        	        introClip.close();
        	    }
        	    playIntroVoice("intro.wav");

        	    opscreenlabel.setVisible(true);
        	    playButton.setVisible(true);
        	    choosecar.setVisible(true);
        	    exitb.setVisible(true);

        	    repaint();  // trigger redraw
        	}

        return;
    }

    if (gameOver) return;
        if(gameOver){
            return;
        }
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && carX > 110) {
            carX -= 5;
        }
        if (key == KeyEvent.VK_RIGHT && carX < 440) {
            carX += 5;
        }
         if (key == KeyEvent.VK_UP && carY > 0) {
             carY -= 5;
         }
         if (key == KeyEvent.VK_DOWN && carY < getHeight() - 80) {
             carY += 5;
         }

        
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }


    // For testing it standalone
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lane Escape");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("logo.png");
        frame.add(new GamePanel());
        frame.setIconImage(logo.getImage());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void playSound(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.setFramePosition(0);  
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    
    private void playBackgroundMusic(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioIn);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();  // optional: free system resources
        }
    }
    
    private void playIntroVoice(String soundFileName) {
        try {
            File soundFile = new File(soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            introClip = AudioSystem.getClip();
            introClip.open(audioIn);
            introClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    
    private void resetGameToOptionScreen() {
        gameOver = false;
        score = 0;
        carX = 310;
        carY = 580;
        crashSoundPlayed = false;
        enemyCars.clear(); // Clear old enemies
        enemyCars.add(new EnemyCar(130, 460, carImage1));
        enemyCars.add(new EnemyCar(380, 380, carImage2));
        enemyCars.add(new EnemyCar(255, 140, carImage3));

        gameState = GameState.OtptionScreen;

        replayButton.setVisible(false);  // Hide the replay button
        opscreenlabel.setText("Select the option");
        opscreenlabel.setBounds(110, 150, 400, 50);
        opscreenlabel.setFont(new Font("DejaVu Sans", Font.ITALIC, 45));
        opscreenlabel.setVisible(true);
        playButton.setVisible(true);
        choosecar.setVisible(true);
        exitb.setVisible(true);

        // Restart intro music
        playIntroVoice("intro.wav");

        animationTimer.start();
        repaint();
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}