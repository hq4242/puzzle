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
public class Puzzle {
    int[][] solucion={{1,2,3},
                      {4,5,6},
                      {7,8,0}};
    Scanner pause = new Scanner(System.in);
    String[][] Acciones = {{"Derecha","Abajo"},{"Derecha", "Izquierda","Abajo"},{ "Izquierda","Abajo"},
                           { "Arriba","Derecha","Abajo"},{ "Arriba","Derecha","Izquierda","Abajo"},{ "Arriba", "Izquierda","Abajo"},
                           {"Arriba","Derecha"},{ "Arriba","Derecha","Izquierda" },{ "Arriba","Izquierda"}};
    //LinkedList frontera = new LinkedList();
    LinkedList explorados = new LinkedList();
    Queue<Nodo> frontera = new PriorityQueue<Nodo>(new Comparator<Nodo>() {
      public int compare(Nodo a1, Nodo a2) {
          		if (a1.getheuristica() < a2.getheuristica()) {
			return -1;
		} else if (a1.getheuristica() > a2.getheuristica()) {
			return 1;
		}
		
        return 0; 
      }
    });    

    
//////////////////////////////////////////////////////////////    
    public Puzzle(Nodo problema){
        busqueda_A(problema);
 
    }
    
    public void busqueda_A(Nodo problema){
            Busqueda_A(problema);
    }
    
    public void Busqueda_A(Nodo problema){
        Nodo nodo=(Nodo)problema;
        if(Arrays.deepEquals(nodo.estado, solucion)){//Si el nodo contiene el estado Solicion entonces termine 
            System.out.println("Es la solucion soldado");
            return;
        }
        frontera.add(nodo);//Agregamos el nodo al nodo frontera
        explorados.clear();//Exploraos es un conjunto vacio
        
        while (true) {

            if(frontera.isEmpty()){//La frontera esta vacia
                System.out.println("NO HAY ELEMENTOS EN LA FRONTERA");
                return;
            }
            
            Nodo nodoPadre=(Nodo)frontera.poll();//Se extrae el nodo de la frontera
            explorados.add(nodo);//Se agrega a explorados
           
            
            //Por cada accion posible a aplicarse en nodo se evalua las posibles direcciones
            nodoPadre.getIndicedelNodo();
            for (String accionPosible : Acciones[nodoPadre.indice]) {
                Nodo hijo=(Nodo)crearNodo(nodoPadre,(String)accionPosible);
                
                if(!estaExplorados(hijo)&&!estaFrontera(hijo)){
                    if(Arrays.deepEquals(hijo.estado, solucion)){//Si el nodo contiene el estado Solicion entonces termine
                        System.out.println("Es la solucion");
                        imprimirSolucion(hijo);
                        return;
                    }
                    
                    frontera.add(hijo); 
                    
                }

      
            }
        }        

       
    }
    public boolean estaFrontera(Nodo hijo){
        LinkedList auxFrontera=new LinkedList(frontera);
        Nodo auxNodo;
        while(!auxFrontera.isEmpty()){
            auxNodo=(Nodo)auxFrontera.poll();
            if(Arrays.deepEquals(hijo.estado, auxNodo.estado))
                return true;

        }
            
      return false;  
        
    }
    public boolean estaExplorados(Nodo hijo){
        LinkedList auxExplorados=new LinkedList(explorados);
        Nodo auxNodo;
        while(!auxExplorados.isEmpty()){
            auxNodo=(Nodo)auxExplorados.poll();
            if(Arrays.deepEquals(hijo.estado, auxNodo.estado))
                return true;

        }
            
      return false;  
       
    }
   
