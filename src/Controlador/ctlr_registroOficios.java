package Controlador;

import Modelo.mdl_alumnos;
import Modelo.mdl_usuarios;
import Modelo.Alumnos;
import Modelo.usuarios;
import Vista.frm_Login;
import Vista.frm_dashboard;
import Vista.panelMenuAdmin;
import Vista.pnel_Cuentas;
import Vista.pnel_Dashboard;
import Vista.pnel_alumnos;
import Vista.pnel_listado;
import app_registroproductos.App_registroproductosMvc;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView;

public class ctlr_registroOficios implements MouseListener, MouseMotionListener, WindowListener, KeyListener {

    //DECLARACION DE FRAME PRINCIPALES
    frm_dashboard frmdashboard;
    frm_Login frm_Login;

    //DECLARACION DE OBJETOS DE USUARIO
    usuarios Mdlusu;
    mdl_usuarios MdlusuC;

    //DECLARACION DE OBJETOS DE PRODUCTOS
    //DECLARACION DE OBJETOS DE PROVEEDORES
    Alumnos Mdlalum;
    mdl_alumnos MdlalumC;

    //CREACION DE OBJETOS DE PANELES
    pnel_Dashboard panelDashboard = new pnel_Dashboard();
    pnel_Cuentas panelCuentas = new pnel_Cuentas();
    panelMenuAdmin panelMenuadmin = new panelMenuAdmin();
    pnel_alumnos panelAlumnos = new pnel_alumnos();
    pnel_listado panelListado = new pnel_listado();

    //DECLARACION DE VARIABLES
    boolean comprobadorPantalla = false, verificadoCampos = false, verificador = false, verificardorModificar = false;
    int CordX, CordY, verificadoNavegacion = 0, menuproducto = 0;

    Color colorCasillas;

