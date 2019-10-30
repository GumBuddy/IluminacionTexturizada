package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
//Librerias para las texturas
import java.io.File;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Dimension;
import java.io.IOException;

import static org.yourorghere.GLRenderer.q;

public class Texturas implements GLEventListener, MouseListener, MouseMotionListener {

    static float a = 0.0f, w = 0.8f, e = 0.2f, r = 1.0f;

    private Texture cara1, cara2, cara3, cara4, cara5, cara6;
    private float view_rotx = 20.0f, view_roty = 30.0f, view_rotz = 0.0f;
    static private float angle = 0.0f;
    private int prevMouseX, prevMouseY;
    private boolean mouseRButtonDown = true;

    float mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
    float mat_shininess[] = {50.0f};
    float light_position[] = {1.0f, 1.0f, 1.0f, 0.0f};
    float[] light_position_2 = {-1.0f, -1.0f, 1.0f};
    float[] COLOR_2 = {-1.0f, -1.0f, 1.0f, 0.0f};

    public static void main(String[] args) {
        Frame frame = new Frame("Tarea");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Texturas());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glEnable(GL.GL_CULL_FACE);
        //Establecemos la ruta de la textura y la variable que tomara dicha textura

        int X = GL.GL_SPECULAR;
        gl.glShadeModel(GL.GL_SMOOTH);

        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, light_position, 0);
        gl.glLightfv(GL.GL_LIGHT1, X, COLOR_2, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, light_position_2, 0);

        float color[] = {a, w, e, r};

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, color, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess, 0);

        gl.glEnable(GL.GL_CONSTANT_ATTENUATION);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);

        try {
            //Se indica la localizacion de la figura                
            cara1 = TextureIO.newTexture(new File("src/org/yourorghere/mariposa.jpg"), true);
            cara2 = TextureIO.newTexture(new File("src/org/yourorghere/mariposa.jpg"), true);
            cara3 = TextureIO.newTexture(new File("src/org/yourorghere/mariposa.jpg"), true);
            cara4 = TextureIO.newTexture(new File("src/org/yourorghere/mariposa.jpg"), true);
            cara5 = TextureIO.newTexture(new File("src/org/yourorghere/mariposa.jpg"), true);
            cara6 = TextureIO.newTexture(new File("src/org/yourorghere/mariposa.jpg"), true);
        } catch (IOException e) {
            System.err.print("No se puede cargar textura" + e);
            System.exit(1);
        }
        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        angle += 0f;
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        gl.glTexEnvi(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_DECAL);
        gl.glLoadIdentity();
        // Move the "drawing cursor" around

        gl.glTranslatef(0.0f, 0.0f, -6.0f);
        gl.glPushMatrix();
        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);

        gl.glPushMatrix();
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
        cara1.enable();//habilitamos
        cara1.bind();//pegamos
        gl.glScalef(8, 8, 8);
        gl.glColor3f(.3f, .8f, .9f);
        gl.glBegin(gl.GL_QUADS);

        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(0, 0, 0);

        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0.2f, 0f, 0);

        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(.2f, .2f, 0);

        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(0f, .2f, 0);
        gl.glEnd();
        cara1.disable();//desabilitar

        cara2.enable();
        cara2.bind();
        //Cara 2  Verde
        gl.glColor3f(.1f, .8f, .1f);
        gl.glBegin(gl.GL_QUADS);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(.2f, 0, 0);

        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(.2f, 0f, -.2f);

        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(.2f, .2f, -.2f);

        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(.2f, .2f, 0f);
        gl.glEnd();
        cara2.disable();

        cara3.enable();
        cara3.bind();
        //Cara 3  Amarillo       
        gl.glColor3f(.8f, .8f, .1f);
        gl.glBegin(gl.GL_QUADS);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(.2f, 0, -.2f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0f, 0f, -.2f);

        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(0f, .2f, -.2f);

        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(.2f, .2f, -.2f);
        gl.glEnd();
        cara3.disable();

        //Cara 4  ROJO
        cara4.enable();
        cara4.bind();
        gl.glColor3f(.9f, .1f, .1f);
        gl.glBegin(gl.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(.0f, 0, 0f);

        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(0f, .2f, 0f);

        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(0f, .2f, -.2f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0f, 0f, -.2f);
        gl.glEnd();
        cara4.disable();

        //Cara 5  Verde Fuerte
        cara5.enable();
        cara5.bind();
        gl.glColor3f(.1f, .4f, .1f);
        gl.glBegin(gl.GL_QUADS);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(.2f, 0f, 0f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(.2f, 0f, -.2f);

        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(0f, 0f, -.2f);

        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(.0f, 0, 0f);
        gl.glEnd();
        cara5.disable();

        //Cara 6  morado
        cara6.enable();
        cara6.bind();
        gl.glColor3f(.3f, .1f, .3f);
        gl.glBegin(gl.GL_QUADS);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(.2f, .2f, 0f);

        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(.2f, .2f, -.2f);

        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(0f, .2f, -.2f);

        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(.0f, .2f, 0f);

        gl.glEnd();
        gl.glPopMatrix();

        gl.glPopMatrix();

        cara6.disable();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevMouseX = e.getX();
        prevMouseY = e.getY();
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            mouseRButtonDown = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            mouseRButtonDown = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
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

    @Override
    public void mouseMoved(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
