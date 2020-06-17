package com.example.grafo;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Vector;

public class Grafo
{
    private List<Vertice> LVertices;

    public Grafo() {
        LVertices = new ArrayList();
    }

    public void crearVertice(String nomV){
        LVertices.add(LVertices.size(),new Vertice(nomV));
    }

    public Vertice buscarVertice(String nomV)
    {
        Vertice vertice;
        int i=0;
        while (i<LVertices.size())
        {
            vertice =(Vertice)LVertices.get(i);
            if (vertice.getNombre().equals(nomV))
                return vertice;
            i++;
        }
        return null;
    }

    public void insertarArco(String X,String Y, float co)
    {
        Vertice vo = buscarVertice(X);
        Vertice vd = buscarVertice(Y);
        vo.insertarArco(new Arco(vd, co));
    }

    public void imprimir(TextView jta){
        int i = 0; Vertice v; Arco a;
        while (i < LVertices.size())
        {
            v = (Vertice)LVertices.get(i);
            int j=0;
            while (j<v.LArcos.size())
            {
                jta.append(v.getNombre());
                jta.append("-->");
                a = (Arco)v.LArcos.get(j); //Muestra el arco donde apunto
                jta.append(a.getNombreVertD() + "  " + a.getCosto());
                jta.append("\n");
                j++;
            }
            i++;
        }
    }

    //METODOS DE ENSEÑANZA
    public float peso()
    {
        int i = 0;
        Vertice v; float s = 0;
        while (i < LVertices.size())
        {
            v = (Vertice)LVertices.get(i);
            int j=0; Arco a;
            while(j < v.LArcos.size())
            {
                a=(Arco)v.LArcos.get(j);
                s = s + a.getCosto();
                j++;
            }
            i++;
        }
        return s;
    }


    public void desmarcarTodos()
    {
        for(int i=0;i<this.LVertices.size();i++){
            Vertice v=(Vertice)this.LVertices.get(i);
            v.marcado=false;
        }
    }


    public void ordenarVerticesAlf() {
        Vertice aux; Vertice v1; Vertice v2;
        for(int i=0;i<LVertices.size();i++){
            for(int j=0;j<LVertices.size()-1;j++){
                v1=(Vertice)LVertices.get(j);
                v2=(Vertice)LVertices.get(j+1);
                if(v1.getNombre().compareTo(v2.getNombre())>0){
                    aux=(Vertice)LVertices.get(j);
                    LVertices.set(j, v2);
                    LVertices.set(j+1, aux);
                }
            }
        }
        for(int i=0;i<LVertices.size();i++){
            Vertice v=(Vertice)LVertices.get(i);
            v.ordenarArcosAlf();
        }
    }

    public void DFS(String A, TextView jta){
        jta.append("DFS: ");
        desmarcarTodos();
        ordenarVerticesAlf();
        Vertice v = buscarVertice(A);
        dfs(v,  jta);
        jta.append("\n");
    }

    private void dfs(Vertice v, TextView jta){
        jta.append(v.getNombre() + " ");
        v.marcado=true;
        Arco a;
        for (int i = 0; i < v.LArcos.size(); i++) {
            a = (Arco) v.LArcos.get(i);
            Vertice w = buscarVertice(a.getNombreVertD());
            if(!w.marcado)
                dfs(w, jta);
        }
    }


    public void BFS(String s,TextView jta)  {
        desmarcarTodos();
        ordenarVerticesAlf();     Arco a;
        Vertice v = buscarVertice(s), w;
        LinkedList <Vertice> C;
        C=new LinkedList<Vertice>();
        C.add(v);
        v.marcado=true;      jta.append("BFS: ");
        do{
            v = C.pop();
            jta.append(v.getNombre() + " ");
            for (int i = 0; i < v.LArcos.size(); i++) {
                a = (Arco) v.LArcos.get(i);
                w = buscarVertice(a.getNombreVertD());
                if (!w.marcado) {
                    C.add(w);
                    w.marcado=true;
                }
            }
        }while (!C.isEmpty());
        jta.append("\n");
    }

    public boolean existeCamino(String X, String Y)
    {
        if( buscarVertice(X)!=null && buscarVertice(Y)!=null ){
            desmarcarTodos();
            Vertice v=buscarVertice(X);
            return existeCamino(v,Y);
        }
        return false;
    }

    private boolean existeCamino(Vertice v, String Y)
    {
        v.marcado=true;
        for(int i=0;i<v.LArcos.size();i++)
        {
            Arco a = (Arco)v.LArcos.get(i);
            Vertice v2 = buscarVertice(a.getNombreVertD());
            if(! v2.marcado ){ //sino esta marcado (marcado==false)
                if(v2.getNombre().equals(Y))
                    return true;
                else
                if(existeCamino(v2,Y))
                    return true;
                
            }
            else
            if(v2.getNombre().equals(Y))   //Cuando se quiera a traves de otros vertices llegar al mismo vertice o del mismo al mismo
                return true;
        }
        return false;
    }