    //METODO PARA CERRAR FRAME
    public void cerrar() {

        String botones[] = {"cerrar", "cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "<html><FONT COLOR=\"white\">¿Desea cerrar el programa? </FONT></html>", "Aviso", 0, 0, null, botones, this);

        if (seleccion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public ctlr_registroOficios(frm_dashboard frmDashboard, usuarios Mdlusu, mdl_usuarios MdlusuC, frm_Login frm_Login, Alumnos Mdlalum, mdl_alumnos MdlalumC) {
        //INICIALIZACION DE OBJETOS
        this.frmdashboard = frmDashboard;
        this.frm_Login = frm_Login;
        this.Mdlusu = Mdlusu;
        this.MdlusuC = MdlusuC;

        this.Mdlalum = Mdlalum;
        this.MdlalumC = MdlalumC;

        //INICIALIZAR LISTENER A COMPONENTES DE FRAME PRICIPAL
        this.frmdashboard.btnSalir.addMouseListener(this);
        this.frmdashboard.btnPantalla.addMouseListener(this);
        this.frmdashboard.panelnorte.addMouseListener(this);
        this.frmdashboard.panelnorte.addMouseMotionListener(this);
        this.frmdashboard.btnMinimizar.addMouseListener(this);
        this.frmdashboard.addWindowListener(this);
        this.frmdashboard.btnDashboard.addMouseListener(this);
        this.frmdashboard.btnCuenta.addMouseListener(this);
        this.frmdashboard.btnadmin.addMouseListener(this);
        this.frmdashboard.btnCerrarSesion.addMouseListener(this);

        //INICIALIZAR LISTENER A COMPONENTES DEL PANEL CUENTAS
        this.panelCuentas.btnmodificarUsuario.addMouseListener(this);

        this.panelMenuadmin.btnlistado.addMouseListener(this);
        this.panelMenuadmin.btnalumnos.addMouseListener(this);

        //INICIALIZAR LISTENER A COMPONENTES DEL PANEL PROVEEDORES
        this.panelAlumnos.btndeshacer.addMouseListener(this);
        this.panelAlumnos.btnguardar.addMouseListener(this);
        this.panelAlumnos.btnmodificar.addMouseListener(this);
        this.panelAlumnos.btnverificar.addMouseListener(this);
        this.panelAlumnos.jtbl_proveedores.addMouseListener(this);
        this.panelListado.jtbl_alumnosgeneral.addMouseListener(this);
        this.panelAlumnos.txtbuscadorProveedores.addKeyListener(this);

    }

    //METODO INICIADOR DEL FRAME PRINCIPAL
    public void iniciar() {

        //AGREGACION DE PANELES EN FRAME PRINCIPAL  
        panelDashboard.setSize(1009, 736);
        panelDashboard.setLocation(904, 631);
        panelDashboard.removeAll();
        this.frmdashboard.panelcontenedorPrincipal.add(panelDashboard);

        panelCuentas.setSize(1009, 736);
        panelCuentas.setLocation(904, 631);
        panelCuentas.removeAll();
        frmdashboard.panelcontenedorPrincipal.add(panelCuentas);
        frmdashboard.lblnombreUsuario.setText(Mdlusu.getNombres());

        panelMenuadmin.setSize(1009, 736);
        panelMenuadmin.setLocation(904, 631);
        panelMenuadmin.removeAll();
        frmdashboard.panelcontenedorPrincipal.add(panelMenuadmin);

        panelAlumnos.setSize(1009, 736);
        panelAlumnos.setLocation(904, 631);
        panelAlumnos.removeAll();
        frmdashboard.panelcontenedorPrincipal.add(panelAlumnos);

        panelListado.setSize(1009, 736);
        panelListado.setLocation(904, 631);
        panelListado.removeAll();
        frmdashboard.panelcontenedorPrincipal.add(panelListado);

        frmdashboard.revalidate();
        frmdashboard.repaint();
        ////

        //NAVEGACION MENU IZQUIERDO
        verificadoNavegacion = 1;
        Navegacion();
        Navegacion2panelesOff();

        frmdashboard.panelprincipal.setBackground(new Color(0, 0, 0, 0));
        frmdashboard.panelcentro.setBackground(new Color(0, 0, 0, 0));

        this.frmdashboard.setIconImage(new ImageIcon(getClass().getResource("/recursos/icono1.png")).getImage());
        this.frmdashboard.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        panelCuentas.lblnumeroid.setText(String.valueOf(Mdlusu.getId()));

    }

    //NAVEGADOR MENU IZQUIERDO
    public void Navegacion() {

        //VISIBILIDAD OFF
        panelDashboard.setVisible(false);
        panelCuentas.setVisible(false);
        panelMenuadmin.setVisible(false);
        frmdashboard.revalidate();
        frmdashboard.repaint();

        //COLORES MENU DEFAULT
        frmdashboard.pnelDashboard.setBackground(new Color(4, 50, 99));
        frmdashboard.btnDashboard.setForeground(Color.white);
        frmdashboard.pnelCuenta.setBackground(new Color(4, 50, 99));
        frmdashboard.btnCuenta.setForeground(Color.white);
        frmdashboard.pnelProductos.setBackground(new Color(4, 50, 99));
        frmdashboard.btnadmin.setForeground(Color.white);
        frmdashboard.pnelCerrarSesion.setBackground(new Color(4, 50, 99));
        frmdashboard.btnCerrarSesion.setForeground(Color.white);

        switch (verificadoNavegacion) {
            case 1:

                frmdashboard.setTitle("ECCP | DashBoard");
                panelDashboard.lblbienvenida.setText("Bienvenido al sistema!, " + Mdlusu.getNombres());
                frmdashboard.lblindicadorVentana.setText("DashBoard");
                frmdashboard.pnelDashboard.setBackground(Color.white);
                frmdashboard.btnDashboard.setForeground(new Color(4, 50, 99));
                Navegacion2panelesOff();
                panelDashboard.setVisible(true);

                 {
                    try {
                        panelDashboard.lblconteoalumnos.setText(String.valueOf(MdlalumC.conteoAlumnos(Mdlalum)));
                        panelDashboard.lblconteousuarios.setText(String.valueOf(MdlusuC.conteoUsuarios(Mdlusu)));
                    } catch (SQLException ex) {
                    }
                }

                break;

            case 2:

                frmdashboard.setTitle("ECCP | Cuenta");
                frmdashboard.lblindicadorVentana.setText("Cuenta");
                frmdashboard.pnelCuenta.setBackground(Color.white);
                frmdashboard.btnCuenta.setForeground(new Color(4, 50, 99));
                Navegacion2panelesOff();
                panelCuentas.setVisible(true);
                verificadoCampos = false;

                Mdlusu.setId(Integer.parseInt(panelCuentas.lblnumeroid.getText()));
                if (MdlusuC.buscar(Mdlusu)) {
                    panelCuentas.txtusuario.setText(Mdlusu.getUsuario());
                    panelCuentas.txtclave.setText(Mdlusu.getClave());
                    panelCuentas.txtnombres.setText(Mdlusu.getNombres());
                    panelCuentas.txtapellidos.setText(Mdlusu.getApellidos());
                    panelCuentas.txtcedula.setText(Mdlusu.getCedula());

                    panelCuentas.lblUsuario.setText(Mdlusu.getNombres() + " " + Mdlusu.getApellidos());
                    frmdashboard.lblnombreUsuario.setText(Mdlusu.getNombres());
                }

                break;

            case 3:

                frmdashboard.setTitle("ECCP | Menu Administracion");
                frmdashboard.lblindicadorVentana.setText("Menu Administracion");
                panelMenuadmin.setVisible(true);
                frmdashboard.pnelProductos.setBackground(Color.white);
                frmdashboard.btnadmin.setForeground(new Color(4, 50, 99));
                Navegacion2panelesOff();
                break;

            case 4:
                frmdashboard.setTitle("ECCP | Cierre de Sesion");
                frmdashboard.pnelCerrarSesion.setBackground(Color.white);
                frmdashboard.btnCerrarSesion.setForeground(new Color(4, 50, 99));

                String botones[] = {"si", "cancelar"};
                int seleccion = JOptionPane.showOptionDialog(null, "<html><FONT COLOR=\"white\">¿Desea cerrar sesion?</FONT></html>", "Aviso", 0, 0, null, botones, this);

                if (seleccion == JOptionPane.YES_OPTION) {
                    frmdashboard.setVisible(false);
                    App_registroproductosMvc appMvc = new App_registroproductosMvc();
                    appMvc.main(null);

                }

                break;

            default:
                throw new AssertionError();
        }

    }

    //NAVEGADOR PANEL MENU PRODUCTOS
    public void Navegacion2panelesOff() {
        panelAlumnos.setVisible(false);
        panelListado.setVisible(false);

    }

    public void navegacion2() {

        //VISIBILIDAD OFF
        Navegacion2panelesOff();

        frmdashboard.revalidate();
        frmdashboard.repaint();

        switch (menuproducto) {
            case 1:

                frmdashboard.setTitle("ProduReg| Listado General");
                frmdashboard.lblindicadorVentana.setText("Listado");
                panelListado.setVisible(true);
                llenartablaA();

                verificador = false;

                break;

            case 2:

                frmdashboard.setTitle("ProduReg| Alumnos");
                frmdashboard.lblindicadorVentana.setText("Alumnos");
                panelAlumnos.setVisible(true);

                verificador = false;
                verificacionAlum();
                limpiarCasillasAlum();
                llenartablaA();

                break;
            default:
                throw new AssertionError();
        }

    }
    //METODOS DE TODOS LOS PANELES

    public void camposVacios(javax.swing.JTextField cajaTexto, javax.swing.JLabel label) {
        //verificar campos

        if (cajaTexto.getText().isEmpty()) {
            label.setForeground(Color.red);
            verificadoCampos = true;
        } else {
            label.setForeground(Color.white);
        }

        //
    }

    public void pintarCasillasAlum() {

        panelAlumnos.txtapellidosAlumno.setBackground(colorCasillas);
        panelAlumnos.txtnombresAlumno.setBackground(colorCasillas);

    }

    public void limpiarCasillasAlum() {

        panelAlumnos.txtapellidosAlumno.setText(null);
        panelAlumnos.txtnombresAlumno.setText(null);
        panelAlumnos.txtcedulaAlumno.setText(null);

    }

    public void verificacionAlum() {

        if (verificador == true) {

            panelAlumnos.txtapellidosAlumno.enable();
            panelAlumnos.txtnombresAlumno.enable();
            panelAlumnos.txtcedulaAlumno.disable();
            panelAlumnos.btnverificar.setBackground(Color.lightGray);
            panelAlumnos.txtcedulaAlumno.setBackground(Color.lightGray);
            colorCasillas = Color.WHITE;
            panelAlumnos.btnguardar.setBackground(new Color(0, 153, 153));
            pintarCasillasAlum();

        } else if (verificador == false) {

            panelAlumnos.txtnombresAlumno.disable();
            panelAlumnos.txtapellidosAlumno.disable();
            panelAlumnos.btnverificar.setBackground(new Color(0, 204, 102));
            colorCasillas = Color.lightGray;
            pintarCasillasAlum();
            panelAlumnos.txtcedulaAlumno.enable();
            panelAlumnos.txtcedulaAlumno.setBackground(Color.WHITE);
            panelAlumnos.btnguardar.setBackground(Color.lightGray);
            panelAlumnos.btnmodificar.setBackground(Color.lightGray);

        }
    }

    public void llenartablaA() {

        try {
            DefaultTableModel modeloTabla = (DefaultTableModel) this.panelAlumnos.jtbl_proveedores.getModel();
            DefaultTableModel modeloTabla2 = (DefaultTableModel) this.panelListado.jtbl_alumnosgeneral.getModel();

            modeloTabla.setColumnCount(0);
            modeloTabla.setRowCount(0);

            modeloTabla2.setRowCount(0);

            modeloTabla.addColumn("Numero#");
            modeloTabla.addColumn("Cedula");
            modeloTabla.addColumn("Nombres");
            modeloTabla.addColumn("Apellidos");

            ResultSet rs = MdlalumC.consultarAlumnosTabla();

            String[] registros = new String[6];

            while (rs.next()) {

                registros[0] = rs.getString("id");
                registros[1] = rs.getString("cedula");
                registros[2] = rs.getString("nombres");
                registros[3] = rs.getString("apellidos");

                modeloTabla.addRow(registros);
                modeloTabla2.addRow(registros);

            }

        } catch (SQLException ex) {

        }

    }

    public void buscarTablaAlum(String text) {

        DefaultTableModel tablaModelo = (DefaultTableModel) panelAlumnos.jtbl_proveedores.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tablaModelo);
        this.panelAlumnos.jtbl_proveedores.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(text));

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //MOUSE EVENT FRM PRINCIPALL
        if (e.getSource() == this.frmdashboard.btnSalir) {
            cerrar();
        }

        if (e.getSource() == this.frmdashboard.btnPantalla) {
            if (comprobadorPantalla == false) {
                this.frmdashboard.setExtendedState(6);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frmdashboard.panelprincipal.setSize(screenSize);
                frmdashboard.panelcentro.setSize(screenSize);
                frmdashboard.panelsur.setSize(screenSize);
                frmdashboard.panelnorte.setSize(screenSize);
                comprobadorPantalla = true;

            } else if (comprobadorPantalla == true) {
                this.frmdashboard.setExtendedState(0);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                frmdashboard.panelprincipal.setSize(screenSize);
                frmdashboard.panelcentro.setSize(screenSize);
                frmdashboard.panelsur.setSize(screenSize);
                frmdashboard.panelnorte.setSize(screenSize);
                comprobadorPantalla = false;

            }
        }
        if (e.getSource() == this.frmdashboard.btnMinimizar) {
            this.frmdashboard.setExtendedState(1);
        }

        //funciones panel de navegacion
        if (e.getSource() == this.frmdashboard.btnDashboard) {
            verificadoNavegacion = 1;
            Navegacion();
        }
        if (e.getSource() == this.frmdashboard.btnCuenta) {
            verificadoNavegacion = 2;
            Navegacion();
        }
        if (e.getSource() == this.frmdashboard.btnadmin) {
            verificadoNavegacion = 3;
            Navegacion();
        }
        if (e.getSource() == this.frmdashboard.btnCerrarSesion) {
            verificadoNavegacion = 4;
            Navegacion();
        }
        //

        //MOUSE CLICK EVENT PANEL CUENTA
        if (e.getSource() == this.panelCuentas.btnmodificarUsuario) {

            //envio de parametro para consultar
            Mdlusu.setId(Integer.parseInt(panelCuentas.lblnumeroid.getText()));
            Mdlusu.setUsuario(panelCuentas.txtusuario.getText());
            Mdlusu.setClave(panelCuentas.txtclave.getText());
            Mdlusu.setNombres(panelCuentas.txtnombres.getText().toUpperCase());
            Mdlusu.setApellidos(panelCuentas.txtapellidos.getText().toUpperCase());
            Mdlusu.setCedula(panelCuentas.txtcedula.getText());

            verificadoCampos = false;
            camposVacios(panelCuentas.txtapellidos, panelCuentas.lblapellidos);
            camposVacios(panelCuentas.txtcedula, panelCuentas.lblcedula);
            camposVacios(panelCuentas.txtclave, panelCuentas.lblclave);
            camposVacios(panelCuentas.txtnombres, panelCuentas.lblnombres);
            camposVacios(panelCuentas.txtusuario, panelCuentas.lblusuario2);

            if (verificadoCampos == true) {
                JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Existen campos obligatorios vacios</FONT></html>");
            }

            if (verificadoCampos == false) {

                if (MdlusuC.modificar(Mdlusu)) {

                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Se ha modificado el usuario correctamente</html></FONT>");
                    panelCuentas.txtusuario.setText(Mdlusu.getUsuario());
                    panelCuentas.txtclave.setText(Mdlusu.getClave());
                    panelCuentas.txtnombres.setText(Mdlusu.getNombres());
                    panelCuentas.txtapellidos.setText(Mdlusu.getApellidos());
                    panelCuentas.txtcedula.setText(Mdlusu.getCedula());
                    panelCuentas.lblUsuario.setText(Mdlusu.getNombres() + " " + Mdlusu.getApellidos());
                    frmdashboard.lblnombreUsuario.setText(Mdlusu.getNombres());

                } else {

                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Error al modificar</html></FONT>");

                }
            }

        }

        if (e.getSource() == this.panelMenuadmin.btnlistado) {

            menuproducto = 1;
            panelMenuadmin.setVisible(false);
            navegacion2();

        }
        if (e.getSource() == this.panelMenuadmin.btnalumnos) {
            menuproducto = 2;
            panelMenuadmin.setVisible(false);
            navegacion2();

        }

        if (e.getSource() == this.panelAlumnos.btnverificar && verificador == false) {

            Mdlalum.setCedula(panelAlumnos.txtcedulaAlumno.getText());
            verificadoCampos = false;
            camposVacios(panelAlumnos.txtcedulaAlumno, panelAlumnos.lblcedulaAlumno);
            if (verificadoCampos == true) {
                JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">No se puede verificar un campo vacio</FONT></html>");
            }
            if (verificadoCampos == false) {
                if (MdlalumC.verificarAlumno(Mdlalum)) {
                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Este Alumno ya se encuentra registrado</html></FONT>");

                } else {

                    verificador = true;
                    verificacionAlum();

                }
            }

        }
        if (e.getSource() == this.panelAlumnos.btnguardar && verificador == true && verificardorModificar == false) {

            Mdlalum.setNombres(panelAlumnos.txtnombresAlumno.getText());
            Mdlalum.setApellidos(panelAlumnos.txtapellidosAlumno.getText());
            Mdlalum.setCedula(panelAlumnos.txtcedulaAlumno.getText());

            verificadoCampos = false;
            camposVacios(panelAlumnos.txtapellidosAlumno, panelAlumnos.lblapellidoalumno);
            camposVacios(panelAlumnos.txtcedulaAlumno, panelAlumnos.lblcedulaAlumno);
            camposVacios(panelAlumnos.txtnombresAlumno, panelAlumnos.lblnombreAlumno);
            if (verificadoCampos == true) {
                JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Existen campos obligatorios vacios</FONT></html>");
            }

            if (verificadoCampos == false) {
                if (MdlalumC.registrar(Mdlalum)) {

                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Alumno registrado correctamente</html></FONT>");
                    llenartablaA();
                    limpiarCasillasAlum();
                    verificador = false;
                    verificacionAlum();

                } else {
                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Error al registrar Alumno</html></FONT>");

                }
            }

        } else if (e.getSource() == this.panelAlumnos.btnguardar && verificador == false) {
            JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Verifique el Alumno primero</html></FONT>");
        } else if (e.getSource() == this.panelAlumnos.btnguardar && verificardorModificar == true) {
            JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Precione modificar</html></FONT>");
        }

