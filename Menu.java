    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica5;

import java.util.*;

/**
 *
 * @author BELTRAN
 */
public class Menu {
    
    public Menu(){
        int[][] problema={{5,8,3},
                          {1,0,6},
                          {4,7,2}};
        if(Evaluacion(problema)){
            Nodo nodoProblema=new Nodo();
            nodoProblema.estado=problema;
            Puzzle puzzle=new Puzzle(nodoProblema);            
        }
        else{
            System.out.println("Sistema no Tiene Solucion");
        }
            
   
    }
    protected boolean Evaluacion(int [][] problema){
        int[] vectorAux = new int[9];
        int bandera = 0;
        int contador=0;
        
        for (int i = 0; i < problema.length; i++) {
            for (int j = 0; j < problema[i].length; j++) {
                if (problema[i][j]==0) {
                    bandera=i;
                }
    
                vectorAux[contador]=problema[i][j];
                contador++;        
            }
        }
        
        contador=0;
        
        for (int i = 0; i < vectorAux.length; i++) {
            for (int j = i+1; j < vectorAux.length; j++) {
                if(vectorAux[i]>vectorAux[j]&&vectorAux[i]!=0&&vectorAux[j]!=0)
                    contador++;
            }
        }
        System.out.println("Inverciones"+contador+" Renglon"+bandera);
        if((contador%2==0))
            return true;
        return false;

            
    }
    
}
