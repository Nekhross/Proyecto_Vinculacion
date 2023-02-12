/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.mdl_alumnos;
import Modelo.mdl_usuarios;
import Modelo.Alumnos;
import Modelo.usuarios;
import Vista.frm_Login;
import Vista.frm_dashboard;
import Vista.pnel_inicioSesion;
import Vista.pnel_registroUsuarios;
import com.sun.tools.javac.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author Nekhross
 */
public class ctlr_login implements MouseListener, MouseMotionListener, KeyListener {

    usuarios Mdlusu;
    mdl_usuarios MdlusuC;
    frm_dashboard frmDashboard;
    UIManager UI;

    Alumnos Mdlprovee;
    mdl_alumnos MdlproveeC;

    frm_Login frm_Login;
    boolean verificadormostrar = false, verificadoCamposVacios = false;

    int CordX = 0, CordY = 0, conteoTexto = 0;

    pnel_inicioSesion panelinicioSesion = new pnel_inicioSesion();
    pnel_registroUsuarios panelregistroUsuario = new pnel_registroUsuarios();

    public ctlr_login(frm_Login frm_Login, mdl_usuarios MdlusuC, usuarios Mdlusu, frm_dashboard fmr_registroProductos, Alumnos Mdlprovee, mdl_alumnos MdlproveeC) {

        this.frm_Login = frm_Login;
        this.Mdlusu = Mdlusu;
        this.MdlusuC = MdlusuC;

        this.frmDashboard = fmr_registroProductos;
        this.Mdlprovee = Mdlprovee;
        this.MdlproveeC = MdlproveeC;

        //listener objs panel inicio sesion
        this.panelinicioSesion.btniniciarSesion.addMouseListener(this);
        this.panelinicioSesion.btnregistrarse.addMouseListener(this);
        this.panelinicioSesion.txtclave.addMouseListener(this);
        this.panelinicioSesion.txtusuario.addMouseListener(this);
        this.panelinicioSesion.btnmostrar.addMouseListener(this);
        //

        //listener del frame general
        this.frm_Login.btnsalir.addMouseListener(this);
        this.frm_Login.panelnorte.addMouseMotionListener(this);
        this.frm_Login.panelnorte.addMouseListener(this);
        //

        // listener objs panel registro de usuario
        this.panelregistroUsuario.btncancelar.addMouseListener(this);
        this.panelregistroUsuario.btnregistrar.addMouseListener(this);
        this.panelregistroUsuario.txtusuario.addMouseListener(this);
        this.panelregistroUsuario.txtapellidos.addMouseListener(this);
        this.panelregistroUsuario.txtnombres.addMouseListener(this);
        this.panelregistroUsuario.txtapellidos.addMouseListener(this);
        this.panelregistroUsuario.txtcedula.addMouseListener(this);
        this.panelregistroUsuario.txtapellidos.addKeyListener(this);
        this.panelregistroUsuario.txtnombres.addKeyListener(this);
        this.panelregistroUsuario.txtcedula.addKeyListener(this);
        this.panelregistroUsuario.btnmostrar.addMouseListener(this);

        //
    }

