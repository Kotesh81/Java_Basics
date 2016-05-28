

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
// import org.opencv.videoio.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.swing.JFileChooser;


public class RecVideo {
	JFrame frame1 = new JFrame();
	JButton btnPause = new JButton("Pause");
	final JButton btnStart = new JButton("Start");
	JPanel panel = new JPanel();
	JFileChooser fileChooser = new JFileChooser();
    private volatile boolean runnable = true;
	    private Catcher cat;
	    private Thread catcher;
	    private OpenCVFrameGrabber grabber = null;
	    private  FFmpegFrameRecorder recorder = null;
	   
	
	// Definitions
	 private DaemonThread myThread = null;
	    int count = 0;
	    VideoCapture webSource = null;

	    Mat frame2 = new Mat();
	    MatOfByte mem = new MatOfByte();
	////////////////////////////////////////////////////////////
	
	
	     class Catcher implements Runnable {
	 	  
		   
		    private static final int WEBCAM_DEVICE_INDEX = 0;
		    private static final int CAPTUREWIDTH = 320;
		    private static final int CAPTUREHRIGHT = 240;
		    private static final int FRAME_RATE = 15;
		    private static final int GOP_LENGTH_IN_FRAMES = 60;
		
		    private static final long serialVersionUID = 1L;
		       
	        public void run() {
	            synchronized (this) {
	                while (runnable) {
	                    
	           //  	CvCapture grabber = new cvCreateCameraCapture(0);
	                	grabber = new OpenCVFrameGrabber(0);
	                     grabber.setImageWidth(320);
	                       grabber.setImageHeight(240);
	                        try {
	                        grabber.start();
	                     } 
							 catch (FrameGrabber.Exception e) {
								
								e.printStackTrace();
							}
	                	System.out.println("Initials.......");
	                     //  recorder = new FFmpegFrameRecorder("output.mp4",  CAPTUREWIDTH, CAPTUREHRIGHT, 2);
	                	recorder = new FFmpegFrameRecorder("output.mp4",  CAPTUREWIDTH, CAPTUREHRIGHT);
	                	   recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
	                       // recorder.setCodecID(avcodec.AV_CODEC_ID_H263);
	                       recorder.setFormat("mp4");
	                       recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
	                     //  recorder.start();
	                      //  recorder.setInterleaved(true);
	                        // video options //
	                       //  recorder.setVideoOption("tune", "zerolatency");
	                       // recorder.setVideoOption("preset", "ultrafast");
	                      //   recorder.setVideoOption("crf", "28");
	                     //   recorder.setVideoBitrate(10 * 1024 * 1024);
	                   //     recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
	                    //    recorder.setFormat("mp4");
	                    //    recorder.setFrameRate(FRAME_RATE);
	                      //   recorder.setGopSize(GOP_LENGTH_IN_FRAMES);
	                        // audio options //
	                   //     recorder.setAudioOption("crf", "0");
	                   //     recorder.setAudioQuality(0);
	                   //     recorder.setAudioBitrate(192000);
	                   //     recorder.setSampleRate(44100);
	                    //    recorder.setAudioChannels(2);
	                      //  recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
	                    //    

	                        try {
	                        	System.out.println("Recording Started........");
								recorder.start();
							} catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
								
								System.out.println("Recording Error........");
								e1.printStackTrace();
							}
	                      
	                       Frame capturedFrame = null;
	                //        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
	                //        try {
								try {
									System.out.println("Record Progressing......");
									capturedFrame = grabber.grab();
									while (capturedFrame  != null)
									{
									
//			    BufferedImage buff = paintConverter.getBufferedImage(capturedFrame, 1);
//			    Graphics g = panel.getGraphics();
//			    g.drawImage(buff, 0, 0, CAPTUREWIDTH, CAPTUREHRIGHT, 0, 0, buff.getWidth(), buff.getHeight(), null);
									try {
										recorder.record(capturedFrame);
										capturedFrame = grabber.grab();
									} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
										
											e.printStackTrace();
										}
//			}
//		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
									
//			e.printStackTrace();
//		}
               

            }
								} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
	                } }
	        }
	     public void Process() throws org.bytedeco.javacv.FrameRecorder.Exception, org.bytedeco.javacv.FrameGrabber.Exception {
	   	 if (btnRec.getText().equals("Stop")) {
             
             recorder.stop();
             catcher.stop();
           //  grabber.stop();
             runnable = false;
             btnRec.setText("Record");
             // text1.setText("");
         }
	     }
	    }
	    
	    
	    class DaemonThread implements Runnable
	    {
	    protected volatile boolean runnable = false;

	    public  void run()
	    {
	        synchronized(this)
	        {
	            while(runnable)
	            {
	                if(webSource.grab())
	                {
			    	try
	                        {
	                            webSource.retrieve(frame2);
				    Highgui.imencode(".bmp", frame2, mem);
				    Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

				    BufferedImage buff = (BufferedImage) im;
				    Graphics g=panel.getGraphics();

				    if (g.drawImage(buff, 0, 0, panel.getWidth(), panel.getHeight() , 0, 0, buff.getWidth(), buff.getHeight(), null))
				    
				    if(runnable == false)
	                            {
				    	System.out.println("Going to wait()");
				    	this.wait();
				    }
				 }
				 catch(Exception ex)
	                         {
				    System.out.println("Error");
	                         }
	                }
	            }
	        }
	     }
	   }  
	    
	   
	private final JButton btnSnapshot = new JButton("SnapShot");
	private final JButton btnRec = new JButton("Record");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		

        System.loadLibrary("opencv_java249"); // load native library of opencv
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecVideo window = new RecVideo();
					window.frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecVideo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	// @SuppressWarnings("deprecation")
	private void initialize() {
	
		frame1.setBounds(100, 100, 641, 520);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);
		
	
		panel.setBounds(59, 67, 500, 326);
		frame1.getContentPane().add(panel);
		
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				 /// start button 
				// webSource =new VideoCapture(0);// Video From The Default Video 
				//  myThread = new DaemonThread();// Create a New Thread Class Of Object 
				  //          Thread t = new Thread(myThread);
				   //         t.setDaemon(true);
				   //         myThread.runnable = true;
				    //        t.start();     // Start The Thread
				     //       btnStart.setEnabled(false);  //Deactivate start button
				      //      btnPause.setEnabled(true);  // Activate Only Pause button
				StartCam();
				
			}
		});
		btnStart.setBounds(28, 423, 117, 25);
		frame1.getContentPane().add(btnStart);
		
		
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				/// stop button 
				myThread.runnable = false;
				            btnStart.setEnabled(true);   
				            btnPause.setEnabled(false);
				            
				            webSource.release();			


				
			}
		});
		btnPause.setBounds(431, 423, 117, 25);
		frame1.getContentPane().add(btnPause);
		
		
		// fileChooser.setBounds(187, 122, 306, 252);
		// fileChooser.disable();
		// fileChooser.setVisible(false);
		
		frame1.getContentPane().add(fileChooser);
		btnSnapshot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