        if (e.getSource() == this.panelAlumnos.btnmodificar && verificador == true && verificardorModificar == true) {

            Mdlalum.setId(Integer.parseInt(panelAlumnos.idProveedor.getText()));
            Mdlalum.setNombres(panelAlumnos.txtnombresAlumno.getText());
            Mdlalum.setApellidos(panelAlumnos.txtapellidosAlumno.getText());
            Mdlalum.setCedula(panelAlumnos.txtcedulaAlumno.getText());

            verificadoCampos = false;
            camposVacios(panelAlumnos.txtapellidosAlumno, panelAlumnos.lblapellidoalumno);
            camposVacios(panelAlumnos.txtcedulaAlumno, panelAlumnos.lblcedulaAlumno);
            camposVacios(panelAlumnos.txtnombresAlumno, panelAlumnos.lblnombreAlumno);
            if (verificadoCampos == true) {
                JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Existen campos obligatorios vacios</FONT></html>");
            }

            if (verificadoCampos == false) {
                if (MdlalumC.modificar(Mdlalum)) {

                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Alumno modificado correctamente</html></FONT>");
                    llenartablaA();
                    limpiarCasillasAlum();
                    verificador = false;
                    verificacionAlum();
                } else {
                    JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">No utilice la cedula de otro Alumno</html></FONT>");
                }
            }

        } else if (e.getSource() == this.panelAlumnos.btnmodificar && verificador == false) {
            JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">Verifique el Alumno primero</html></FONT>");
        } else if (e.getSource() == this.panelAlumnos.btnmodificar && verificardorModificar == false) {
            JOptionPane.showMessageDialog(null, "<html><FONT COLOR=\"white\">No puede modificar un registro sin guardar seleccine uno</html></FONT>");
        }

        if (e.getSource() == this.panelAlumnos.jtbl_proveedores) {

            verificador = true;
            verificardorModificar = true;
            panelAlumnos.btnmodificar.setBackground(new Color(0, 153, 153));
            panelAlumnos.txtapellidosAlumno.enable();
            panelAlumnos.txtnombresAlumno.enable();
            panelAlumnos.txtcedulaAlumno.enable();
            panelAlumnos.btnverificar.setBackground(Color.lightGray);
            panelAlumnos.txtcedulaAlumno.setBackground(Color.white);
            panelAlumnos.btnguardar.setEnabled(false);
            colorCasillas = Color.white;
            pintarCasillasAlum();

            DefaultTableModel tm = (DefaultTableModel) this.panelAlumnos.jtbl_proveedores.getModel();

            String nombre = String.valueOf(tm.getValueAt(this.panelAlumnos.jtbl_proveedores.getSelectedRow(), 2)).trim();
            this.panelAlumnos.txtnombresAlumno.setText(nombre);
            String apellidos = String.valueOf(tm.getValueAt(this.panelAlumnos.jtbl_proveedores.getSelectedRow(), 3)).trim();
            this.panelAlumnos.txtapellidosAlumno.setText(apellidos);
            String cedula = String.valueOf(tm.getValueAt(this.panelAlumnos.jtbl_proveedores.getSelectedRow(), 1)).trim();
            this.panelAlumnos.txtcedulaAlumno.setText(cedula);
            String id = String.valueOf(tm.getValueAt(this.panelAlumnos.jtbl_proveedores.getSelectedRow(), 0)).trim();
            this.panelAlumnos.idProveedor.setText(id);

        }
        if (e.getSource() == this.panelAlumnos.btndeshacer) {

            limpiarCasillasAlum();
            verificador = false;
            verificacionAlum();
            verificardorModificar = false;
            panelAlumnos.btnmodificar.setBackground(Color.lightGray);
            panelAlumnos.btnguardar.setBackground(Color.lightGray);
            panelAlumnos.btnguardar.setEnabled(true);

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == this.frmdashboard.panelnorte) {
            this.CordX = e.getX();
            this.CordY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this.frmdashboard.btnSalir) {
            this.frmdashboard.panelBtnSalir.setBackground(new Color(255, 102, 102));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == this.frmdashboard.btnSalir) {
            this.frmdashboard.panelBtnSalir.setBackground(Color.white);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == this.frmdashboard.panelnorte) {
            if (comprobadorPantalla == false) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                int pX = p.x - CordX;
                int pY = p.y - CordY;
                this.frmdashboard.setLocation(pX, pY);
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (e.getSource() == this.frmdashboard) {
            cerrar();

        }

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        //keyrealeased event panel proveedores
        if (e.getSource() == this.panelAlumnos.txtbuscadorProveedores) {
            buscarTablaAlum(this.panelAlumnos.txtbuscadorProveedores.getText());
        }

    }

}
