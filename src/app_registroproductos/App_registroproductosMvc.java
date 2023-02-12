/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_registroproductos;

import Controlador.ctlr_login;
import Modelo.mdl_alumnos;
import Modelo.mdl_usuarios;
import Modelo.Alumnos;
import Modelo.usuarios;
import Vista.frm_Login;
import Vista.frm_dashboard;

/**
 *
 * @author Nekhross
 */
public class App_registroproductosMvc {
    
    public static void main(String[]args){
        
        usuarios Mldusu = new usuarios();
        mdl_usuarios MldusuC = new mdl_usuarios();
        frm_Login frm_Login = new frm_Login();
        frm_dashboard frm_registroProductos = new frm_dashboard();
     
        
        Alumnos Mdlprovee = new Alumnos();
        mdl_alumnos MdlproveeC = new mdl_alumnos();
        
        ctlr_login ctlr_login = new ctlr_login(frm_Login, MldusuC, Mldusu, frm_registroProductos, Mdlprovee, MdlproveeC);
        ctlr_login.iniciar();
        frm_Login.setVisible(true);
        
        
        
    }
}