    public Nodo crearNodo(Nodo nodoPadre,String accion){
        Nodo hijo=new Nodo();
        hijo.estado=resolverProblema(copiarArreglo(nodoPadre),accion);
        hijo.NodoPadre=nodoPadre;
        hijo.costo=nodoPadre.costo+1;
        //hijo.heuristica=hijo.costo+hijo.CuantosDesacomodados(solucion);
        hijo.heuristica=hijo.costo+hijo.distManh(solucion);
        return hijo;
        
    }
    
    
    public int[][] resolverProblema(int [][] estado,String accion){
        int[][] estadoAux=estado.clone();
        boolean flag=false;
        int flagNumero=100;
        int aux,posC=0,posF=0;
        
        if(accion=="Abajo"){
            for (int i = 0; i < estadoAux.length; i++) {
                for (int j = 0; j < estadoAux[i].length; j++) {
                    if (estadoAux[i][j]==0) {
                        flag=true;
                        flagNumero=3;
                        posF=i;
                        posC=j;
                    }
                    if(flag){
                        if(flagNumero==0){
                            estadoAux[posF][posC]=estadoAux[i][j];
                            estadoAux[i][j]=0;
                            return estadoAux;
                        }
                        flagNumero--;
                    } 
                }    
            }            
        }
        if(accion=="Arriba"){
            for (int i = estadoAux.length-1; i >= 0; i--) {
                for (int j =estadoAux[i].length-1 ; j >=0 ; j--) {
                    
                    if (estadoAux[i][j]==0) {
                        flag=true;
                        flagNumero=3;
                        posF=i;
                        posC=j;
                    }
                    if(flag){
                        if(flagNumero==0){
                            estadoAux[posF][posC]=estadoAux[i][j];
                            estadoAux[i][j]=0;
                            return estadoAux;
                        }
                        flagNumero--;
                    }
                    
                }
                
            }
        }
        if(accion=="Derecha"){
            for (int i = 0; i < estadoAux.length; i++) {
                for (int j = 0; j < estadoAux[i].length; j++) {
                    if (estadoAux[i][j]==0) {
                        flag=true;
                        flagNumero=1;
                        posF=i;
                        posC=j;
                    }
                    if(flag){
                        if(flagNumero==0){
                            estadoAux[posF][posC]=estadoAux[i][j];
                            estadoAux[i][j]=0;
                            return estadoAux;
                        }
                        flagNumero--;
                    } 
                }    
            }                 
        }
        
        if(accion=="Izquierda"){
            for (int i = estadoAux.length-1; i >= 0; i--) {
                for (int j =estadoAux[i].length-1 ; j >=0 ; j--) {
                    
                    if (estadoAux[i][j]==0) {
                        flag=true;
                        flagNumero=1;
                        posF=i;
                        posC=j;
                    }
                    if(flag){
                        if(flagNumero==0){
                            estadoAux[posF][posC]=estadoAux[i][j];
                            estadoAux[i][j]=0;
                            return estadoAux;
                        }
                        flagNumero--;
                    }
                    
                }
                
            }
        }        
        return estadoAux;

        
    }
    

    public void imprimirSolucion(Nodo aux){
        int contador=1;
        //System.out.println("Costo del Algoritmo: "+aux.costo);
        while(aux.NodoPadre!=null){
        for (int i = 0; i < aux.estado.length; i++) {
            for (int j = 0; j < aux.estado[i].length; j++) {
                System.out.printf(aux.estado[i][j]+" "); 
            }
            System.out.println();  
        }
        System.out.println();
        aux=aux.NodoPadre;
        contador++;
            System.out.println("Costo del Algotimo->"+aux.costo);
            System.out.println("----------------------------------");
        }
        System.out.println("Numero de Niveles: "+contador);
        System.out.println("El numero de nodos creados: "+aux.contador);
        
    }
    public void imprimir(Nodo aux){
            for (int i = 0; i < aux.estado.length; i++) {
            for (int j = 0; j < aux.estado[i].length; j++) {
                System.out.printf(aux.estado[i][j]+" "); 
            }
            System.out.println();  
        }
        System.out.println();    
    }
    
    public int[][] copiarArreglo(Nodo nodo){
        int[][] aux=new int[3][3];
        for(int i=0; i<nodo.estado.length; i++)
        for(int j=0; j<nodo.estado[i].length; j++)
            aux[i][j]=nodo.estado[i][j];
        return aux;
    }
    

    
    
}
