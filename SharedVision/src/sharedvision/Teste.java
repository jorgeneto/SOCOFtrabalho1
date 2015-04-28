package sharedvision;

import com.sun.glass.events.KeyEvent;
import java.awt.AWTException;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Teste implements KeyListener {

    public Teste() {

    }

    public void interfaceTeclado() {

        JFrame frame = new JFrame("Teste");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel jp = new JPanel();

        JButton buttonUp = new JButton("Up");
        buttonUp.addKeyListener(this);
        //buttonUp.setVisible(false);
        JButton buttonLeft = new JButton("Left");
        buttonLeft.addKeyListener(this);
        //buttonLeft.setVisible(false);
        JButton buttonDown = new JButton("Down");
        buttonDown.addKeyListener(this);
        //buttonDown.setVisible(false);
        JButton buttonRight = new JButton("Right");
        buttonRight.addKeyListener(this);
        //buttonRight.setVisible(false);

        jp.add(buttonUp);
        jp.add(buttonLeft);
        jp.add(buttonDown);
        jp.add(buttonRight);

        frame.add(jp);

        frame.pack();
        //5. Mostra a janela
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent ke) {
        System.out.println("");
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == e.VK_UP) {
            System.out.println("Pressionou para cima");
        }
        if (e.getKeyCode() == e.VK_LEFT) {
            System.out.println("Pressionou para esquerda ");
        }
        if (e.getKeyCode() == e.VK_DOWN) {
            System.out.println("Pressionou para baixo ");
        }
        if (e.getKeyCode() == e.VK_RIGHT) {
            System.out.println("Pressionou para direita ");
        }
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        System.out.println("");
    }
}