    public boolean sonIguales(Grafo G1, Grafo G2){

        boolean cond = true;

        if(G1.LVertices.size() != G2.LVertices.size()){
            return false;
        }
        if(G1.peso() != G2.peso()){
            return false;
        }
        for(int i = 0; i < LVertices.size();i++){
            if(G1.LVertices.get(i) != G1.LVertices.get(i)){
                return false;
            }
        }
        return cond;
    }

    public int arcosSalientes(String X){

        Vertice v=buscarVertice(X);
        return arcosSalientes(v,X);
    }

    private int arcosSalientes(Vertice v, String X) {

        return v.LArcos.size();

    }

    public int cantCaminos(String x, String y){

        if(buscarVertice(x) != null && buscarVertice(y)!= null)
        {
            desmarcarTodos();
            Vertice v = buscarVertice(x);
            return cantCaminos(v,y);
        }
        else{return -1;}

    }

    private int cantCaminos(Vertice V, String y){
        int i = 0, c = 0;

        V.marcado = true;

        while(i <  V.LArcos.size()){
            Arco a = (Arco)V.LArcos.get(i);
            Vertice V2 = buscarVertice(a.getNombreVertD());
            if(!V2.marcado){
                if(V2.getNombre().equals(y)){
                    c++;
                } else {
                    c+=cantCaminos(V2,y);
                    V2.marcado = false;
                }
            }
            i++;
        }
        return c;
    }

    public boolean unicoCamino(String X, String Y)
    {
        if( buscarVertice(X)!=null && buscarVertice(Y)!=null ){
            desmarcarTodos();
            Vertice v=buscarVertice(X);
            int c = cantCaminos(v,Y);
            if(c == 1){
                return true;
            }
        }
        return false;
    }
    public boolean existecaminoBFS(String x,String y)  {
        desmarcarTodos();
        ordenarVerticesAlf();     Arco a;
        Vertice v = buscarVertice(x), w;
        LinkedList <Vertice> C;
        C=new LinkedList<Vertice>();
        C.add(v);
        v.marcado=true;
        do{
            v = C.pop();

            for (int i = 0; i < v.LArcos.size(); i++) {
                a = (Arco) v.LArcos.get(i);
                w = buscarVertice(a.getNombreVertD());
                if (!w.marcado) {
                    C.add(w);
                    return true;
                }
            }
        }while (!C.isEmpty());

        return true;
    }

    public boolean esConexo()
    {
        int i=0;
        while(i<LVertices.size())
        {
            Vertice vo=(Vertice)LVertices.get(i);
            int j=0;
            while(j<LVertices.size()){
                Vertice vd=(Vertice)LVertices.get(j);
                if(!existeCamino(vo.getNombre(), vd.getNombre()))
                    return false;
                j++;
            }
            i++;
        }
        return true;
    }

    public int cantCaminosBFS(String x,String y)  {
        int cont = 0;
        desmarcarTodos();
        ordenarVerticesAlf();     Arco a;
        Vertice v = buscarVertice(x), w;
        LinkedList <Vertice> C;
        C=new LinkedList<Vertice>();
        C.add(v);
        v.marcado=true;
        do{
            v = C.pop();

            for (int i = 0; i < v.LArcos.size(); i++) {
                a = (Arco) v.LArcos.get(i);
                w = buscarVertice(a.getNombreVertD());
                if (!w.marcado) {
                    C.add(w);
                    cont++;
                    return cont;

                }
            }
        }while (!C.isEmpty());

        return cont;
    }




    public boolean esArbol(String x){

        Vertice vo=buscarVertice(x);
        vo.marcado = true;
        int i = 0;
        if(vo.LArcos.size() <= 2){
            while(i < vo.LArcos.size()){
                Arco a = (Arco)vo.LArcos.get(i);
                Vertice vd = buscarVertice(a.getNombreVertD());
                i++;
                if(!vd.marcado){
                    if(!esArbol(a.getNombreVertD())){
                        return false;
                    }
                    else{
                        return false;
                    }
                }

            }
            return true;
        }
        return false;
    }


    public boolean esLista(String x){

        Vertice vo=buscarVertice(x);
        vo.marcado = true;
        int i = 0;
        if(vo.LArcos.size() <= 1){
            while(i < vo.LArcos.size()){
                Arco a = (Arco)vo.LArcos.get(i);
                Vertice vd = buscarVertice(a.getNombreVertD());
                i++;
                if(!vd.marcado){
                    if(!esLista(a.getNombreVertD())){
                        return false;
                    }
                    else{
                        return false;
                    }
                }

            }
            return true;
        }
        return false;
    }


}  //end class
