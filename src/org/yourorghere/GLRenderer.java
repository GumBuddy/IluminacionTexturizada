package org.yourorghere;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import com.sun.opengl.util.GLUT;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.GLJPanel;
import javax.swing.JOptionPane;
import java.io.File;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GLException;

public class GLRenderer implements GLEventListener, MouseListener, MouseMotionListener {

    private float view_rotx = 20.0f, view_roty = 30.0f, view_rotz = 0.0f;
    private int figura;
    static private float angle = 0.0f;
    private int prevMouseX, prevMouseY;
    private boolean mouseRButtonDown = true;
    float mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float mat_shininess[] = {50.0f};
    float light_position[] = {1.0f, 1.0f, 1.0f, 0.0f};
    float[] light_position_2 = {-1.0f, -1.0f, 1.0f};
    float[] COLOR_2 = {-1.0f, -1.0f, 1.0f,0.0f};


    static int x = 0, y = 0;
    static double Escala = 3;

    /*las siguientes variables son para el color, esta combinacion es para verde*/
    static float q = 0.0f, w = 0.8f, e = 0.2f, r = 1.0f;

    public void init(GLAutoDrawable drawable) {
        try {
            GL gl = drawable.getGL();
            gl.setSwapInterval(2);
            //gl.glEnable(GL.GL_CULL_FACE);

            GLUT glut = new GLUT();
            float color[] = {q, w, e, r};
            /*
            ILUMINACION SUAVE:SMOOTH 
            ILUMINACION AMBIENTAL:FLAT
            */
            gl.glShadeModel(GL.GL_SMOOTH);     
            //gl.glShadeModel(GL.GL_FLAT);
            /*
            Caracteristicas del material
            */
            gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, color, 0);
            gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
            gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess, 0);
            
            int X=GL.GL_SPECULAR;
          
            gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, light_position, 0);
            gl.glLightfv(GL.GL_LIGHT1, X, COLOR_2,0);
            gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, light_position_2,0 );
            


            gl.glEnable(GL.GL_CONSTANT_ATTENUATION);
            gl.glEnable(GL.GL_LIGHTING);
            gl.glEnable(GL.GL_LIGHT0);
            gl.glEnable(GL.GL_LIGHT1);
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glDepthFunc(GL.GL_LESS);

            figura = gl.glGenLists(4);
            gl.glNewList(figura, GL.GL_COMPILE);
            Figura_generada(glut);
            gl.glTranslatef(-2, 1, 0);
            Figura_generada_2(glut);
            gl.glTranslatef(2, 2, 0);
            Figura_generada_3(glut);
            gl.glTranslatef(2, -5, 0);
            Figura_generada_4(glut);
            gl.glEndList();
            gl.glEnable(GL.GL_NORMALIZE);
            
            
            drawable.addMouseListener(this);
            drawable.addMouseMotionListener(this);
            drawable.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    dispatchKey(e.getKeyCode(), e.getKeyChar());
                }
            });
        } catch (GLException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*Cordenadas en X y Y*/

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        float h = (float) height / (float) width;

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustum(-1.0f, 1.0f, -h, h, 5.0f, 80.0f);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        /*el ultimo valor es para acercarlo a la camara o alejarlo*/
        gl.glTranslatef(0.0f, 0.0f, -30.0f);
    }

    public static void Figura_generada(GLUT glut) {
        //glut.glutSolidIcosahedron();
         glut.glutSolidSphere(1.0, 100, 100);
        //glut.glutSolidTeapot(2);
       //glut.glutSolidCone(2, 4, 3, 1);
       //glut.glutSolidTorus(1,1,5,25);
        //glut.glutSolidCube(1);
        //glut.glutSolidRhombicDodecahedron();
    }
    public static void Figura_generada_2(GLUT glut) {
         glut.glutSolidRhombicDodecahedron();
        
    }
    public static void Figura_generada_3(GLUT glut) {
        glut.glutSolidIcosahedron();
         }
    public static void Figura_generada_4(GLUT glut) {
         glut.glutSolidTeapot(1);
     }
    public void display(GLAutoDrawable drawable) {
        try {
            GL gl = drawable.getGL();

            //Rotacion
            angle += 0.1f;
            //angle=0;
////////////////////////////////////////////////////////////////////////////////
            
   
    Texture cara= TextureIO.newTexture(new File("src/org/yourorghere/mariposa.jpg"), true);

    gl.glTexParameteri( gl.GL_TEXTURE_2D, gl.GL_TEXTURE_WRAP_S, gl.GL_REPEAT );
    gl.glTexParameteri( gl.GL_TEXTURE_2D, gl.GL_TEXTURE_WRAP_T, gl.GL_REPEAT );
    gl.glTexParameteri( gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MAG_FILTER, gl.GL_LINEAR );
    gl.glTexParameteri( gl.GL_TEXTURE_2D, gl.GL_TEXTURE_MIN_FILTER, gl.GL_LINEAR );
    gl.glTexEnvi(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_MODULATE);
    
    
    
    gl.glEnable(gl.GL_TEXTURE_GEN_S);
    gl.glTexGeni(gl.GL_T, gl.GL_TEXTURE_GEN_MODE, gl.GL_OBJECT_LINEAR);
  
            
         //Habilitamos las texturas.
    gl.glEnable(gl.GL_TEXTURE_2D);
    gl.glEnable (gl.GL_TEXTURE_GEN_T);
////////////////////////////////////////////////////////////////////////////////

            
            
            
            
            
           
            //gl.glColor4d(1, 0, 0, 0);
            if ((drawable instanceof GLJPanel)
                    && !((GLJPanel) drawable).isOpaque()
                    && ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
                gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
            } else {
                gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
            }
            gl.glPushMatrix();
            gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
            gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);

            gl.glPushMatrix();
            gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
            gl.glTranslatef(x, y, 0);
            gl.glScaled(Escala, Escala, Escala);
            gl.glCallList(figura);
            gl.glPopMatrix();

            gl.glPopMatrix();
        } catch (GLException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    //-+-------------------------------------------------------------------------------------------------------

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        prevMouseX = e.getX();
        prevMouseY = e.getY();
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            mouseRButtonDown = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            mouseRButtonDown = false;
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    // Methods required for the implementation of MouseMotionListener
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();

        float thetaY = 360.0f * ((float) (x - prevMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);

        prevMouseX = x;
        prevMouseY = y;

        view_rotx += thetaX;
        view_roty += thetaY;
    }

    public void mouseMoved(MouseEvent e) {
    }
    //-+-------------------------------------------------------------------------------------------------------

    static private boolean[] b = new boolean[256];
    private int program = 2;
    private int obj = 2;

    static private void dispatchKey(int key, char k) {
        if (k < 256) {
            b[k] = !b[k];
        }

        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_KP_UP:
                y = y + 1;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_KP_DOWN:
                y = y - 1;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_KP_LEFT:
                x = x - 1;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_KP_RIGHT:
                x = x + 1;
                break;
            case KeyEvent.VK_K:
                Escala = Escala - 1;
                break;
            case KeyEvent.VK_L:
                Escala = Escala + 1;
                break;
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1:
                angle += 10;
                break;
            case KeyEvent.VK_0:
            case KeyEvent.VK_NUMPAD0:
                angle -= 0.9;
                break;

            case KeyEvent.VK_H:
                String endl = System.getProperty("line.separator");
                endl = endl + endl;
                String msg
                        = ("MENU" + endl
                        + "Usar letras K(+) o  L(-) para Escalar" + endl
                        + "Usar Flechas para Trasladar" + endl
                        + "Mueve el raton para rotar el objeto" + endl
                        + "Mantener sumido 0 o 1 para cambiar velocidad de la animacion" + endl
                        + "Para salir presiona Esc " + endl);
                JOptionPane.showMessageDialog(null, msg, "Help", JOptionPane.INFORMATION_MESSAGE);
                break;

            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                System.exit(0);
                return;

        }

    }
}