    public void iniciar() {

        frm_Login.setTitle("ECCP |Acceso al Sistema");

        panelinicioSesion.setSize(533, 947);
        panelinicioSesion.setLocation(0, -20);
        panelinicioSesion.removeAll();
        this.frm_Login.panelLogin.add(panelinicioSesion);

        panelregistroUsuario.setSize(533, 947);
        panelregistroUsuario.setLocation(10, -20);
        panelregistroUsuario.removeAll();
        this.frm_Login.panelLogin.add(panelregistroUsuario);
        panelregistroUsuario.setVisible(false);

        this.frm_Login.revalidate();
        this.frm_Login.repaint();

        conteoTexto = 1;
        textorotativo();

        do {
            Timer timer = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    conteoTexto = conteoTexto + 1;
                    if (conteoTexto == 4) {
                        conteoTexto = 1;
                    }
                    textorotativo();
                }
            });
            timer.start();
        } while (conteoTexto == 5);

    }

    public void convertiraMayusculas(javax.swing.JTextField jTextfieldS) {
        String cadena = (jTextfieldS.getText()).toUpperCase();
        jTextfieldS.setText(cadena);
    }

    public void textorotativo() {

        if (conteoTexto == 1) {

            frm_Login.lbltexto.setText("<html><body><center>Objetivos<br><i><br>Formar profesionales capaces y responsables<br>en la conduccion mediante un proceso de capacitacion<br>academica y asi propender al desarrollo<br>social economico del pais</body></html></center></i>");
        }
        if (conteoTexto == 2) {

            frm_Login.lbltexto.setText("<html><body><center>Mision<br><br><i>Formar conductores conscientes de su identidad con gran sentido<br>de respeto responsabilidad y solidaridad<br>de formacion humanistica con actitud tecnica cientificacapacidad de liderezgo<br>pensamiento critico y alta cognicion ciudadana comprometidos con el cambio social (...)</center></i>");
        }
        if (conteoTexto == 3) {

            frm_Login.lbltexto.setText("<html><body><center>Vision<br><br><i>La federacion de choferes profesionales del ecuador propone alcanzar una verdadera excelencia educativa en el nuevo conductor profesional en base al cultivo de valores, en concordancia con el avance de la tecnica y la ciencia,<br> con la aplicacion del nuevo reglamento para las escuelas de capacitacion (...)</center></i>");

        }

    }

    public void mostrarClave(javax.swing.JPasswordField cajaTexto, javax.swing.JLabel label) {

        if (verificadormostrar == false) {

            cajaTexto.setEchoChar((char) 0);
            label.setIcon(new ImageIcon(getClass().getResource("/recursos/ojo.png")));
            verificadormostrar = true;
        } else {
            cajaTexto.setEchoChar('•');
            label.setIcon(new ImageIcon(getClass().getResource("/recursos/ojoCerrado.png")));
            verificadormostrar = false;
        }

    }

    public void camposVacios(javax.swing.JTextField cajaTexto, javax.swing.JLabel label) {
        //verificar campos

        if (cajaTexto.getText().isEmpty()) {
            label.setForeground(Color.red);
            verificadoCamposVacios = true;
        } else {
            label.setForeground(Color.white);
        }

        //
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == this.frm_Login.btnsalir) {

            System.exit(0);
        }

        //panel iniciar sesion 
        if (e.getSource() == this.panelinicioSesion.btnmostrar) {

            mostrarClave(panelinicioSesion.txtclave, panelinicioSesion.btnmostrar);
        }
        if (e.getSource() == this.panelregistroUsuario.btnmostrar) {
            mostrarClave(panelregistroUsuario.txtclave, panelregistroUsuario.btnmostrar);
        }
        if (e.getSource() == this.panelinicioSesion.btnregistrarse) {

            panelregistroUsuario.setVisible(true);
            panelinicioSesion.setVisible(false);

        }
        if (e.getSource() == this.panelinicioSesion.btniniciarSesion) {

            Mdlusu.setUsuario(panelinicioSesion.txtusuario.getText());
            Mdlusu.setClave(panelinicioSesion.txtclave.getText());
            verificadoCamposVacios = false;

            camposVacios(panelinicioSesion.txtusuario, panelinicioSesion.lblusuario);
            camposVacios(panelinicioSesion.txtclave, panelinicioSesion.lblclave);
            if (verificadoCamposVacios == true) {
                JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Existen campos obligatorios vacios</FONT></html>");
            }

            if (verificadoCamposVacios == false) {

                if (MdlusuC.verificarCuenta(Mdlusu)) {

                    limpiarLogin();
                    UI = null;
                    UI.put("OptionPane.background", new Color(4, 50, 99));
                    UI.put("Panel.background", new Color(4, 50, 99));
                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Bievenido al sistema " + Mdlusu.getNombres() + " " + Mdlusu.getApellidos() + "</FONT></html>");
                    conteoTexto = 5;
                    frm_Login.setVisible(false);

                    frmDashboard.panelcontenedorPrincipal.removeAll();
                    ctlr_registroOficios ctlr_registroOficios = new ctlr_registroOficios(frmDashboard, Mdlusu, MdlusuC, frm_Login, Mdlprovee, MdlproveeC);
                    ctlr_registroOficios.iniciar();
                    frmDashboard.setVisible(true);

                } else {
                    UI = null;
                    UI.put("OptionPane.background", new Color(4, 50, 99));
                    UI.put("Panel.background", new Color(4, 50, 99));
                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Usuario no encontrado</FONT></html>");
                    limpiarLogin();

                }
            }

        }

        //panel registro usuario
        if (e.getSource() == this.panelregistroUsuario.btncancelar) {

            panelregistroUsuario.setVisible(false);
            panelinicioSesion.setVisible(true);

        }
        if (e.getSource() == this.panelregistroUsuario.btnregistrar) {

            Mdlusu.setUsuario(panelregistroUsuario.txtusuario.getText());
            Mdlusu.setClave(panelregistroUsuario.txtclave.getText());
            Mdlusu.setNombres(panelregistroUsuario.txtnombres.getText().toUpperCase());
            Mdlusu.setApellidos(panelregistroUsuario.txtapellidos.getText().toUpperCase());
            Mdlusu.setIdrol(2);
            Mdlusu.setCedula(panelregistroUsuario.txtcedula.getText());
            
            verificadoCamposVacios = false;
            camposVacios(panelregistroUsuario.txtusuario, panelregistroUsuario.lblUsuario1);
            camposVacios(panelregistroUsuario.txtcedula, panelregistroUsuario.lblcedula);
            camposVacios(panelregistroUsuario.txtclave, panelregistroUsuario.lblclave);
            camposVacios(panelregistroUsuario.txtnombres, panelregistroUsuario.lblnombres);
            camposVacios(panelregistroUsuario.txtapellidos, panelregistroUsuario.lblapellidos);
            
            if (verificadoCamposVacios == true) {
                JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Existen campos obligatorios vacios</FONT></html>");
            }

            if (verificadoCamposVacios == false) {
                if (MdlusuC.registrar(Mdlusu)) {

                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Se ha registrado el usuario correctamente</FONT></html>");
                    limpiarRegistro();
                } else {

                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Error al registrar</FONT></html>");
                    limpiarRegistro();
                }
            }

        }

    }

    public void limpiarRegistro() {

        panelregistroUsuario.txtusuario.setText(null);
        panelregistroUsuario.txtnombres.setText(null);
        panelregistroUsuario.txtapellidos.setText(null);
        panelregistroUsuario.txtclave.setText(null);
        panelregistroUsuario.txtcedula.setText(null);

    }

    public void limpiarLogin() {
        panelinicioSesion.txtclave.setText(null);
        panelinicioSesion.txtusuario.setText(null);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == this.frm_Login.panelnorte) {
            this.CordX = e.getX();
            this.CordY = e.getY();
        }
        if (e.getSource() == this.frm_Login.panelnorte2) {
            this.CordX = e.getX();
            this.CordY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == this.panelinicioSesion.txtusuario) {

            this.panelinicioSesion.txtusuario.setFocusable(true);
        }
        if (e.getSource() == this.panelinicioSesion.txtclave) {

            this.panelinicioSesion.txtclave.setFocusable(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == this.frm_Login.panelnorte) {
            Point p = MouseInfo.getPointerInfo().getLocation();
            int pX = p.x - CordX;
            int pY = p.y - CordY;
            this.frm_Login.setLocation(pX, pY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char caracter = e.getKeyChar();

        boolean codAsciiNum = caracter >= 48 && caracter <= 57;

        if (e.getSource() == panelregistroUsuario.txtnombres) {
            if (!Character.isLetter(caracter) && !Character.isSpaceChar(caracter)) {

                e.consume();
            }
        }
        if (e.getSource() == panelregistroUsuario.txtapellidos) {
            if (!Character.isLetter(caracter) && !Character.isSpaceChar(caracter)) {

                e.consume();
            }
        }
        if (e.getSource() == panelregistroUsuario.txtcedula) {
            if (!Character.isDigit(caracter)) {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getSource() == this.panelregistroUsuario.txtapellidos) {
            convertiraMayusculas(panelregistroUsuario.txtapellidos);
        }
        if (e.getSource() == this.panelregistroUsuario.txtnombres) {
            convertiraMayusculas(panelregistroUsuario.txtnombres);
        }

    }

}