////////////////snapshot button
// 
        int returnVal = fileChooser.showSaveDialog(fileChooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        //	fileChooser.setVisible(true);
        // 	fileChooser.enable();
        File file =	null;
       file = fileChooser.getSelectedFile();
       
        	String filename ;
        	
        	  filename = file.toString();
        	  System.out.println(frame2);
        	  System.out.println(filename);
        	  
        Highgui.imwrite(filename, frame2);
    } else {
        System.out.println("File access cancelled by user.");
    }
			}
		});
		btnSnapshot.setBounds(314, 424, 89, 23);
		
		frame1.getContentPane().add(btnSnapshot);
		btnRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  try {
					CamRecorder();
				} catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (org.bytedeco.javacv.FrameRecorder.Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnRec.setEnabled(true);
		btnRec.setBounds(169, 423, 117, 25);
		
		frame1.getContentPane().add(btnRec);
		
	
		 /// start Camera
		StartCam();
		 
	}
	
	@SuppressWarnings("deprecation")
	public void CamRecorder() throws org.bytedeco.javacv.FrameGrabber.Exception, org.bytedeco.javacv.FrameRecorder.Exception {
	grabber = new OpenCVFrameGrabber(0);
		Catcher cat = new Catcher();
        
		if (btnRec.getText().equals("Stop")) {										System.out.println("Record Progressing......");
		System.out.println("Stopping Initiated.....");

			
            
          //  recorder.stop();
          cat.Process();//  catcher.stop();
         //   grabber.stop();
         //   runnable = false;
            btnRec.setText("Record");
            // text1.setText("");
        } else {
            btnRec.setText("Stop");
            catcher = new Thread(cat);
            catcher.start();
            runnable = true;
          //   text1.setText("Recording ...");
        }
                
                
        }
	
	public void StartCam() {
		// TODO Auto-generated method stub
		/// start button 
		 webSource =new VideoCapture(0);// Video From The Default Video 
		  myThread = new DaemonThread();// Create a New Thread Class Of Object 
		            Thread t = new Thread(myThread);
		            t.setDaemon(true);
		            myThread.runnable = true;
		            t.start();     // Start The Thread
		            btnStart.setEnabled(false);  //Deactivate start button
		            btnPause.setEnabled(true);  // Activate Only Pause button
	}
	
	
	
	
}

